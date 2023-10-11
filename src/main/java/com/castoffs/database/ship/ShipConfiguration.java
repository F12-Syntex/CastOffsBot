package com.castoffs.database.ship;

import java.io.File;

import com.castoffs.database.Configuration;
import com.castoffs.handler.Dependencies;
import com.castoffs.utils.FileUtils;
import com.google.gson.Gson;

public class ShipConfiguration extends Configuration{

    private ShipData shipData;

    public ShipConfiguration(File file, Dependencies dependencies) {
        super(file, dependencies);
    }

    @Override
    public void load() {

        String contents = FileUtils.readFile(this.file);
        // String contents = HtmlUtils.getHtml("https://raw.githubusercontent.com/F12-Syntex/CastOffsBot/master/Castoffs/information/truthOrDare.json");
        String gson;

        System.out.println(contents);
        
        if (contents.isEmpty()) {
            gson = "{}"; // Empty JSON object
            this.shipData = new ShipData();
            this.save(this.shipData);
            return;
        } else {
            gson = contents;
        }
    
        
        this.shipData = new Gson().fromJson(gson, ShipData.class);
    }

    public void updateConfig() {
        this.save(this.shipData);
    }

    @Override
    public Class<?> jsonType() {
        return ShipData.class;
    }

    public ShipData getShipData() {
        return this.shipData;
    }

    
}
