package com.castoffs.bot;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * A store for all the settings of the bot.
 */
public class Settings {
    public static final String NAME = "Castoffs";
    public static final String VERSION = "v1.0.11";
    public static final String PREFIX = ";";

    private final static boolean MAIN_BOT = false;

    public static String getEnvKeyForToken(){
        if(Settings.isDebugging()){
            return "DISCORD_TOKEN_CASTOFFS_DEBUG";
        }
        return "DISCORD_TOKEN_CASTOFF"; 
    }

    /**
     * If the bot is in debug mode.
     * @return
     */
    public static boolean isDebugging(){
        InetAddress localMachine;
        try {
            localMachine = InetAddress.getLocalHost();
            String computerName = localMachine.getHostName();
            return computerName.equals("workstation") && !MAIN_BOT;
        } catch (UnknownHostException e) {
            return false;
        }
    }
}
