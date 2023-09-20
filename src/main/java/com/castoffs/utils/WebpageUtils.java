package com.castoffs.utils;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class WebpageUtils {

    /**
     * Reads the content of a web page located at the specified URL.
     *
     * @param url The URL of the web page to be read.
     * @return A string containing the HTML content of the web page.
     */
    public static String readWebPage(String url) {
        String html = "";

        try (CloseableHttpClient httpClient = HttpClients.custom().setUserAgent("Mozilla/5.0")
                .disableCookieManagement().build()) {

            HttpGet httpGet = new HttpGet(url);

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    html = EntityUtils.toString(entity);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return html;
    }
}
