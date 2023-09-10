package com.kodo.database.codewars;

import java.io.File;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.kodo.codewars.scraper.KataInformation;
import com.kodo.database.Configuration;
import com.kodo.handler.Dependencies;
import com.kodo.utils.FileUtils;

public class CodewarsChallenges extends Configuration{

    private KataInformation kataInformation;

    public CodewarsChallenges(File file, Dependencies dependencies) {
        super(file, dependencies);
    }

    @Override
    public void load() {
        String contents = FileUtils.readFile(this.file);
        String gson;
        
        if (contents.isEmpty()) {
            gson = "{}"; // Empty JSON object
            this.kataInformation = new KataInformation();
            this.save(this.kataInformation);
            return;
        } else {
            gson = contents;
        }
    
        this.kataInformation = new Gson().fromJson(gson, KataInformation.class);
        this.save(this.kataInformation);

        Logger.getGlobal().info("Loaded " + this.kataInformation.getChallenges().size() + " katas.");
    }

    @Override
    public Class<?> jsonType() {
        return KataInformation.class;
    }

    public KataInformation getKataInformation() {
        return kataInformation;
    }
}
