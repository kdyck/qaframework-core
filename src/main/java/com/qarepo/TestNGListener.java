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
package com.qarepo;

import com.qarepo.driver.WebDriverRunner;
import com.qarepo.driver.WebDriverThreadManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

import java.util.Map;

public class TestNGListener implements IInvokedMethodListener {
    private static final Logger LOGGER = LogManager.getLogger(TestNGListener.class);
    private WebDriverRunner webDriverRunner = new WebDriverRunner();

    @Override
    public void beforeInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
        if (iInvokedMethod.isTestMethod()) {
            Map<String, String> testParams = iInvokedMethod.getTestMethod().getXmlTest().getLocalParameters();
            String browser = testParams.get("browser");
            String url = testParams.get("URL");
            webDriverRunner.startWebDriver(browser);
            WebDriverThreadManager.getDriver().get(url);
            LOGGER.info("[WebDriver Hash: "
                    + WebDriverThreadManager.getDriver().hashCode()
                    + "] Start test with parameters... \n"
                    + "[Description: " + iTestResult.getMethod().getDescription()
                    + "] [Browser: " + browser
                    + "] [URL: " + url + "]");
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
        if (iInvokedMethod.isTestMethod()) {
            webDriverRunner.stopWebDriver();
        }
    }
}
