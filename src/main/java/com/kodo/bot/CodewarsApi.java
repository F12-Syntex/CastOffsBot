package com.kodo.bot;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kodo.codewars.CodeWars;
import com.kodo.database.users.scheme.Challenge;
import com.kodo.database.users.scheme.Challenges;
import com.kodo.database.users.scheme.User;

public class CodewarsApi {

    private final CodeWars codewars;

    public CodewarsApi(CodeWars codewars) {
        this.codewars = codewars;   
    }

    public User getProfileData(String username){

        String url = "https://www.codewars.com/api/v1/users/" + username;
        try {

            //we use httpclient to make a get request to the url and get the response
            //we then parse the response to a json object and check if the user exists
            HttpClient httpClient = HttpClients.custom()
                                            .setUserAgent("Mozilla/5.0")
                                            .disableCookieManagement().build();

            HttpGet request = new HttpGet(url);

            //send the request
            HttpResponse response = httpClient.execute(request);
            String responseBody = EntityUtils.toString(response.getEntity());

            //parse the response to a json object
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(responseBody, JsonObject.class);

            //check if the user exists by checking if the id field exists
            if(jsonObject.has("id")){
                User user = gson.fromJson(jsonObject, User.class);
                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        throw new RuntimeException("User not found");
    }

    public void reCacheChallenges(String username){

        final String USER = username;
        
        int page = 0;
        int lastPage = 1;

        Challenges challenges = this.codewars.getUserStorage().getUser(username).getCompletedKatas().getChallenges();
        
        while(page < lastPage){
            String url = "https://www.codewars.com/api/v1/users/" + USER + "/code-challenges/completed?page=" + page;

            if(challenges.isPageCached(page) && (page+1) < lastPage){
                continue;
            }

            try {

                //we use httpclient to make a get request to the url and get the response
                //we then parse the response to a json object and check if the user exists
                HttpClient httpClient = HttpClients.custom()
                                            .setUserAgent("Mozilla/5.0")
                                            .disableCookieManagement().build();

                HttpGet request = new HttpGet(url);

                //send the request
                HttpResponse response = httpClient.execute(request);
                String responseBody = EntityUtils.toString(response.getEntity());

                //parse the response to a json object
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(responseBody, JsonObject.class);

                lastPage = jsonObject.get("totalPages").getAsInt();

                JsonArray array = jsonObject.get("data").getAsJsonArray();

                final int PAGE = page;

                array.forEach(element -> {
                    
                    //get the json object
                    JsonObject object = element.getAsJsonObject();
                    
                    //convert it to a challenge
                    Challenge challenge = gson.fromJson(object, Challenge.class);

                    if(challenges.containsChallenge(challenge.getId())){
                        return;
                    }

                    //set the page
                    challenges.addChallenge(challenge, PAGE);
                });

                challenges.addCachedPage(PAGE);

                page += 1;

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public Challenges getAllCompletedChallenges(String username) {

        final String USER = username;
        
        int page = 0;
        int lastPage = 1;

        Challenges challenges = new Challenges();

        while(page < lastPage){
            String url = "https://www.codewars.com/api/v1/users/" + USER + "/code-challenges/completed?page=" + page;

            try {

                //we use httpclient to make a get request to the url and get the response
                //we then parse the response to a json object and check if the user exists
                HttpClient httpClient = HttpClients.custom()
                                            .setUserAgent("Mozilla/5.0")
                                            .disableCookieManagement().build();
                                            
                HttpGet request = new HttpGet(url);

                //send the request
                HttpResponse response = httpClient.execute(request);
                String responseBody = EntityUtils.toString(response.getEntity());

                //parse the response to a json object
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(responseBody, JsonObject.class);

                lastPage = jsonObject.get("totalPages").getAsInt();

                JsonArray array = jsonObject.get("data").getAsJsonArray();

                final int PAGE = page;

                array.forEach(element -> {
                    
                    //get the json object
                    JsonObject object = element.getAsJsonObject();
                    
                    //convert it to a challenge
                    Challenge challenge = gson.fromJson(object, Challenge.class);

                    //set the page
                    challenges.addChallenge(challenge, PAGE);
                });

                challenges.addCachedPage(PAGE);

                page += 1;

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return challenges;    
    }
    
}
