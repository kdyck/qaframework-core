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

/**
 * <p>
 * This {@link Proxy} POJO class provides constructor, getters(), setters() and toString() methods
 * used to obtain proxy Host IP Address(es) and Port(s) from https://free-proxy-list.net/.
 * </p>
 *
 * @since 1.0.0
 */
public class Proxy {
    String protocol;
    String ip;
    String port;

    public Proxy(String protocol, String ip, String port) {
        this.protocol = protocol;
        this.ip = ip;
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return protocol + "://" + ip + ":" + port;
    }
}
