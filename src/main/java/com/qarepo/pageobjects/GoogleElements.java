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
package com.qarepo.pageobjects;

import org.openqa.selenium.By;

public class GoogleElements {
    public static By textBox_GoogleAccount() {
        return By.xpath("//input[@type='email']");
    }

    public static By textBox_password() {
        return By.xpath("//input[@type='password']");
    }

    public static By button_Next() {
        return By.xpath("//div//span[text()='Next']");
    }

    public static By text_ProfileId() {
        return By.id("profileIdentifier");
    }

    public static By text_WrongPassword() {
        return By.xpath("//div/span[contains (text(), 'Wrong')]");
    }

    public static By textBox_Search() {
        return By.xpath("//input[@title='Search']");
    }

    public static By button_GoogleSearch() {
        return By.xpath("//input[@value='Google Search']");
    }

    public static By button_ImFeelingLucky() {
        return By.xpath("//input[@value='I'm Feeling Lucky']");
    }

    public static By text_Header_QaRepo() {
        return By.xpath("//h3[contains (text(), '@qarepo')]");
    }

    public static By button_SignIn() {
        return By.xpath("//a[contains (@href, 'accounts.google.')]");
    }

    public static By button_SignOut() {
        return By.xpath("//a[contains (@href, 'accounts.google.com/Logout')]");
    }

    public static By button_GoogleAcct() {
        return By.xpath("//a[contains (@aria-label, '@gmail')]//span");
    }
}
