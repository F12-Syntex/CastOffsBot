package com.kodo.database;

import java.io.File;

import com.kodo.database.users.UserConfiguration;
import com.kodo.handler.Dependencies;

public abstract class UsersConfiguration extends Configuration {

    protected UserConfiguration config;

    public UsersConfiguration(UserConfiguration config, File file, Dependencies dependencies) {
        super(file, dependencies);
        this.config = config;
    }

    public UserConfiguration getUserConfiguration() {
        return config;
    }

    public void setUserConfiguration(UserConfiguration userConfiguration) {
        this.config = userConfiguration;
    }

    
}
