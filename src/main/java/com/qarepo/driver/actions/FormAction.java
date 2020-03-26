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
package com.qarepo.driver.actions;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qarepo.driver.WebDriverThreadManager;
import com.qarepo.driver.webelement.WebElementDetails;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.util.Map;
import java.util.TreeMap;

import static com.qarepo.driver.WebDriverActions.findElementWithWait;

public interface FormAction {

    default void fillForm(Object formValuesObject) {
        Map<String, String> locators = loadFormLocators();
        Map<String, String> formValues = objectToMap(formValuesObject);
        for (String locatorKey : locators.keySet()) {
            WebElementDetails formElementDetails = findElementWithWait(By.xpath(locators.get(locatorKey)));
            WebElement formElement = formElementDetails.getWebElement();
            try {
                if (formValues.get(locatorKey) != null) {
                    if (formElementDetails.getTagName().equalsIgnoreCase("input")) {
                        formElement.clear();
                        formElement.click();
                    }
                    formElement.sendKeys(formValues.get(locatorKey));
                }
            } catch (ElementClickInterceptedException e) {
                ((JavascriptExecutor) WebDriverThreadManager.getDriver()).executeScript("arguments[0].scrollIntoView(true);",
                        findElementWithWait(By.xpath("//body")).getWebElement());
                if (formElementDetails.getTagName().equalsIgnoreCase("input")) {
                    formElement.clear();
                    formElement.click();
                }
                formElement.sendKeys(formValues.get(locatorKey));
            }
        }
    }

    default Map<String, String> getValidationMessages(String validationLocator) {
        Map<String, String> locators = loadFormLocators();
        Map<String, String> validationMessages = new TreeMap<>();
        for (String locatorKey : locators.keySet()) {
            WebElementDetails formElementDetails = findElementWithWait(By.xpath(locators.get(locatorKey)
                    + validationLocator));
            validationMessages.put(locatorKey, formElementDetails.getText());
        }
        return validationMessages;
    }

    default Map<String, String> objectToMap(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(object, new TypeReference<Map<String, String>>() {
        });
    }

    Map<String, String> loadFormLocators();

    void clickSubmit();
}
