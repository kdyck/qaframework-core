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
package com.qarepo.driver;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

/*
 * @author Rafael Dyck (rafaeldyck@gmail.com)
 * @since 1.0.0
 */
public class WebDriverRunner implements WebDriverRunnable {
    private static final Logger LOGGER = LogManager.getLogger(WebDriverRunner.class);

    @Override
    public void startWebDriver(String browser) {
        WebDriver driver = DriverFactory.createDriverInstance(browser, "");
        WebDriverThreadManager.setWebDriver(driver);
        LOGGER.log(Level.INFO, "[WebDriver Hash: " + driver.hashCode() + "] WebDriver Created");
    }

    @Override
    public void stopWebDriver() {
        int driverHash = 0;
        if (WebDriverThreadManager.getDriver() != null) {
            driverHash = WebDriverThreadManager.getDriver().hashCode();
            WebDriverThreadManager.getDriver().quit();
        }
        LOGGER.log(Level.INFO, "[WebDriver Hash: " + driverHash + "] WebDriver Stopped");
    }
}
