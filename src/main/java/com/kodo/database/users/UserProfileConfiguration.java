package com.kodo.database.users;

import java.io.File;

import com.google.gson.Gson;
import com.kodo.database.UsersConfiguration;
import com.kodo.database.users.scheme.User;
import com.kodo.handler.Dependencies;
import com.kodo.utils.FileUtils;

/**
 * this user config stores the users profile data
 */
public class UserProfileConfiguration extends UsersConfiguration{

    private User user;

    public UserProfileConfiguration(UserConfiguration config, File dir, Dependencies dependencies) {
        super(config, dir, dependencies);
    }

    public UserProfileConfiguration(UserConfiguration config, File dir, User user, Dependencies dependencies) {
        super(config, dir, dependencies);
        this.user = user;
    }

    @Override
    public void load() {
        if(user == null){
            String contents = FileUtils.readFile(this.file);
            String gson;
            
            if (contents.isEmpty()) {
                gson = "{}"; // Empty JSON object
            } else {
                gson = contents;
            }
        
            User user = new Gson().fromJson(gson, User.class);
            this.user = user;
        }
    
        this.save(user);
    }

    public void setUser(User user){
        this.user = user;
        this.save(user);
    }

    public User getUser() {
        return user;
    }

    @Override
    public Class<?> jsonType() {
        return User.class;
    }

}
