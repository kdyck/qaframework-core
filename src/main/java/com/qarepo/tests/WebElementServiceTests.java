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

import static com.qarepo.driver.WebDriverActions.*;
import com.qarepo.driver.WebDriverRunner;
import com.qarepo.driver.WebDriverThreadManager;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class WebElementServiceTests {

    @BeforeTest
    public void setWebDriverInstance() {
        WebDriverRunner runner = new WebDriverRunner();
        runner.startWebDriver("Google Chrome");
    }

    @AfterTest
    public void closeWebDriverInstance() {
        WebDriverRunner runner = new WebDriverRunner();
        runner.stopWebDriver();
    }

    @Test
    public void elementAttributesAreExtractableToList() {
        WebDriverThreadManager.getDriver().get("https://www.google.com");
        findElementWithWait(By.xpath("//input[@title='Search']")).getWebElement().sendKeys("Search Term");
        findElementWithWait(By.xpath("//input[@value='Google Search']")).getWebElement().click();
        Assert.assertNotNull(findElementWithWait(By.xpath("")).getWebElement());
    }

}
