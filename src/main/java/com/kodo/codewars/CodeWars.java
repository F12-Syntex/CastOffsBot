package com.kodo.codewars;

import com.kodo.bot.Settings;
import com.kodo.database.users.UserProfileConfiguration;
import com.kodo.database.users.UserStorage;
import com.kodo.database.users.scheme.User;
import com.kodo.handler.Dependencies;

/*
 * this is a facade class for dealing with codewars related functions
 */
public class CodeWars {

    private final Dependencies dependencies;
    private final UserStorage userConfiguration;

    public CodeWars(Dependencies dependencies){
        this.dependencies = dependencies;
        this.userConfiguration = dependencies.getStorage().getUserStorage();
    }

    public void registerUser(String username){
        if(userConfiguration.isRegistered(username)) throw new IllegalArgumentException("User is already registered");

        User data = UserProfileConfiguration.getProfileData(username);

        if(data.getClan() == null || !data.getClan().equals(Settings.CLAN_NAME)) throw new IllegalArgumentException("User is not in the clan");

        userConfiguration.registerUser(username, data);
    }

    public User getUserProfile(String username){
        return this.userConfiguration.getUser(username).getProfile().getUser();
    }

    public UserStorage getUserStorage() {
        return userConfiguration;
    }

    public Dependencies getDependencies() {
        return dependencies;
    }
}
