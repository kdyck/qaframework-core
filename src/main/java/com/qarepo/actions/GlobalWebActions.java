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
import com.qarepo.driver.WebDriverWaits;
import com.qarepo.pageobjects.GoogleElements;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *  @since 1.0.0
 */
public class GlobalWebActions {
    static final Logger LOGGER = LogManager.getLogger(GlobalWebActions.class);
    String driverHash = "[WebDriver Hash: " + WebDriverThreadManager.getDriver().hashCode() + "]";

    /**
     * This is helper method. Return element attribute as set.
     * Used with other actions method to extract webElementAttribute from DOM elements.
     *
     * @param webElement          the By locator for the element or elements the use is testing
     * @param webElementAttribute the attribute for the tag
     * @return set of attributes
     */
    public String getElementAttribute(By webElement, String webElementAttribute) {
        return WebDriverWaits.findElementWithWait(webElement).getAttribute(webElementAttribute);
    }

    public void typeText(By webElement, String inputText, String webElementAttribute) {
        String elementAttribute = WebDriverWaits.findElementWithVisibilityWait(webElement)
                                                .getAttribute(webElementAttribute);
        WebDriverWaits.findElementWithWait(webElement).sendKeys(inputText);
        LOGGER.info("[WebDriver Hash: " + driverHash + "] [Type '" + inputText + "' into '" + elementAttribute + "' input field]");
    }

    public void clickElement(By webElement, String webElementAttribute) {
        String elementAttribute = WebDriverWaits.findElementWithWait(webElement, 10, 1).getAttribute(webElementAttribute);
        WebDriverWaits.clickElementWithWait(webElement, 10, 1);
        LOGGER.info("[WebDriver Hash: " + driverHash + "] [Click '" + elementAttribute + "' button]");
    }

    public boolean clickElements(By webElement, String webElementAttribute) {
        WebDriverWaits.findElementsWithWait(webElement).forEach(WebElement::click);
        LOGGER.info("[WebDriver Hash: " + driverHash + "] [Click '" + webElementAttribute + "' button]");
        return true;
    }

    public String getElementText(By webElement, String webElementAttribute) {
        String elementAttribute = WebDriverWaits.findElementWithWait(webElement, 10, 1).getAttribute(webElementAttribute);
        String text = WebDriverWaits.findElementWithVisibilityWait(webElement).getText();
        LOGGER.info("[WebDriver Hash: " + driverHash + "] [Get text '" + text + " for element '" + elementAttribute + "']");
        return text;
    }

    public List<String> getTextForAListOfElements(By webElements, String webElementAttribute) {
        List<String> list = WebDriverWaits.findElementsWithWait(webElements)
                                          .stream()
                                          .map(WebElement::getText)
                                          .collect(Collectors.toList());
        LOGGER.info("[WebDriver Hash: " + driverHash + "] [No. elements found: " + list.size() + " Element text '" + list
                .toString() + "' for element " + webElementAttribute + "]");
        return list;
    }

    public Set<String> getElementTextAsSet(By webElements, String webElementAttribute) {
        Set<String> set = WebDriverWaits.findElementsWithWait(webElements)
                                        .stream()
                                        .map(WebElement::getText)
                                        .collect(Collectors.toSet());
        LOGGER.info("[WebDriver Hash: " + driverHash + "] [No. elements found: " + set.size() + " Element text '" + set
                .toString() + "' for element " + webElementAttribute + "]");
        return set;
    }

    public void googleSignIn(String username, String password) {
        typeText(GoogleElements.textBox_GoogleAccount(), username, "aria-label");
        clickElement(GoogleElements.button_Next(), "class");
        String profileId = getElementText(GoogleElements.text_ProfileId(), "id");
        if (profileId.equalsIgnoreCase(username)) {
            typeText(GoogleElements.textBox_password(), password, "name");
            clickElement(GoogleElements.button_Next(), "class");
        }
    }
}
