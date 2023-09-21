package com.castoffs.database;

import java.io.File;

import com.castoffs.database.tod.TruthOrDareConfiguration;
import com.castoffs.handler.Dependencies;

public class InformationStorage extends Storage{

    private TruthOrDareConfiguration truthOrDare;

    public InformationStorage(File directory, Dependencies dependencies) {
        super(directory, dependencies);
    }

    @Override
    public void load() {
        this.truthOrDare = new TruthOrDareConfiguration(new File(directory, "truthOrDare.json"), dependencies);
        this.truthOrDare.startup();
    }

    /*
     * get the truth or dare configuration
     */
    public TruthOrDareConfiguration getTruthOrDare() {
        return this.truthOrDare;
    }


    
}
