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
package com.qarepo.utils;

import com.qarepo.driver.WebDriverActions;
import com.qarepo.driver.WebDriverThreadManager;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAmount;

/*
 * @since 1.0.0
 */
public final class SeleniumUtils {
    private static final Logger LOGGER = LogManager.getLogger(SeleniumUtils.class);
    private static String driverHash = "[WebDriver Hash: " + WebDriverThreadManager.getDriver()
            .hashCode() + "] ";
    private static StringWriter sw = new StringWriter();
    private static FluentWait<WebDriver> wait;

    /*
     * Private constructor to avoid instantiation of the class
     */
    private SeleniumUtils() {
    }

    /*
     * Method will generate a screenshot in the form of a file and save to the
     * Screenshots folder in test-output
     */
    public static void takeScreenshot(String browser, String screenshotName) throws IOException {
        File screenshot = ((TakesScreenshot) WebDriverThreadManager.getDriver()).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshot, new File("./test-output/Reports/Screenshots/" + browser + " - "
                + WebDriverThreadManager.getDriver().hashCode() + " - " + screenshotName + ".png"));

    }

    /**
     * returns the path of an existing screenshot
     *
     * @param browser        {@link String}  browser name for screenshot filename
     * @param screenshotName filename for screenshot
     * @return screenshot image path
     */
    public static String getScreenshotPath(String browser, String screenshotName) {
        return "./Screenshots/" + browser + " - " + WebDriverThreadManager.getDriver().hashCode() + " - "
                + screenshotName + ".png";
    }

    /**
     * Switched to a frame by extracting the name attribute from an iframe
     * based on an xpath locator
     *
     * @param frameXPath xpath expression for iFrame
     */
    public static void switchToFrameByName(String frameXPath) {
        String frame = WebDriverThreadManager.getDriver().findElement(By.xpath(frameXPath)).getAttribute("name");
        WebDriverThreadManager.getDriver().switchTo().frame(frame);
    }

    /**
     * Switches to a frame by using the id attribute in the iFrame
     *
     * @param id iFrame id
     */
    public static void switchToFrameByID(String id) {
        WebDriverThreadManager.getDriver().switchTo().frame(id);
    }

    /**
     * Returns a date as a String based on current date +/- a certain
     * number of days Syntax for TemporalAmount = Period.OfDays(int days),
     * Period.OfWeeks(int weeks), Period.OfMonths(int months), Period.OfYears(int
     * years)
     *
     * @param period {@link TemporalAmount} for number of days to add from current date.
     */
    public static String dateGenerator(TemporalAmount period) {
        LocalDateTime localDate = LocalDateTime.now().plus(period);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return localDate.format(formatter);
    }

    /**
     * Executes javascript to scroll to a specific DOM element
     * takes an xpath expression
     *
     * @param webElement element to scroll into view
     */
    public static void scrollToElement(WebElement webElement) {
        ((JavascriptExecutor) WebDriverThreadManager.getDriver()).executeScript("arguments[0].scrollIntoView(true);", webElement);
    }

    /**
     * Executes javascript to inject a geolocation in the browser
     *
     * @param latitude  locale latitude point
     * @param longitude locale longitude point
     */
    public static void injectGeoLocation(String latitude, String longitude) {
        ((JavascriptExecutor) WebDriverThreadManager.getDriver()).executeScript(
                "window.navigator.geolocation.getCurrentPosition = function(success){ var position = {\"coords\" : {\"latitude\": \""
                        + latitude + "\",\"longitude\": \"" + longitude + "\"}};success(position);}");
    }

    /**
     * Runs javascript in the browser
     *
     * @param script Javascript to be executed
     */
    public static void executeJavascript(String script) {
        ((JavascriptExecutor) WebDriverThreadManager.getDriver()).executeScript(
                script);
    }

    /**
     * Switches from parent window to sub-window.
     *
     * @param expectedURL    URL to wait for
     * @param returnToParent true to return to parent, false otherwise
     */
    public static String switchWindows(String expectedURL, boolean returnToParent) {
        String currentURL = null;
        String parentWindow = WebDriverThreadManager.getDriver().getWindowHandle();
        for (final String subWindow : WebDriverThreadManager.getDriver().getWindowHandles()) {
            WebDriverThreadManager.getDriver().switchTo().window(subWindow);
            if (!WebDriverThreadManager.getDriver().getWindowHandle().contentEquals(parentWindow)) {
                WebDriverActions.waitForUrl(expectedURL);
                currentURL = WebDriverThreadManager.getDriver().getCurrentUrl();
                WebDriverThreadManager.getDriver().close();
            }
        }
        if (returnToParent)
            WebDriverThreadManager.getDriver().switchTo().window(parentWindow);
        return currentURL;
    }

    /**
     * Switches from parent window to sub-window and back to parent window.
     *
     * @param timeOutInSeconds how long to wait
     * @param expectedURL      URL to wait for
     * @return {@link String} currentURL
     */
    public static String switchWindowsAndReturnToParent(long timeOutInSeconds, String expectedURL) {
        WebDriverWait wait = new WebDriverWait(WebDriverThreadManager.getDriver(), timeOutInSeconds);
        String currentURL = null;
        String parentWindow = WebDriverThreadManager.getDriver()
                .getWindowHandle();
        try {
            for (final String subWindow : WebDriverThreadManager.getDriver()
                    .getWindowHandles()) {
                WebDriverThreadManager.getDriver()
                        .switchTo()
                        .window(subWindow);
                if (!WebDriverThreadManager.getDriver()
                        .getWindowHandle()
                        .contentEquals(parentWindow)) {
                    wait.until(ExpectedConditions.urlContains(expectedURL));
                    currentURL = WebDriverThreadManager.getDriver()
                            .getCurrentUrl();
                    WebDriverThreadManager.getDriver()
                            .close();
                }
            }
            WebDriverThreadManager.getDriver()
                    .switchTo()
                    .window(parentWindow);
        } catch (Exception e) {
            return e.toString();
        }
        return currentURL;
    }

    /**
     * Switches from parent window to sub-window and stay in sub-window.
     *
     * @param timeOutInSeconds how long to wait
     * @param expectedURL      URL to wait for
     * @return {@link String} currentURL
     */
    public static String switchWindowsAndDoNotReturnToParent(long timeOutInSeconds, String expectedURL) {
        WebDriverWait wait = new WebDriverWait(WebDriverThreadManager.getDriver(), timeOutInSeconds);
        String currentURL = null;
        try {
            String parentWindow = WebDriverThreadManager.getDriver()
                    .getWindowHandle();
            for (final String subWindow : WebDriverThreadManager.getDriver()
                    .getWindowHandles()) {
                WebDriverThreadManager.getDriver()
                        .switchTo()
                        .window(subWindow);
                if (!WebDriverThreadManager.getDriver()
                        .getWindowHandle()
                        .contentEquals(parentWindow)) {
                    wait.until(ExpectedConditions.urlContains(expectedURL));
                    currentURL = WebDriverThreadManager.getDriver()
                            .getCurrentUrl();
                    WebDriverThreadManager.getDriver()
                            .close();
                }
            }
        } catch (Exception e) {
            return e.toString();
        }
        return currentURL;
    }

    /**
     * calls void accept() to click on the 'OK' button of the alert.
     */
    public static void rejectAlert() {
        LOGGER.info(driverHash + "Click alert 'OK' button");
        WebDriverThreadManager.getDriver()
                .switchTo()
                .alert()
                .accept();
    }

    /**
     * calls void dismiss() to click on the 'Cancel' button of the alert.
     */
    public static void acceptAlert() {
        LOGGER.info(driverHash + "Click alert 'Cancel' button");
        WebDriverThreadManager.getDriver()
                .switchTo()
                .alert()
                .dismiss();
    }

    /**
     * calls {@link String} getText() to capture the alert message.
     *
     * @param polling polling interval
     * @param timeout timeout after polling
     * @return {@link String} of alert text.
     */
    public static String getAlertText(long timeout, long polling) {
        wait = new FluentWait<>(WebDriverThreadManager.getDriver());
        wait.withTimeout(Duration.ofSeconds(timeout))
                .pollingEvery(Duration.ofSeconds(polling))
                .ignoring(WebDriverException.class)
                .until(ExpectedConditions.alertIsPresent());
        String alertText = WebDriverThreadManager.getDriver()
                .switchTo()
                .alert()
                .getText();
        LOGGER.info(driverHash + "Alert text: [" + alertText + "]");
        return alertText;
    }

    /**
     * calls void sendKeys() to enter text in alert
     *
     * @param textToSend alert text to send
     */
    public static void sendTextToAlert(String textToSend) {
        LOGGER.info(driverHash + "Text to alert: [" + textToSend + "]");
        WebDriverThreadManager.getDriver()
                .switchTo()
                .alert()
                .sendKeys(textToSend);
    }


    /**
     * Used to handle static wait (thread sleeps) and catches exception
     *
     * @param millis how many milliseconds the thread sleeps
     */
    public static void getThreadSleep(long millis) {
        try {
            Thread.sleep(millis);
            LOGGER.info(driverHash + "Thread sleeping for: [" + millis + "] milliseconds.");
        } catch (InterruptedException e) {
            e.printStackTrace(new PrintWriter(sw));
            LOGGER.error(driverHash + " " + sw.toString());
        }
    }

}
