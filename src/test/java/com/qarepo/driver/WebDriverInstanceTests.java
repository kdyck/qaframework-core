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
package com.qarepo.driver;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

/**
 * @since 1.0.0
 */
public class WebDriverInstanceTests {
    private WebDriver driver = null;
    private String browser = "Google Chrome Headless";

    @Test
    public void driverInstanceHashCodeIsUnique() {
        Map<String, Integer> mapOne = new HashMap<>();
        Map<String, Integer> mapTwo = new HashMap<>();
        try {
            for (int i = 0; i < 6; i++) {
                driver = DriverFactory.createDriverInstance(browser, "");
                WebDriverThreadManager.setWebDriver(driver);
                int driverHashOne = driver.hashCode();
                mapOne.put(browser, driverHashOne);
                driver.quit();
            }
            for (int i = 0; i < 6; i++) {
                driver = DriverFactory.createDriverInstance(browser, "");
                WebDriverThreadManager.setWebDriver(driver);
                int driverHashTwo = driver.hashCode();
                mapTwo.put(browser, driverHashTwo);
                driver.quit();
            }
            Assert.assertNotSame(mapOne.values(), mapTwo.values());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void createDriverInstanceWithInvalidBrowserFails() {
        WebDriverRunner driver = new WebDriverRunner();
        driver.startWebDriver(browser + " BROWSER");
        Assert.assertNull(WebDriverThreadManager.getDriver());
    }

    @Test
    public void createDriverInstanceWithValidBrowserIsSuccessful() {
        String[] browsers = {"Firefox", "Internet Explorer",
                "Microsoft Edge", "Google Chrome",
                "Google Chrome Headless"};
        try {
            for (String browser : browsers) {
                driver = DriverFactory.createDriverInstance(browser, "");
                WebDriverThreadManager.setWebDriver(driver);
                if (driver instanceof WebDriverThreadManager) {
                    Assert.assertNull(WebDriverThreadManager.getDriver());
                }
                driver.quit();
                Assert.assertTrue(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
