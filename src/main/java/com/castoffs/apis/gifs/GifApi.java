package com.castoffs.apis.gifs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class GifApi {
    
    public static List<String> query(String query) {
        try {

            String encodedQuery = URLEncoder.encode(query, "UTF-8");

            // Create the URL for the API request
            String urlStr = "https://api.otakugifs.xyz/gif?reaction=" + encodedQuery;
    
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
            boolean containsGif = jsonResponse.has("url");

            List<String> gifs = new ArrayList<>();

            if(!containsGif){
                return gifs;
            }

            gifs.add(jsonResponse.getString("url"));

            return gifs;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }
    
    
}
