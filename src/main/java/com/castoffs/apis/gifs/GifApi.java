package com.castoffs.apis.gifs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class GifApi {
    
    public static List<String> query(String query) {
        try {
            String giphyAPIKey = "h0Esa8V0S8d6iILz3TZIV0eUs6ap6nhq";
    
            query = "cute " + query;

            String encodedQuery = URLEncoder.encode(query, "UTF-8");

            // Create the URL for the API request
            String urlStr = "https://api.giphy.com/v1/gifs/search?api_key=" + giphyAPIKey + "&q=" + encodedQuery;
    
            // Send the HTTP GET request
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
    
            // Read the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
    
            // Parse the JSON response
            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONArray data = jsonResponse.getJSONArray("data");
    
            // Extract the URLs of the GIFs
            List<String> gifs = new ArrayList<>();
            for (int i = 0; i < data.length(); i++) {
                JSONObject gifObject = data.getJSONObject(i);
                String gifUrl = gifObject.getJSONObject("images").getJSONObject("original").getString("url");
                System.out.println(gifObject);
                gifs.add(gifUrl);
            }
    
            return gifs;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }
    
    
}
