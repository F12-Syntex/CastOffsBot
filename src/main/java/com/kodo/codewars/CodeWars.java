package com.kodo.codewars;

import java.util.logging.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kodo.database.users.UserConfiguration;
import com.kodo.database.users.UserStorage;
import com.kodo.handler.Dependencies;

import net.dv8tion.jda.api.entities.User;

public class CodeWars {

    private final Dependencies dependencies;
    private final UserStorage userConfiguration;

    public CodeWars(Dependencies dependencies){
        this.dependencies = dependencies;
        this.userConfiguration = dependencies.getStorage().getUserStorage();
    }

    public RegisterResult registerUser(String username){

        String url = "https://www.codewars.com/api/v1/users/" + username;

        //we use httpclient to make a get request to the url and get the response
        //we then parse the response to a json object and check if the user exists
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);

        try {
            
            //send the request
            HttpResponse response = httpClient.execute(request);
            String responseBody = EntityUtils.toString(response.getEntity());

            //parse the response to a json object
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(responseBody, JsonObject.class);

            Logger.getGlobal().info(jsonObject.toString());

            //check if the user exists by checking if the id field exists
            if(jsonObject.has("id")){
                //check if the user is in the clan
                JsonElement clan = jsonObject.get("clan");

                if(clan.isJsonNull() || !clan.getAsString().equals("UKC compSoc")) return RegisterResult.USER_NOT_IN_CLAN;
                
                //check if they have already been registered
                if(userConfiguration.isRegistered(username)) return RegisterResult.USER_ALREADY_REGISTERED;

                //regiser the user to the system
                userConfiguration.registerUser(username);
                return RegisterResult.USER_REGISTERED;
            }

            return RegisterResult.USER_NOT_FOUND;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return RegisterResult.SERVER_ERROR;
    }
    
}
