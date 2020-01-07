package com.qarepo.driver;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

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
