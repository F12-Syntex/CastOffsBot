package com.castoffs.database;

import java.io.File;
import java.util.logging.Logger;

import com.castoffs.bot.Settings;
import com.castoffs.handler.Dependencies;

/**
 * This class is used to load and save storage files, which are used to store config files or other storage files
 */
public class StorageManager {

    private final File rootDirectory = new File(Settings.NAME);

    private final Dependencies dependencies;

    private InformationStorage informationStorage;

    public StorageManager(Dependencies dependencies) {
        this.dependencies = dependencies;

    }

    /**
     * create the root directory if it doesn't exist
     */
    public void startup(){  

        //create the root directory if it doesn't exist
        if(!rootDirectory.exists()){
            if(!rootDirectory.mkdir()){
                Logger.getGlobal().severe("could not create root directory for " + Settings.NAME);
                System.exit(1);
            }
        }   

        this.informationStorage = new InformationStorage(new File(rootDirectory, "information"), dependencies);
        this.informationStorage.startup();
    }

    /**
     * get dependencies
     * @return
     */
    public Dependencies getDependencies() {
        return dependencies;
    }

    /**
     * get the information storage
     * @return
     */
    public InformationStorage getInformationStorage() {
        return informationStorage;
    }
}
