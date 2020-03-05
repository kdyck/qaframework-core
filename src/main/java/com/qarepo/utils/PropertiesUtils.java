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
package com.qarepo.utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.stream.Collectors;

/*
 * @author Kaci Dyck (kdyck00@gmail.com)
 * @since 1.0.0
 */
public class PropertiesUtils {
    private static final Logger LOGGER = LogManager.getLogger(PropertiesUtils.class);
    private static PropertiesUtils instance = null;

    private PropertiesUtils() {
    }

    public synchronized static PropertiesUtils getInstance() {
        if (instance == null)
            instance = new PropertiesUtils();
        return instance;
    }

    /**
     * @param propertiesFile the directory path for the *.properties file.
     * @param property       the partial or complete String for the property.
     * @return {@code null} if property not found
     * otherwise will return a string value for passed property.
     */
    public String getPropertyByName(String propertiesFile, String property) {
        Properties props = new Properties();
        try {
            LOGGER.log(Level.INFO, "[Loading properties file: " + propertiesFile + "]");
            props.load(new FileInputStream(new File(propertiesFile)));
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.log(Level.ERROR, "Exception occurred while loading properties: " + e.toString());
        }
        LOGGER.log(Level.INFO, "[Getting value for property: " + property + "]");
        return props.getProperty(property);
    }

    /**
     * @param propertiesFile the directory path for the *.properties file.
     * @param property       this is the partial or complete String for the property.
     *                       passing a {@code null} or empty string {@code ""} returns all properties in file.
     * @return a map of keys and values in this properties file
     * where both keys and values are Strings.
     */
    public Map<String, String> getPropertiesAsMap(String propertiesFile, String property) {
        Properties props = new Properties();
        try {
            LOGGER.log(Level.INFO, "[Loading properties file: " + propertiesFile + "]");
            props.load(new FileInputStream(new File(propertiesFile)));
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.log(Level.ERROR, "Exception occurred while loading properties: " + e.toString());
        }
        LOGGER.log(Level.INFO, "[Filter(by key) properties and return as map: " + property + "]");
        return new TreeMap<>(props.entrySet()
                .stream()
                .filter(n -> property == null || property.isEmpty() || n.getKey()
                        .toString()
                        .contains(property))
                .collect(Collectors.toMap(e -> e.getKey().toString()
                        , e -> e.getValue().toString())));
    }
}
