package com.castoffs.database;

import java.io.File;
import java.util.logging.Logger;

import com.castoffs.handler.Dependencies;

/**
 * This class is used to load and save storage files, which are used to store config files or other storage files
 * represented by folders
 */
public abstract class Storage {
    
    protected Dependencies dependencies;
    protected final File directory;

    /**
     * @param directory
     * @param dependencies
     */
    public Storage(File directory, Dependencies dependencies) {
        this.directory = directory;
        this.dependencies = dependencies;
    }

    /**
     * create the directory if it doesn't exist, then load the storage
     */
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

    /**
     * This method is called to load the storage
     * typically loads all the sub configs / storage files
     */
    public abstract void load();

    /**
     * This method is called to get the directory of the storage
     * @return
     */
    public File getDirectory() {
        return directory;
    }

}
