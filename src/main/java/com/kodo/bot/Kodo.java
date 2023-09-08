package com.kodo.bot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kodo.codewars.CodeWars;
import com.kodo.commands.CommandHandler;
import com.kodo.database.StorageManager;
import com.kodo.handler.Dependencies;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public final class Kodo extends ListenerAdapter {

    private JDABuilder builder;
    private JDA discord;
    private CommandHandler commandHandler;

    private final Dependencies dependencies = new Dependencies();
    
    public static void main(String[] args) {
        Kodo kodo = new Kodo();
        kodo.build();
    }

    private  Kodo() {
        this.configureLogger();

        //load the storage
        StorageManager storage = new StorageManager();
        storage.startup();
        dependencies.setStorage(storage);

        String authToken = System.getenv("DISCORD_TOKEN");
        System.out.println("Token: " + authToken);

        this.builder = JDABuilder.createDefault(authToken)
                .disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE)
                .setBulkDeleteSplittingEnabled(false)
                .setActivity(Activity.competing("Codewars"));

        dependencies.setBuilder(builder);
    }

    public void build() {

        try {

            this.discord = this.builder.build();
            this.discord.addEventListener(this);
            dependencies.setDiscord(discord);

            CodeWars codeWars = new CodeWars(this.dependencies);
            dependencies.setCodeWars(codeWars);

            this.commandHandler = new CommandHandler(this.dependencies); 
            this.commandHandler.loadCommands();
            this.commandHandler.registerCommands();
            this.discord.addEventListener(this.commandHandler);
            dependencies.setCommandHandler(commandHandler);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

public void configureLogger() {
    Logger logger = Logger.getGlobal();
    
    // Set the log level to control which logs are printed
    logger.setLevel(Level.ALL);
    
    // Create a console handler to output log messages to the console
    ConsoleHandler consoleHandler = new ConsoleHandler();
    
    // Create a custom formatter that prints only the class name and log message
    Formatter formatter = new Formatter() {
        @Override
        public String format(java.util.logging.LogRecord record) {
            return record.getSourceClassName() + ": " + record.getMessage() + "\n";
        }
    };
    
    // Set the custom formatter for the console handler
    consoleHandler.setFormatter(formatter);
    
    // Remove any existing handlers from the logger
    logger.setUseParentHandlers(false);
    logger.addHandler(consoleHandler);
}
}
