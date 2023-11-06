package com.castoffs.database;

import java.io.File;

import com.castoffs.database.jackbox.JackBoxConfig;
import com.castoffs.database.ship.ShipConfiguration;
import com.castoffs.database.tod.TruthOrDareConfiguration;
import com.castoffs.handler.Dependencies;

public class InformationStorage extends Storage{

    private TruthOrDareConfiguration truthOrDare;
    private ShipConfiguration ship;
    private JackBoxConfig jackBoxConfig;

    public InformationStorage(File directory, Dependencies dependencies) {
        super(directory, dependencies);
    }

    @Override
    public void load() {
        this.truthOrDare = new TruthOrDareConfiguration(new File(directory, "truthOrDare.json"), dependencies);
        this.truthOrDare.startup();

        this.ship = new ShipConfiguration(new File(directory, "ship.json"), dependencies);
        this.ship.startup();

        this.jackBoxConfig = new JackBoxConfig(new File(directory, "jackbox.json"), dependencies);
        this.jackBoxConfig.startup();
    }

    /*
     * get the truth or dare configuration
     */
    public TruthOrDareConfiguration getTruthOrDare() {
        return this.truthOrDare;
    }

    /*
     * get the ship configuration
     */
    public ShipConfiguration getShip() {
        return this.ship;
    }


    
}
