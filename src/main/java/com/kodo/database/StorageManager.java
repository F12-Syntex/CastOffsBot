package com.kodo.database;

import java.io.File;
import java.util.logging.Logger;

import com.kodo.bot.Settings;
import com.kodo.database.users.UserStorage;
import com.kodo.handler.Dependencies;
import com.kodo.utils.DirectoryUtils;

public class StorageManager {

    private final File rootDirectory = new File(Settings.NAME);
    
    //represents the storage section for all user data
    private UserStorage userStorage;
    private final Dependencies dependencies;

    public StorageManager(Dependencies dependencies) {
        this.dependencies = dependencies;
    }

    public void startup(){  

        //create the root directory if it doesn't exist
        if(!rootDirectory.exists()){
            if(!rootDirectory.mkdir()){
                Logger.getGlobal().severe("could not create root directory for " + Settings.NAME);
                System.exit(1);
            }
        }   
        
        //load user data
        this.userStorage = new UserStorage(DirectoryUtils.directoryBuilder(rootDirectory, "users"), dependencies);
        this.userStorage.startup();
    }

    public UserStorage getUserStorage() {
        return userStorage;
    }
    
}
