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
import org.testng.Assert;
import org.testng.annotations.Test;

public class GoogleTests {
    GoogleActions webActions;
    private static final String GOOGLE_USERNAME = "qarepo.com@gmail.com";
    private static final String GOOGLE_PSWD = "rand0mXYZ123passworD";

    @Test(groups = {"search1"}, description = "Search for QARepo")
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
        Assert.assertEquals(errorText, "Wrong password. Try again or click Forgot password to reset it.");
    }

}
