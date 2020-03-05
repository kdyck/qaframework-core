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
package com.qarepo.testdata;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.qarepo.TestNGListener;
import com.qarepo.utils.JSoupLinkExtractor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class DataGenerator implements AutoCloseable {
    private static final Logger LOGGER = LogManager.getLogger(DataGenerator.class);
    private final static String HREF_LINKS_CSV = "./src/main/resources/test-data/site_href_links.csv";
    private static StringWriter sw = new StringWriter();
    private String filePath;

    public DataGenerator(String filePath) {
        this.filePath = filePath;
    }

    @DataProvider(name = "getLinksFromCSV")
    public static Object[][] getLinksFromCSV(ITestContext itc) {
        String browser = itc.getCurrentXmlTest().getAllParameters().get("browser");
        String url = itc.getCurrentXmlTest().getAllParameters().get("URL");

        JSoupLinkExtractor linkExtractor = new JSoupLinkExtractor(url);
        linkExtractor.writeDataArrayToCSV(linkExtractor.convertLinksToDataArray(), HREF_LINKS_CSV);
        List<String> allUrls = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(new File(HREF_LINKS_CSV)))) {
            String line;
            while ((line = br.readLine()) != null) {
                allUrls.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Object[][] data = new Object[allUrls.size()][2];
        for (int i = 0; i < allUrls.size(); i++) {
            data[i][0] = browser;
            data[i][1] = allUrls.get(i);
        }
        return data;
    }

    /**
     * Uses a stream to iterate over Csv values
     *
     * @return a {@link List} of {@link Map} of {@link String} file contents
     */
    public List<Map<String, String>> readCsv() {
        LOGGER.info("Starting CSV Reader...");
        List<Map<String, String>> csvAsList = Collections.synchronizedList(new LinkedList<>());
        MappingIterator<Map<String, String>> mappingIterator;
        try {
            CsvMapper csvMapper = new CsvMapper();
            CsvSchema csvSchema = CsvSchema.emptySchema().withHeader();
            LOGGER.info("CSV Filename: " + filePath);
            mappingIterator = csvMapper.readerFor(Map.class)
                                       .with(csvSchema)
                                       .readValues(new File(filePath));
            synchronized (csvAsList) {
                while (mappingIterator.hasNext()) {
                    csvAsList.add(mappingIterator.next());
                }
            }
        } catch (IOException e) {
            e.printStackTrace(new PrintWriter(sw));
            LOGGER.error(" " + sw.toString());
        }
        LOGGER.info("Ending CSV Reader.");
        return csvAsList;
    }

    @Override
    public void close() {
        LOGGER.info("Starting File Finder...");
        try {
            List<Path> filesInFolder = Files.walk(Paths.get(filePath)).filter(Files::isRegularFile)
                                            .collect(Collectors.toList());
            LOGGER.info("Files in Folder: " + filesInFolder);
            if (!(filesInFolder.isEmpty())) {
                for (Path found_File : filesInFolder) {
                    found_File.toFile().deleteOnExit();
                    LOGGER.info("Deleting Files: " + found_File);
                }
            }
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            LOGGER.error("Exception occurred while finding file! Exception: " + sw.toString());
        }
        LOGGER.info("Ending File Finder.");
    }
}
