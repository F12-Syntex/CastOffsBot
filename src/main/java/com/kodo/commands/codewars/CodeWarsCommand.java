package com.kodo.commands.codewars;

import java.awt.Color;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kodo.codewars.CodeWars;
import com.kodo.commands.Command;
import com.kodo.database.users.scheme.Challenge;
import com.kodo.database.users.scheme.Challenges;
import com.kodo.database.users.scheme.User;
import com.kodo.embeds.EmbedMaker;
import com.kodo.embeds.PagedEmbed;
import com.kodo.handler.Dependencies;

import net.dv8tion.jda.api.EmbedBuilder;

//TODO: sort by difficulty
public abstract class CodeWarsCommand extends Command{

    public CodeWarsCommand(Dependencies dependencies) {
        super(dependencies);
    }

    protected PagedEmbed getProfileEmbed(User profile, Challenges challenges){
        EmbedBuilder embedBuilder = new EmbedBuilder();

        List<Challenge> completed = challenges.getChallenges();

        completed.sort((a, b) -> {
            int aRank = a.getKataInformation().getRank().getDifficulty();
            int bRank = b.getKataInformation().getRank().getDifficulty();
            return aRank - bRank;
        });

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(profile, User.class);

        if (json != null) {
            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
            JsonObject overall = jsonObject.getAsJsonObject("ranks").getAsJsonObject("overall");
            
            embedBuilder.setTitle("Success!");
            embedBuilder.setColor(Color.green);
            embedBuilder.setThumbnail(this.dependencies.getDiscord().getSelfUser().getAvatarUrl());

            String ranking = jsonObject.get("leaderboardPosition").getAsString();

            embedBuilder.addField("Username", "`" +  profile.getUsername() + "`", true);
            embedBuilder.addField("Honor", "`" +  jsonObject.get("honor").getAsString() + "`", true);
            embedBuilder.addField("Clan", "`" +  jsonObject.get("clan").getAsString() + "`", true);
            if(ranking != null && ranking != "0") embedBuilder.addField("Ranking", "`" +  "#" + ranking + "`", true);
            embedBuilder.addField("Rank", "`" + overall.get("rank").getAsString() + "`", true);
            embedBuilder.addField("Score", "`" + overall.get("score").getAsString() + "`", true); 
            
            if(challenges.getMostDifficultChallenge().isPresent()){
                Challenge challenge = challenges.getMostDifficultChallenge().get();
                embedBuilder.addField("Best Kata (" + challenge.getKataInformation().getRank().getDifficulty() + " Kyu)", "```" + challenge.getName() + System.lineSeparator() + "```    ", true);   
            }          
        }

        
        PagedEmbed paged = new PagedEmbed();
        paged.appendPages(embedBuilder);

        System.out.println("Challenges: " + completed.size());

        int AMOUNT = 30;
        int pos = 0;

        int lengthOfNumber = String.valueOf(completed.size()).length();
        

        for(int i = 0; i < completed.size(); i+=AMOUNT){

            EmbedBuilder builder = new EmbedBuilder();

            builder.setTitle("Finished katas.");
            builder.setColor(Color.green);
            builder.setThumbnail(this.dependencies.getDiscord().getSelfUser().getAvatarUrl());

            for(int j = i; j < i + AMOUNT; j++){
                if(j >= completed.size()) break;
                Challenge challenge = completed.get(j);
                String indexString = String.format("%0" + lengthOfNumber + "d", ++pos);

                builder.appendDescription("`" + indexString + "`" 
                                            + ": [" + challenge.getName() + "](" + "https://www.codewars.com/kata/" + challenge.getId() + ")" 
                                            + " `" + challenge.getKataInformation().getRank().getDifficulty() + " kyu`"
                                            + System.lineSeparator()
                                            );
            }

            paged.appendPages(builder);
        }

        return paged;
    }

    protected EmbedBuilder getProfileNotExistEmbed(net.dv8tion.jda.api.entities.User profile) {
        return EmbedMaker.ERROR(profile, "User data not found", "The user's data could not be found. Try setting `api_search` to `true` which will look up data through the API.");
    }

    public CodeWars getCodeWars(){
        return this.dependencies.getCodeWars();
    }

}
