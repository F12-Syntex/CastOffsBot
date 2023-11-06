package com.castoffs.database.jackbox;

public class JackBoxConfigurationData {
    
    private Quiplash quiplash;

    public JackBoxConfigurationData(){
        this.quiplash = new Quiplash();
    }

    public Quiplash getQuiplash(){
        return this.quiplash;
    }

}
