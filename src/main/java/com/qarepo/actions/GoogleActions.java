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
package com.qarepo.actions;

import com.qarepo.driver.WebDriverThreadManager;
import com.qarepo.pageobjects.GoogleElements;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @since 1.0.0
 */
public class GoogleActions implements CommonActions, UserAccessActions {
    static final Logger LOGGER = LogManager.getLogger(GoogleActions.class);
    String driverHash = "[WebDriver Hash: " + WebDriverThreadManager.getDriver().hashCode() + "]";

    /**
     * @param registrationInfo credentials for registration name, email etc
     */
    @Override
    public void register(String... registrationInfo) {
        // TODO: Implement registration if needed
    }

    /**
     * Sample multi step actions got Google,
     * Signs in to Gmail by using the typeText() and clickElement() actions
     *
     * @param username login username String
     * @param password login password String
     */
    @Override
    public void login(String username, String password) {
        typeText(GoogleElements.textBox_GoogleAccount(), username, "aria-label");
        clickElement(GoogleElements.button_Next(), "class");
        String profileId = getElementText(GoogleElements.text_ProfileId(), "id");
        if (profileId.equalsIgnoreCase(username)) {
            typeText(GoogleElements.textBox_password(), password, "name");
            clickElement(GoogleElements.button_Next(), "class");
        }
    }

    @Override
    public void logout() {
        clickElement(GoogleElements.button_GoogleAcct(), "class");
        clickElement(GoogleElements.button_SignOut(), "href");
    }
}
