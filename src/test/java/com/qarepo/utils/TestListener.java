package com.qarepo.utils;

import com.qarepo.driver.WebDriverRunner;
import com.qarepo.driver.WebDriverThreadManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

import java.util.Map;

public class TestListener implements IInvokedMethodListener {
    private static final Logger LOGGER = LogManager.getLogger(TestListener.class);
    private WebDriverRunner webDriver = new WebDriverRunner();

    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            Map<String, String> testParams = method.getTestMethod().getXmlTest().getLocalParameters();
            String browser = testParams.get("browser");
            String url = testParams.get("URL");
            webDriver.startWebDriver(browser);
            WebDriverThreadManager.getDriver().get(url);
            LOGGER.info("[WebDriver Hash: "
                    + WebDriverThreadManager.getDriver().hashCode()
                    + "] Start test with parameters... \n"
                    + "[Description: " + testResult.getMethod().getDescription()
                    + "] [Browser: " + browser
                    + "] [URL: " + url);
        }
    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            webDriver.stopWebDriver();
        }
    }
}
