package com.kodo.database.users;

import java.io.File;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.kodo.database.UsersConfiguration;
import com.kodo.database.users.scheme.Challenge;
import com.kodo.database.users.scheme.Challenges;
import com.kodo.handler.Dependencies;
import com.kodo.utils.FileUtils;

public class UserCompletedConfiguration extends UsersConfiguration {

    private Challenges challenges;

    public UserCompletedConfiguration(UserConfiguration config, File file, Dependencies dependencies) {
        super(config, file, dependencies);
    }

    @Override
    public void load() {

        String contents = FileUtils.readFile(this.file);
        String gson;
        
        if (contents.isEmpty()) {
            gson = "{}"; // Empty JSON object
            String username = this.config.getUserName();
            this.challenges = this.dependencies.getCodeWars().getApi().getAllCompletedChallenges(username);
            this.save(challenges);
            Logger.getGlobal().warning("set " + username + "completed katas to " + challenges.getChallenges().size() + "");
            return;
        } else {
            gson = contents;
        }
    
        this.challenges = new Gson().fromJson(gson, Challenges.class);
        this.save(challenges);
    }

    @Override
    public Class<?> jsonType() {
        return Challenge.class;
    }
    
    public Challenges getChallenges() {
        return this.challenges;
    }

    public void setChallenges(Challenges challenges) {
        this.challenges = challenges;
    }
    
}
