package com.kodo.database.users;

import java.io.File;

import com.kodo.database.Storage;
import com.kodo.handler.Dependencies;
import com.kodo.utils.DirectoryUtils;

/**
 * this config will store an indivisual users' data
 */
public class UserConfiguration extends Storage{

    private final String USER_ID;

    private UserProfileConfiguration profile;
    private UserCompletedConfiguration completedKatas;

    public UserConfiguration(File directory, String userId, Dependencies dependencies) {
        super(directory, dependencies);
        this.USER_ID = userId;   

        //initialize all the sub configs for the user
        this.profile = new UserProfileConfiguration(this, DirectoryUtils.directoryBuilder(directory, "codewars_profile.json"), dependencies);
        this.completedKatas = new UserCompletedConfiguration(this, DirectoryUtils.directoryBuilder(directory, "completed_katas.json"), dependencies);
    }

    public void update(){
        this.profile.update();
        this.completedKatas.update();
    }

    @Override
    public void load() {
        
        //startup all the sub configs
        this.profile.startup();
        this.completedKatas.startup();
    }

    public String getUserName() {
        return USER_ID;
    }

    public UserProfileConfiguration getProfile() {
        return profile;
    }

    public UserCompletedConfiguration getCompletedKatas() {
        return completedKatas;
    }
    
}
