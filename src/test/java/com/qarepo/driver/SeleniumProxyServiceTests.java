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
package com.qarepo.driver;

import com.qarepo.driver.proxy.Proxy;
import com.qarepo.driver.proxy.SeleniumProxyService;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * <p>
 * The main objective of this class is to provide tests
 * for the {@link SeleniumProxyService} implementations.
 * </p>
 *
 * @author Kaci Dyck (kdyck00@gmail.com)
 * @since 1.0.0
 */
public class SeleniumProxyServiceTests {
    private SeleniumProxyService service;

    private SeleniumProxyServiceTests() {
    }

    @Test
    public void randomProxyValueNotNull() {
        service = new SeleniumProxyService();
        Proxy randomProxyValue = service.getRandomProxy();
        Assert.assertNotNull(randomProxyValue.toString(), "SeleniumProxy=" + randomProxyValue.toString());
    }

    @Test
    void proxyListSizeIsGreaterThanZero() {
        service = new SeleniumProxyService();
        List<Proxy> proxyList = service.getProxyList();
        Assert.assertTrue(proxyList.size() <= 20);
    }

    @Test
    public void randomProxyValueIsUnique() {
        service = new SeleniumProxyService();
        Proxy randomProxyOne = service.getRandomProxy();
        Proxy randomProxyTwo = service.getRandomProxy();
        if (!randomProxyOne.toString().equalsIgnoreCase(randomProxyTwo.toString())) {
            Assert.assertTrue(true
                    , "SeleniumProxyValueOne=" + randomProxyOne.toString()
                            + "SeleniumProxyValueTwo=" + randomProxyTwo.toString());
        }
    }
}
