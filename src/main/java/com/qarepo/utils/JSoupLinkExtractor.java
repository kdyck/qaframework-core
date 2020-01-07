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
package com.qarepo.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * @author Kaci Dyck (kdyck00@gmail.com)
 * @since 1.0.0
 */
public class JSoupLinkExtractor {
    private static final Logger LOGGER = LogManager.getLogger(JSoupLinkExtractor.class);
    private static StringWriter sw = new StringWriter();
    private String url;

    public JSoupLinkExtractor(String url) {
        this.url = url;
    }

    public void writeDataArrayToCSV(List<String[]> data, String filename) {
        File csvOutFile = new File(filename).getAbsoluteFile();
        System.out.println(csvOutFile);
        try (PrintWriter pw = new PrintWriter(csvOutFile)) {
            data.stream()
                .map(JSoupLinkExtractor::convertDataArrayToCSV)
                .distinct()
                .forEach(pw::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace(new PrintWriter(sw));
            LOGGER.error("Error occurred while finding file " + filename
                    + "\n" + sw.toString());
        }
    }

    public static String convertDataArrayToCSV(String[] data) {
        return Stream.of(data)
                     .map(JSoupLinkExtractor::escapeSpecialCharacters)
                     .collect(Collectors.joining(","));
    }

    public List<String[]> convertLinksToDataArray() {
        Document doc;
        Elements aTags, imgTags, linkTags;
        List<String[]> data = new ArrayList<>();
        try {
            doc = Jsoup.connect(url).get();
            aTags = doc.select("a[href]");
            imgTags = doc.select("[src]");
            linkTags = doc.select("link[href]");

            aTags.removeIf(e -> e.toString().contains("javascript"));
            imgTags.removeIf(e -> !e.tagName().contains("img"));

            for (Element aTag : aTags) {
                String aTagHref = aTag.attr("abs:href").trim();
                data.add(new String[]{aTagHref});
            }
            for (Element imgTag : imgTags) {
                String imgSrcs = imgTag.attr("abs:src");
                data.add(new String[]{imgSrcs});
            }
            for (Element linkTag : linkTags) {
                String linkHrefs = linkTag.attr("abs:href");
                data.add(new String[]{linkHrefs});
            }
        } catch (IOException e) {
            LOGGER.error(e.toString());
        }
        return data;
    }

    private static String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", "");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }
}
