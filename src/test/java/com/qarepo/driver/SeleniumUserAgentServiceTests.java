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

import com.qarepo.driver.useragent.JSoupUserAgentService;
import com.qarepo.driver.useragent.SeleniumUserAgentService;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * <p>
 * The main objective of this class is to provide
 * test for the {@link JSoupUserAgentServiceTests}.
 * </p>
 *
 * @author Kaci Dyck (kdyck00@gmail.com)
 * @since 1.0.0
 */
public class SeleniumUserAgentServiceTests {
    private SeleniumUserAgentService service;

    private SeleniumUserAgentServiceTests() {
    }

    @Test
    public void randomProxyValueNotNull() {
        service = new SeleniumUserAgentService();
        String randomUserAgent = service.getRandomUserAgent();
        Assert.assertNotNull(randomUserAgent, "RandomSeleniumUserAgent=" + randomUserAgent);
    }

    @Test
    public void randomProxyValueIsUnique() {
        service = new SeleniumUserAgentService();
        String randomUserAgentOne = service.getRandomUserAgent();
        String randomUserAgentTwo = service.getRandomUserAgent();
        if (!randomUserAgentOne.equalsIgnoreCase(randomUserAgentTwo)) {
            Assert.assertTrue(true
                    , "RandomSeleniumUserAgentValueOne=" + randomUserAgentOne
                            + "RandomSeleniumUserAgentValueTwo=" + randomUserAgentOne);
        }
    }
}
