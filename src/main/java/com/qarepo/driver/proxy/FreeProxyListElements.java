/*
 * (C) Copyright 2020 qarepo.com (https://qarepo.com/)
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
package com.qarepo.driver.proxy;

import org.openqa.selenium.By;

/**
 * This class designates the locators used in {@link SeleniumProxyService} method implementations.
 * It uses Selenium WebDriver's {@link By} locator and XPath expressions to
 * get relevant [https://free-proxy-list.net/] elements.
 *
 * @since 1.0.0
 */

class FreeProxyListElements {
    /**
     * Prevents instantiation
     */
    private FreeProxyListElements() {
    }

    /**
     * @return {@link By} Locator element of the https drop-down 'yes' option
     */
    static By dropDown_FilterHttps() {
        return By.xpath("(//th[7]//select/option)[3]");
    }

    /**
     * @return {@link By} Locator list of IP Addresses
     */
    static By list_IpAddresses() {
        return By.xpath("//table[@id='proxylisttable']//td[1]");
    }

    /**
     * @return {@link By} Locator list of Ports
     */
    static By list_Ports() {
        return By.xpath("//table[@id='proxylisttable']//td[2]");
    }
}
