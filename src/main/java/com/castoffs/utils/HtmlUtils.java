package com.castoffs.utils;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class HtmlUtils {

public static String getHtml(String url) {
        // Create an HttpClient with a custom user agent
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3")
                .build();

        try {
            // Create an HttpGet request with the specified URL
            HttpGet request = new HttpGet(url);

            // Execute the request and get the response
            HttpResponse response = httpClient.execute(request);

            // Read the HTML content from the response
            String htmlContent = EntityUtils.toString(response.getEntity());

            // Return the HTML content
            return htmlContent;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    
}
