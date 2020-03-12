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

import com.qarepo.actions.GoogleActions;
import com.qarepo.driver.WebDriverThreadManager;
import com.qarepo.pageobjects.GoogleElements;
import com.qarepo.testdata.DataGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class SampleTests {
    GoogleActions webActions;
    private static final Logger LOGGER = LogManager.getLogger(SampleTests.class);
    private static final String GOOGLE_USERNAME = "qarepo.com@gmail.com";
    private static final String GOOGLE_PSWD = "rand0mXYZ123passworD";

    @Test(groups = {"search"}, description = "Search for QARepo")
    public void searchForQARepo() {
        webActions = new GoogleActions();
        webActions.typeText(GoogleElements.textBox_Search(), "qarepo", "aria-label");
        webActions.clickElement(GoogleElements.button_GoogleSearch(), "href");
        String text = webActions.getElementText(GoogleElements.text_Header_QaRepo(), "*");
        Assert.assertEquals(text, "qarepo (@qarepo) | Twitter");
    }

    @Test(groups = {"search"}, description = "Click one search result")
    public void clickQARepoSearchResult() {
        GoogleActions webActions = new GoogleActions();
        searchForQARepo();
        webActions.clickElement(GoogleElements.text_Header_QaRepo(), "href");
        String currentUrl = WebDriverThreadManager.getDriver().getCurrentUrl();
        Assert.assertEquals(currentUrl, "https://twitter.com/qarepo");
    }

    @Test(groups = {"sign-in"}, description = "Sign in, wrong password error displays")
    public void signInWrongPasswordErrorDisplays() {
        webActions = new GoogleActions();
        webActions.clickElement(GoogleElements.button_SignIn(), "href");
        webActions.login(GOOGLE_USERNAME, GOOGLE_PSWD);
        String errorText = webActions.getElementText(GoogleElements.text_WrongPassword(), "jsname");
        boolean isEqual = errorText.equalsIgnoreCase("Wrong password. Try again or click Forgot password to reset it.")
                || errorText.equalsIgnoreCase("This browser or app may not be secure. Learn more") ? true : false;
        Assert.assertTrue(isEqual, errorText);
    }

    @Test(groups = {"site-links"},
            dataProvider = "getLinksFromCSV"
            , dataProviderClass = DataGenerator.class
            , description = "Click links and verify success/redirect response")
    public void clickSiteLinks(String... params) {
        StringWriter sw = new StringWriter();
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
