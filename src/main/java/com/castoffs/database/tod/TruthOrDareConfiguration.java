package com.castoffs.database.tod;

import java.io.File;

import com.castoffs.database.Configuration;
import com.castoffs.handler.Dependencies;
import com.castoffs.utils.FileUtils;
import com.google.gson.Gson;

public class TruthOrDareConfiguration extends Configuration{

    private TruthOrDare truthOrDare;

    public TruthOrDareConfiguration(File file, Dependencies dependencies) {
        super(file, dependencies);
    }

    @Override
    public void load() {


        String contents = FileUtils.readFile(this.file);
        String gson;
        
        if (contents.isEmpty()) {
            gson = "{}"; // Empty JSON object
            this.truthOrDare = new TruthOrDare();
            this.save(this.truthOrDare);
            return;
        } else {
            gson = contents;
        }
    
        this.truthOrDare = new Gson().fromJson(gson, TruthOrDare.class);
    }

    @Override
    public Class<?> jsonType() {
        return TruthOrDare.class;
    }

    /**
     * get the trurh or dare configuration
     */
    public TruthOrDare getTruthOrDare() {
        return this.truthOrDare;
    }

    public void addTruth(String truthOption) {
        this.truthOrDare.addTruth(truthOption);
        this.save(this.truthOrDare);
    }

    public void addDare(String dareOption) {
        this.truthOrDare.addDare(dareOption);
        this.save(this.truthOrDare);
    }
    
}
