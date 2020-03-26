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
package com.qarepo.driver.webelement;

import com.qarepo.driver.WebDriverThreadManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.Map;

public class WebElementService {

    /**
     *
     * @param webElement WebElement to extract details from
     * @return WebElementDetails which includes the original WebElement and all extracted details
     */
    public WebElementDetails extractElementDetails(WebElement webElement) {
        WebElementDetails webElementDetails = new WebElementDetails();
        if (webElement != null) {
            webElementDetails.setWebElement(webElement);
        } else {
            throw new NoSuchElementException("Element Not Found In The DOM: " + webElement.toString());
        }
        webElementDetails.setLocator(webElement.toString());
        if (webElement.getTagName() != null) {
            webElementDetails.setTagName(webElement.getTagName());
        }
        if (webElement.getText() != null) {
            webElementDetails.setText(webElement.getText());
        }
        webElementDetails.setAttributes(getWebElementAttributes(webElement));
        return webElementDetails;
    }

    private Map<String, String> getWebElementAttributes(WebElement element) {
        Map<String, String> webElementAttributes;
        JavascriptExecutor executor = (JavascriptExecutor) WebDriverThreadManager.getDriver();
        webElementAttributes = (Map<String, String>) executor.executeScript(
                "var elements = {}; for (i = 0; i < arguments[0].attributes.length; ++i) " +
                        "{ elements[arguments[0].attributes[i].name] = arguments[0].attributes[i].value };" +
                        " return elements;", element);
        return webElementAttributes;
    }

}
