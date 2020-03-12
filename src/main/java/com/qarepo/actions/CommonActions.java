package com.qarepo.actions;

import com.qarepo.driver.WebDriverThreadManager;
import com.qarepo.driver.WebDriverWaits;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface CommonActions {
    Logger LOGGER = LogManager.getLogger(CommonActions.class);
    String driverHash = "[WebDriver Hash: " + WebDriverThreadManager.getDriver().hashCode() + "]";

    /**
     * Extract element attribute
     * Uses the WebElement getAttribute() method to get attribute value as String.
     *
     * @param webElement          the By locator for the element or elements the use is testing
     * @param webElementAttribute the attribute for the tag
     */
    default String getElementAttribute(By webElement, String webElementAttribute) {
        return WebDriverWaits.findElementWithWait(webElement).getAttribute(webElementAttribute);
    }

    /**
     * Types text (e.g. input, text-box).
     * Uses the WebElement sendKeys() method to type text in to an input field.
     *
     * @param inputText           the text to type
     * @param webElement          the By locator for the element or elements the use is testing
     * @param webElementAttribute the attribute for the tag
     */
    default void typeText(By webElement, String inputText, String webElementAttribute) {
        String elementAttribute = WebDriverWaits.findElementWithVisibilityWait(webElement)
                                                .getAttribute(webElementAttribute);
        WebDriverWaits.findElementWithWait(webElement).sendKeys(inputText);
        LOGGER.info("[WebDriver Hash: " + driverHash + "] [Type '" + inputText + "' into '" + elementAttribute + "' input field]");
    }

    /**
     * Clicks one(1) element (e.g. link, buttons).
     * Uses the clickElementWithWait() method which wraps the WebElement click() method for clicking.
     *
     * @param webElement          the By locator for the element or elements the use is testing
     * @param webElementAttribute the attribute for the tag
     */
    default void clickElement(By webElement, String webElementAttribute) {
        String elementAttribute = WebDriverWaits.findElementWithWait(webElement, 10, 1)
                                                .getAttribute(webElementAttribute);
        WebDriverWaits.clickElementWithWait(webElement, 10, 1);
        LOGGER.info("[WebDriver Hash: " + driverHash + "] [Click '" + elementAttribute + "' button]");
    }

    /**
     * Clicks for clicking elements using foreach() (e.g. link, buttons).
     * Uses the WebElement click() method for clicking.
     *
     * @param webElement          the By locator for the element or elements the use is testing
     * @param webElementAttribute the attribute for the tag
     * @return true if clicks succeeds else false
     */
    default boolean clickElements(By webElement, String webElementAttribute) {
        WebDriverWaits.findElementsWithWait(webElement).forEach(WebElement::click);
        LOGGER.info("[WebDriver Hash: " + driverHash + "] [Click '" + webElementAttribute + "' button]");
        return true;
    }

    /**
     * Extracts element text (e.g. text, link, buttons).
     * Uses the WebElement getText() method for text.
     *
     * @param webElement          the By locator for the element or elements the use is testing
     * @param webElementAttribute the attribute for the tag
     * @return extract text as String
     */
    default String getElementText(By webElement, String webElementAttribute) {
        String elementAttribute = WebDriverWaits.findElementWithWait(webElement, 10, 1)
                                                .getAttribute(webElementAttribute);
        String text = WebDriverWaits.findElementWithVisibilityWait(webElement).getText();
        LOGGER.info("[WebDriver Hash: " + driverHash + "] [Get text '" + text + " for element '" + elementAttribute + "']");
        return text;
    }

    /**
     * Gets a List of WebElements
     * Uses the findElementsWithWait() to locate elements.
     *
     * @param webElements         the By locator for elements
     * @param webElementAttribute the attribute for the tag
     * @return a List of WebElements
     */
    default List<WebElement> getAListOfElements(By webElements, String webElementAttribute) {
        List<WebElement> list = new ArrayList<>(WebDriverWaits.findElementsWithWait(webElements));
        LOGGER.info("[WebDriver Hash: " + driverHash + "] [No. elements found: " + list.size() + " Element text '" + list
                .toString() + "' for element " + webElementAttribute + "]");
        return list;
    }

    /**
     * Gets a Set of WebElements
     * Uses the findElementsWithWait() to locate elements.
     *
     * @param webElements         the By locator for elements
     * @param webElementAttribute the attribute for the tag
     * @return a Set of WebElements
     */
    default Set<WebElement> getASetOfElements(By webElements, String webElementAttribute) {
        Set<WebElement> set = new HashSet<>(WebDriverWaits.findElementsWithWait(webElements));
        LOGGER.info("[WebDriver Hash: " + driverHash + "] [No. elements found: " + set.size() + " Element text '" + set
                .toString() + "' for element " + webElementAttribute + "]");
        return set;
    }
}
