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
    private static String driverHash = "[WebDriver Hash: " + WebDriverThreadManager.getDriver()
            .hashCode() + "] ";
    private static FluentWait<WebDriver> wait;
    private static WebElementService webElementService = new WebElementService();

    /**
     * Waits for WebElement to be present in DOM, extracts details from WebElement and logs WebElementDetails.
     * Number of times that DOM will be checked for WebElement = timeout/polling
     * @param by locator for WebElement
     * @param timeout time to wait for WebElement to be available before throwing org.openqa.selenium.TimeoutException
     * @param polling interval for checking if WebElement is present in the DOM
     * @return WebElementDetails which includes the original WebElement and all extracted details
     */
    public static WebElementDetails findElementWithWait(By by, long timeout, long polling) {
        WebDriver driver = WebDriverThreadManager.getDriver();
        wait = new FluentWait<>(driver);
        wait.withTimeout(Duration.ofSeconds(timeout)).pollingEvery(Duration.ofSeconds(polling))
                .ignoring(WebDriverException.class).until(e -> driver.findElement(by));
        LOGGER.info(driverHash + "Waiting for element to be available: " + by.toString());
        WebElementDetails webElementDetails = webElementService.extractElementDetails(driver.findElement(by));
        LOGGER.info(driverHash + "Element Found: " + webElementDetails.toString());
        return webElementDetails;
    }

    /**
     * @param by locator for WebElement
     * @return WebElementDetails which includes the original WebElement and all extracted details
     */
    public static WebElementDetails findElementWithWait(By by) {
        return findElementWithWait(by, DEFAULT_WAIT, DEFAULT_POLLING);
    }

    /**
     * Waits for WebElement to be present in DOM, extracts details from WebElement and logs WebElementDetails.
     * Number of times that DOM will be checked for WebElement = timeout/polling
     * @param by locator for WebElement
     * @param timeout time to wait for WebElement to be available before throwing org.openqa.selenium.TimeoutException
     * @param polling interval for checking if WebElement is present in the DOM
     * @return List of WebElementDetails which includes the original WebElements and all extracted details
     */
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

    /**
     * Waits for WebElement to be present in DOM, extracts details from WebElement and logs WebElementDetails.
     * Number of times that DOM will be checked for WebElement = timeout/polling
     * @param by locator for WebElement
     * @return List of WebElementDetails which includes the original WebElements and all extracted details
     */
    public static List<WebElementDetails> findElementsWithWait(By by) {
        return findElementsWithWait(by, 10, DEFAULT_POLLING);
    }

    /**
     * Waits for WebElement to be in DOM that contains text, extracts details from WebElement and logs WebElementDetails.
     * Number of times that DOM will be checked for WebElement = timeout/polling
     * @param by locator for WebElement
     * @param textContains text to be contained in WebElement
     * @param timeout time to wait for WebElement to be available before throwing org.openqa.selenium.TimeoutException
     * @param polling interval for checking if WebElement is present in the DOM
     * @return List of WebElementDetails which includes the original WebElements and all extracted details
     */
    public static WebElementDetails findElementWithTextContainsWait(By by, String textContains, long timeout, long polling) {
        WebDriver driver = WebDriverThreadManager.getDriver();
        wait = new FluentWait<>(driver);
        wait.withTimeout(Duration.ofSeconds(timeout))
                .pollingEvery(Duration.ofSeconds(polling))
                .ignoring(WebDriverException.class)
                .until(ExpectedConditions.textToBePresentInElementLocated(by, textContains));
        return webElementService.extractElementDetails(driver.findElement(by));
    }

    /**
     * Waits for WebElement to be in DOM that contains text, extracts details from WebElement and logs WebElementDetails.
     * Number of times that DOM will be checked for WebElement = timeout/polling
     * @param by locator for WebElement
     * @param textContains textContains text to be contained in WebElement
     * @return WebElementDetails which includes the original WebElement and all extracted details
     */
    public static WebElementDetails findElementWithTextContainsWait(By by, String textContains) {
        return findElementWithTextContainsWait(by, textContains, 10, DEFAULT_POLLING);
    }

    /**
     * Waits for URL to contain a String
     * Number of times that DOM will be checked for WebElement = timeout/polling
     * @param expectedURL partial or complete URL
     * @param timeout time to wait for expectedURL
     * @param polling interval for checking URL
     */
    public static void waitForUrl(String expectedURL, long timeout, long polling) {
        WebDriver driver = WebDriverThreadManager.getDriver();
        wait = new FluentWait<>(driver);
        wait.withTimeout(Duration.ofSeconds(timeout)).pollingEvery(Duration.ofSeconds(polling))
                .ignoring(WebDriverException.class).until(ExpectedConditions.urlContains(expectedURL));
    }

    /**
     * Waits for URL to contain a String
     * @param expectedURL partial or complete URL
     */
    public static void waitForUrl(String expectedURL) {
        waitForUrl(expectedURL, 10, DEFAULT_POLLING);
    }

    public static WebElementDetails clickElementWithWait(By by, long timeout, long polling) {
        WebElementDetails webElementDetails = new WebElementDetails();
        for (int i = 0; i < 10; i++) {
            try {
                webElementDetails = findElementWithWait(by);
                LOGGER.info("Clicking element: " + webElementDetails.toString());
                webElementDetails.getWebElement().click();
                break;
            } catch (ElementClickInterceptedException e) {
                LOGGER.log(Level.WARN, "[WebDriver Hash: " + WebDriverThreadManager.getDriver().hashCode()
                        + "][attempt: " + (i + 1) + "] Failed to click element: " + webElementDetails.toString());
            }
        }
        return webElementDetails;
    }

    public static void clickElementWithWait(By by) {
        clickElementWithWait(by, 10, DEFAULT_POLLING);
    }
}
