package com.qarepo.utils;

import com.qarepo.testdata.DataGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class JSoupLinkExtractorTests {
    private static final Logger LOGGER = LogManager.getLogger(JSoupLinkExtractorTests.class);
    private static StringWriter sw = new StringWriter();

    @Test(groups = {"jsoup-site-links"},
            dataProvider = "getLinksFromCSV"
            , dataProviderClass = DataGenerator.class
            , description = "Check site links for success/redirect response")
    public void checkLinks(String... params) {
        SoftAssert softAssert = new SoftAssert();
        URL url;
        try {
            url = new URL(params[1]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            int responseCode = conn.getResponseCode();
            LOGGER.debug("[URL: " + params[1] + "] [Response Code: " + responseCode + "]");
            if (responseCode == 200) {
                softAssert.assertEquals(200, responseCode);
            } else if (responseCode == 301) {
                softAssert.assertEquals(301, responseCode);
            } else {
                softAssert.fail("[URL: " + params[1] + "] [Response Code: " + responseCode + "]");
            }
        } catch (IOException e) {
            e.printStackTrace(new PrintWriter(sw));
            LOGGER.error(sw.toString());
        }
        softAssert.assertAll();
    }
}
