package com.castoffs.database;

import java.io.File;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.castoffs.handler.Dependencies;
import com.castoffs.utils.FileUtils;

/**
 * This class is used to load and save configuration files
 */
public abstract class Configuration {
    
    protected final File file;
    protected Dependencies dependencies;

    public Configuration(File file, Dependencies dependencies) {  
        this.file = file;;
        this.dependencies = dependencies;
    }

    /**
     * This method is called to load the configuration file
     */
    public void startup(){

        Logger.getGlobal().info("Loading " + this.getClass().getSimpleName() + " from " + this.file.getAbsolutePath());

        //create the directory if it doesn't exist
        if(!file.exists()){
            try{
                if(!file.createNewFile()){
                    failedToLoadFile();
                }
            }catch(Exception e){
                failedToLoadFile();
            }
        }

        this.load();
    }

    /**
     * This method is called to save the configuration file
     * @param obj
     */
    public void save(Object obj) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(obj);
        FileUtils.writeFile(this.file, jsonString);
    }
    
    /**
     * call if the file could not be loaded, sends an error message and exits the program
     */
    private void failedToLoadFile(){
        Logger.getGlobal().severe("could not create directory for " + this.getClass().getSimpleName());
        System.exit(1);
    }

    public abstract void load();
    public abstract Class<?> jsonType();

    /**
     * get the configuration file
     * @return
     */
    public File getFile() {
        return file;
    }
}

