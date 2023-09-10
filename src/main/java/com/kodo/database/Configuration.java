package com.kodo.database;

import java.io.File;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kodo.database.users.UserConfiguration;
import com.kodo.handler.Dependencies;
import com.kodo.utils.FileUtils;

public abstract class Configuration {
    
    protected final File file;
    protected UserConfiguration config;
    protected Dependencies dependencies;

    public Configuration(UserConfiguration config, File file, Dependencies dependencies) {  
        this.file = file;
        this.config = config;
        this.dependencies = dependencies;
    }

    public void startup(){
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

    public void save(Object obj) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(obj);
        FileUtils.writeFile(this.file, jsonString);
    }
    
    private void failedToLoadFile(){
        Logger.getGlobal().severe("could not create directory for " + this.getClass().getSimpleName());
        System.exit(1);
    }

    public abstract void load();
    public abstract Class<?> jsonType();

    public File getFile() {
        return file;
    }

}
