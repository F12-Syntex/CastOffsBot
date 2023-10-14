package com.castoffs.bot;

/**
 * A store for all the settings of the bot.
 */
public class Settings {
    public static final String NAME = "Castoffs";
    public static final String VERSION = "v1.0.9";
    public static final String PREFIX = ";";
    public static final boolean DEBUG = false;

    public static String getEnvKeyForToken(){
        if(DEBUG){
            return "DISCORD_TOKEN_CASTOFFS_DEBUG";
        }
        return "DISCORD_TOKEN_CASTOFF"; 
    }
}
