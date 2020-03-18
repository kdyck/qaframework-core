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

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/*
 * @since 1.0.0
 */
public final class DriverFactory {
    private static final Logger LOGGER = LogManager.getLogger(DriverFactory.class);
    private static final String BROWSERSTACK_USERNAME = "change-me";
    private static final String BROWSERSTACK_AUTOMATE_KEY = "change-me";
    private static final String BROWSERSTACK_URL = "https://" + BROWSERSTACK_USERNAME + ":" + BROWSERSTACK_AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";
    private static final String DOCKER_CHROME_DRIVER_URL = "http://chrome:4444/wd/hub";

    protected static WebDriver createDriverInstance(final String browser, final String downloadPath) {
        StringWriter sw = new StringWriter();
        WebDriver driver = null;
        RemoteWebDriver remoteWebDriver = null;
        if (browser.equalsIgnoreCase("Firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            options.setCapability("marionette", true);
            driver = new FirefoxDriver(options);
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            return driver;
        } else if (browser.equalsIgnoreCase("Internet Explorer")) {
            WebDriverManager.iedriver().setup();
            driver = new InternetExplorerDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            return driver;
        } else if (browser.equalsIgnoreCase("Microsoft Edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            return driver;
        } else if (browser.equalsIgnoreCase("Google Chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            return driver;
        } else if (browser.equalsIgnoreCase("Google Chrome Headless")) {
            try {
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.setHeadless(true);
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--disable-extensions");
                options.addArguments("--headless");
                options.addArguments("--no-sandbox");
                options.addArguments("--lang=en");
                driver = new ChromeDriver(options);
                driver.manage().window().maximize();
                driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
                return driver;
            } catch (Exception e) {
                e.printStackTrace(new PrintWriter(sw));
                LOGGER.error(sw.toString());
            }
        } else if (browser.equalsIgnoreCase("Chrome BrowserStack")) {
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("browserName", "Chrome");
            caps.setCapability("device", "");
            caps.setCapability("os_version", "11");
            caps.setCapability("name", "Bstack-[Java] Sample Test");
            caps.setCapability("acceptSslCerts", "true");
            // caps.setCapability("browserstack.local", "true");
            try {
                remoteWebDriver = new RemoteWebDriver(new URL(BROWSERSTACK_URL), caps);
                remoteWebDriver.manage().window().maximize();
                remoteWebDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return remoteWebDriver;
        } else if (browser.equalsIgnoreCase("Chrome Docker")) {
            try {
                ChromeOptions options = new ChromeOptions();
                options.setHeadless(true);
                options.addArguments("--disable-extensions");
                options.addArguments("--disable-gpu");
                options.addArguments("--no-sandbox");
                remoteWebDriver = new RemoteWebDriver(new URL(DOCKER_CHROME_DRIVER_URL), options);
                remoteWebDriver.manage().window().maximize();
                remoteWebDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return remoteWebDriver;
        }
        return driver;
    }
}

