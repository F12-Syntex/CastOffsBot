package com.kodo.database;

import java.io.File;
import java.util.logging.Logger;

import com.kodo.handler.Dependencies;

public abstract class Storage {
    
    protected Dependencies dependencies;
    protected final File directory;

    public Storage(File directory, Dependencies dependencies) {
        this.directory = directory;
        this.dependencies = dependencies;
    }

    public void startup(){
        Logger.getGlobal().info("Loading storage for " + this.getClass().getName());

        //create the directory if it doesn't exist
        if(!directory.exists()){
            if(!directory.mkdir()){
                Logger.getGlobal().severe("could not create directory for " + this.getClass().getSimpleName());
                System.exit(1);
            }
        }

        this.load();
    }

    public abstract void load();

    public File getDirectory() {
        return directory;
    }

}
