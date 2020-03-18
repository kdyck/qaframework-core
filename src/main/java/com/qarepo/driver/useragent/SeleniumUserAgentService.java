/*
 * Copyright (c) 2020-2030, Rafael Dyck and Kacianne Dyck (qarepo.com) and contributing developers
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.qarepo.driver.useragent;

import com.qarepo.driver.WebDriverRunner;
import com.qarepo.driver.WebDriverThreadManager;
import com.qarepo.driver.WebDriverActions;
import com.qarepo.driver.webelement.WebElementDetails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * <p>
 * The main objective of this class to allow to ability to get random user agents
 * using Selenium WebDriver on the chrome user agent list located via the
 * https://developers.whatismybrowser.com/useragents/explore/software_name/chrome/ site.
 * </p>
 *
 * @since 1.0.0
 */
public class SeleniumUserAgentService {
    private static final Logger LOGGER = LogManager.getLogger(SeleniumUserAgentService.class);
    private String browser;
    private String url;

    public SeleniumUserAgentService() {
        this.browser = "GOOGLE CHROME HEADLESS";
        this.url = "https://developers.whatismybrowser.com/useragents/explore/software_name/chrome/";
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getUserAgentList() {
        return WebDriverActions.findElementsWithWait(By.xpath("//td/a"))
                             .stream()
                             .map(WebElementDetails::getText)
                             .collect(Collectors.toList());
    }

    public String getRandomUserAgent() {
        String userAgent = null;
        WebDriverRunner driver = new WebDriverRunner();
        try {
            driver.startWebDriver(browser);
            WebDriverThreadManager.getDriver()
                                  .get(url);
            List<String> userAgentList = getUserAgentList();
            userAgent = userAgentList.get(new Random().nextInt(userAgentList.size()));
            LOGGER.info("[USER_AGENT: " + userAgent + "]");
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            LOGGER.error(sw.toString());
        } finally {
            driver.stopWebDriver();
        }
        return userAgent;
    }
}
