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

import com.qarepo.driver.WebDriverRunner;
import com.qarepo.driver.WebDriverThreadManager;
import com.qarepo.driver.WebDriverWaits;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * <p>
 * The main objective of this {@link SeleniumProxyService} class is to provide method implementations
 * for the {@link ProxyFinder} interface. The methods in this class are used for obtaining proxy
 * Host IP Address(es) and Port(s) from https://free-proxy-list.net/ by {@link FreeProxyListElements} locators.
 * <br>
 * The {@link #getProxyList()} method utilizes {@link WebDriverWaits} FluentWaits {@link WebDriverWaits#findElementWithClickableWait(By)}
 * and {@link WebDriverWaits#findElementWithVisibilityWait(By)} method utils for polling and timeouts.
 * Each method waits 10s with 1s polling, if a specified element is found in document the action is executed.
 * Calling {@link #getRandomProxy()} is a simple way to obtain a random {@link Proxy} with a valid connection.
 * </p>
 *
 * @author Kaci Dyck (kdyck00@gmail.com)
 * @since 1.0.0
 */
public class SeleniumProxyService implements ProxyFinder {

    private static final Logger LOGGER = LogManager.getLogger(SeleniumProxyService.class);
    private String browser;
    private String url;

    public SeleniumProxyService() {
        this.browser = "Google Chrome Headless";
        this.url = "https://free-proxy-list.net/";
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return {@link Proxy} formatted as 'https://012.345.678.910:0000'
     */
    @Override
    public Proxy getRandomProxy() {
        List<Proxy> proxyList = getProxyList();
        Proxy proxyInfo = proxyList.get(ThreadLocalRandom.current().nextInt(1, proxyList.size()));
        LOGGER.info("[PROXY_URL: " + proxyInfo + "]");
        return proxyInfo;
    }

    /**
     * <p>
     * Starts WebDriver thread with browser "GOOGLE CHROME HEADLESS" and gets https://free-proxy-list.net
     * Calls {@link #setProtocol()} method to set https protocol, gets {@link  List<String>} ipAddresses and ports
     * then check for valid proxy connections. Valid connections are added to {@link List<Proxy>} the WebDriver is stopped.
     * </p>
     *
     * @return a {@link List<Proxy>} of proxies
     */
    @Override
    public List<Proxy> getProxyList() {
        WebDriverRunner driver = new WebDriverRunner();
        List<Proxy> proxyList;
        try {
            driver.startWebDriver(browser);
            WebDriverThreadManager.getDriver().get(url);
            setProtocol();
            proxyList = new ArrayList<>();
            List<String> ipAddresses = WebDriverWaits.findElementsWithWait(FreeProxyListElements.list_IpAddresses())
                                                     .stream()
                                                     .map(WebElement::getText)
                                                     .collect(Collectors.toList());
            List<String> ports = WebDriverWaits.findElementsWithWait(FreeProxyListElements.list_Ports())
                                               .stream()
                                               .map(WebElement::getText)
                                               .collect(Collectors.toList());
            if (ipAddresses.size() == ports.size()) {
                for (int i = 0; i < ipAddresses.size(); i++) {
                    Proxy proxy = new Proxy("https", ipAddresses.get(i), ports.get(i));
                    if (pingHost(proxy))
                        proxyList.add(proxy);
                    LOGGER.info("[PROXY_LIST_SIZE: " + proxyList.size() + "]");
                }
            }
        } finally {
            driver.stopWebDriver();
        }
        return proxyList;
    }

    /**
     * Sets 'https' protocol via the UI by
     * clicking https 'yes' option.
     */
    private void setProtocol() {
        LOGGER.info("Select HTTPS filter option: 'yes'");
        WebDriverWaits.findElementWithClickableWait(FreeProxyListElements.dropDown_FilterHttps()).click();
    }

    /**
     * Attempts to resolve connections for proxy
     *
     * @return true for valid connections.
     */
    private boolean pingHost(Proxy proxy) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(proxy.getIp(), Integer.parseInt(proxy.getPort())), 2000);
            return true;
        } catch (IOException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            LOGGER.error(sw.toString());
            return false;
        }
    }
}
