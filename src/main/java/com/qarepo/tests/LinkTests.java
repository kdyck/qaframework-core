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
package com.qarepo.tests;

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

public class LinkTests {
    private static final Logger LOGGER = LogManager.getLogger(LinkTests.class);
    private static StringWriter sw = new StringWriter();

    @Test(groups = {"site-links"},
            dataProvider = "getLinksFromCSV"
            , dataProviderClass = DataGenerator.class
            , description = "Click links and verify success/redirect response")
    public void clickSiteLinks(String... params) {
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
