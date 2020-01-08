/*
 * (C) Copyright 2020 qarepo.com (https://qarepo.com/)
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Random;

/**
 * <p>
 * The main objective of this class to allow to ability to get random user agents
 * using Jsoup library selector on the chrome user agent list located via the
 * https://developers.whatismybrowser.com/useragents/explore/software_name/chrome/ site.
 * </p>
 *
 * @since 1.0.0
 */
public class JSoupUserAgentService {
    private static final Logger LOGGER = LogManager.getLogger(JSoupUserAgentService.class);
    private String url;
    Document doc = null;

    public JSoupUserAgentService() {
        this.url = "https://developers.whatismybrowser.com/useragents/explore/software_name/chrome/";
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRandomUserAgent() {
        String userAgent = null;
        try {
            doc = Jsoup.connect(url)
                       .timeout(3000)
                       .get();
            List<Element> userAgentList = doc.select("td.useragent");
            System.out.println(userAgentList.size());
            for (int i = 0; i < userAgentList.size(); i++) {
                userAgent = userAgentList.get(new Random().nextInt(userAgentList.size())).text();
            }
            LOGGER.info("[USER_AGENT: " + userAgent + "]");
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            LOGGER.error(sw.toString());
        }
        return userAgent;
    }
}
