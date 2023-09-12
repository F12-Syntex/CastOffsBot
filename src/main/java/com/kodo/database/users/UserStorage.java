package com.kodo.database.users;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import com.kodo.database.Storage;
import com.kodo.database.users.scheme.User;
import com.kodo.handler.Dependencies;

public class UserStorage extends Storage{

    public Map<String, UserConfiguration> users = new HashMap<>();

    public UserStorage(File directory, Dependencies dependencies) {
        super(directory, dependencies);
    }

    @Override
    public void load() {
        for(File file : getDirectory().listFiles()) {
            if(file.isDirectory()){
                Logger.getGlobal().info("registering user: " + file.getName());
                this.registerUser(file.getName(), null);
            }
        }
    }

    public Collection<UserConfiguration> getUsers(){
        return this.users.values();
    }

    public UserConfiguration registerUser(String userName, User user) {
        File userDirectory = new File(getDirectory(), userName);
        
        UserConfiguration config = new UserConfiguration(userDirectory, userName, dependencies);
        config.startup();
        
        if (user != null) {
            config.getProfile().setUser(user);
        }
    
        users.put(userName, config);
        return config;
    }
    

    public Optional<UserConfiguration> getUserData(String username){
        if(!users.containsKey(username)) return Optional.empty();
        return Optional.of(users.get(username));
    }
    

    public boolean isRegistered(String userId){
        return users.containsKey(userId);
    }

    public UserConfiguration getUser(String userId){
        return users.get(userId);
    }


}
