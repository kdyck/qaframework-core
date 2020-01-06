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
package com.qarepo.utils;

import com.qarepo.driver.WebDriverThreadManager;
import com.qarepo.driver.WebDriverWaits;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAmount;

/*
 * @author Rafael Dyck (rafaeldcyk@gmail.com)
 * @author Kaci Dyck (kdyck00@gmail.com)
 * @since 1.0.0
 */
public final class SeleniumUtils {

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

    /*
     * Method will generate and return the path of an existing screenshot.
     */
    public static String getScreenshotPath(String browser, String screenshotName) {
        String imagePath = "./Screenshots/" + browser + " - " + WebDriverThreadManager.getDriver().hashCode() + " - "
                + screenshotName + ".png";
        return imagePath;
    }

    /*
     * Method will switch to a frame by extracting the name attribute from an iframe
     * based on an xpath locator
     */
    public static void switchToFrameByName(String frameXPath) {
        String frame = WebDriverThreadManager.getDriver().findElement(By.xpath(frameXPath)).getAttribute("name");
        WebDriverThreadManager.getDriver().switchTo().frame(frame);
    }

    /*
     * Method will switch to a frame by using the id attribute in the iframe
     */
    public static void switchToFrameByID(String id) {
        WebDriverThreadManager.getDriver().switchTo().frame(id);
    }

    /*
     * Method will return a date as a String based on current date +/- a certain
     * number of days Syntax for TemporalAmount = Period.OfDays(int days),
     * Period.OfWeeks(int weeks), Period.OfMonths(int months), Period.OfYears(int
     * years)
     */
    public static String dateGenerator(TemporalAmount period) {
        LocalDateTime localDate = LocalDateTime.now().plus(period);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return localDate.format(formatter);
    }

    /*
     * Method will execute javascript to scroll to a specific element on the page
     * located by a xpath expression
     */
    public static void scrollToElement(WebElement webElement) {
        ((JavascriptExecutor) WebDriverThreadManager.getDriver()).executeScript("arguments[0].scrollIntoView(true);", webElement);
    }

    /*
     * Method will execute javascript to inject a geolocation in the browser
     */
    public static void injectGeoLocation(String latitude, String longitude) {
        ((JavascriptExecutor) WebDriverThreadManager.getDriver()).executeScript(
                "window.navigator.geolocation.getCurrentPosition = function(success){ var position = {\"coords\" : {\"latitude\": \""
                        + latitude + "\",\"longitude\": \"" + longitude + "\"}};success(position);}");
    }

    /*
     * Switches from parent window to sub-window.
     */
    public static String switchWindows(String expectedURL, boolean returnToParent) {
        String currentURL = null;
        String parentWindow = WebDriverThreadManager.getDriver().getWindowHandle();
        for (final String subWindow : WebDriverThreadManager.getDriver().getWindowHandles()) {
            WebDriverThreadManager.getDriver().switchTo().window(subWindow);
            if (!WebDriverThreadManager.getDriver().getWindowHandle().contentEquals(parentWindow)) {
                WebDriverWaits.waitForUrl(expectedURL);
                currentURL = WebDriverThreadManager.getDriver().getCurrentUrl();
                WebDriverThreadManager.getDriver().close();
            }
        }
        if (returnToParent)
            WebDriverThreadManager.getDriver().switchTo().window(parentWindow);
        return currentURL;
    }

}
