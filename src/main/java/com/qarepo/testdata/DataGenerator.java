package com.qarepo.testdata;

import com.qarepo.utils.JSoupLinkExtractor;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataGenerator {
    private final static String HREF_LINKS_CSV = "./src/main/resources/test-data/site_href_links.csv";

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
}
