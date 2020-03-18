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

import com.qarepo.driver.webelement.WebElementDetails;
import com.qarepo.driver.webelement.WebElementService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

/*
 * @since 1.0.0
 */
public class WebDriverActions {
    private static final Logger LOGGER = LogManager.getLogger(WebDriverActions.class);
    private static final int DEFAULT_WAIT = 10;
    private static final int DEFAULT_POLLING = 1;
    private static FluentWait<WebDriver> wait;
    private static WebElementService webElementService = new WebElementService();
    private static String driverHash = "[WebDriver Hash: " + WebDriverThreadManager.getDriver()
            .hashCode() + "] ";

    public static WebElementDetails findElementWithWait(By by, long timeout, long polling) {
        WebDriver driver = WebDriverThreadManager.getDriver();
        wait = new FluentWait<>(driver);
        wait.withTimeout(Duration.ofSeconds(timeout)).pollingEvery(Duration.ofSeconds(polling))
                .ignoring(WebDriverException.class).until(e -> driver.findElement(by));
        WebElementDetails webElementDetails = webElementService.extractElementDetails(driver.findElement(by));
        LOGGER.info(driverHash + webElementDetails.toString());
        return webElementDetails;
    }

    public static WebElementDetails findElementWithWait(By by) {
        return findElementWithWait(by, DEFAULT_WAIT, DEFAULT_POLLING);
    }

    public static List<WebElementDetails> findElementsWithWait(By by, long timeout, long polling) {
        WebDriver driver = WebDriverThreadManager.getDriver();
        wait = new FluentWait<>(driver);
        wait.withTimeout(Duration.ofSeconds(timeout)).pollingEvery(Duration.ofSeconds(polling))
                .ignoring(WebDriverException.class).until(e -> driver.findElement(by));
        List<WebElementDetails> webElementDetailsList = driver.findElements(by).stream()
                .map(e -> webElementService.extractElementDetails(e))
                .collect(Collectors.toList());
        webElementDetailsList.forEach(e -> LOGGER.info(driverHash + e.toString()));
        return webElementDetailsList;
    }

    public static List<WebElementDetails> findElementsWithWait(By by) {
        return findElementsWithWait(by, 10, DEFAULT_POLLING);
    }

    public static WebElement findElementWithTextContainsWait(By by, String textContains, long timeout, long polling) {
        WebDriver driver = WebDriverThreadManager.getDriver();
        wait = new FluentWait<>(driver);
        wait.withTimeout(Duration.ofSeconds(timeout))
                .pollingEvery(Duration.ofSeconds(polling))
                .ignoring(WebDriverException.class)
                .until(ExpectedConditions.textToBePresentInElementLocated(by, textContains));
        return driver.findElement(by);
    }

    public static WebElement findElementWithTextContainsWait(By by, String textContains) {
        return findElementWithTextContainsWait(by, textContains, 10, DEFAULT_POLLING);
    }

    public static void waitForUrl(String expectedURL, long timeout, long polling) {
        WebDriver driver = WebDriverThreadManager.getDriver();
        wait = new FluentWait<>(driver);
        wait.withTimeout(Duration.ofSeconds(timeout)).pollingEvery(Duration.ofSeconds(polling))
                .ignoring(WebDriverException.class).until(ExpectedConditions.urlContains(expectedURL));
    }

    public static void waitForUrl(String expectedURL) {
        waitForUrl(expectedURL, 10, DEFAULT_POLLING);
    }

    public static void clickElementWithWait(By by, long timeout, long polling) {
        for (int i = 0; i < 10; i++) {
            try {
                WebDriver driver = WebDriverThreadManager.getDriver();
                wait = new FluentWait<>(driver);
                wait.withTimeout(Duration.ofSeconds(timeout)).pollingEvery(Duration.ofSeconds(polling))
                        .ignoring(WebDriverException.class).until(ExpectedConditions.elementToBeClickable(by));
                WebDriverThreadManager.getDriver().findElement(by).click();
                break;
            } catch (ElementClickInterceptedException e) {
                LOGGER.log(Level.WARN, "[WebDriver Hash: " + WebDriverThreadManager.getDriver().hashCode()
                        + "][attempt: " + (i + 1) + "] Failed to click element: " + by.toString());
            }
        }
    }

    public static void clickElementWithWait(By by) {
        clickElementWithWait(by, 10, DEFAULT_POLLING);
    }
}
