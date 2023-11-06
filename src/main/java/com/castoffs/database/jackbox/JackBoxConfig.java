package com.castoffs.database.jackbox;

import java.io.File;

import com.castoffs.database.Configuration;
import com.castoffs.database.ship.ShipData;
import com.castoffs.handler.Dependencies;
import com.castoffs.utils.FileUtils;
import com.google.gson.Gson;

public class JackBoxConfig extends Configuration{

    private JackBoxConfigurationData data;

    public JackBoxConfig(File file, Dependencies dependencies) {
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
            this.data = new JackBoxConfigurationData();
            this.save(this.data);
            return;
        } else {
            gson = contents;
        }
    
        
        this.data = new Gson().fromJson(gson, JackBoxConfigurationData.class);
    }

    public void updateConfig() {
        this.save(this.data);
    }

    @Override
    public Class<?> jsonType() {
        return ShipData.class;
    }

    public JackBoxConfigurationData getShipData() {
        return this.data;
    }

    
}
