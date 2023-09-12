package com.kodo.bot;

import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.kodo.codewars.CodeWars;
import com.kodo.codewars.events.IntervalChallenge;
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

    private IntervalChallenge dailyChallenges;
    private IntervalChallenge weeklyChallenges;

    private final Dependencies dependencies = new Dependencies();
    
    public static void main(String[] args) {
        Kodo.getInstance().build();
    }

    /**
     * avoid using this singleton unless necessary
     * @deprecated
     * @return
     */
    @Deprecated
    public static Kodo getInstance() {
        if (Kodo.instance == null) {
            Kodo.instance = new Kodo();
        }
        return Kodo.instance;
    }

    private static Kodo instance;

    private Kodo() {
        this.configureLogger();

        CodeWars codeWars = new CodeWars(this.dependencies);
        dependencies.setCodeWars(codeWars);

        //load the storage
        StorageManager storage = new StorageManager(this.dependencies);
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

            this.commandHandler = new CommandHandler(this.dependencies); 
            this.commandHandler.loadCommands();
            this.commandHandler.registerCommands();
            this.discord.addEventListener(this.commandHandler);
            dependencies.setCommandHandler(commandHandler);

            //start challenges when jda is ready
            this.discord.awaitReady();

            this.dailyChallenges = new IntervalChallenge(this.dependencies, 1, java.util.concurrent.TimeUnit.DAYS, 5, 8, "daily-challenges");
            this.dailyChallenges.host();

            this.weeklyChallenges = new IntervalChallenge(this.dependencies, 7, java.util.concurrent.TimeUnit.DAYS, 4, 5, "weekly-challenges");
            this.weeklyChallenges.host();

            this.dependencies.setDailyChallenges(this.dailyChallenges);
            this.dependencies.setWeeklyChallenges(this.weeklyChallenges);

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

    public JDA getDiscord() {
        return discord;
    }

    public CommandHandler getCommandHandler() {
        return commandHandler;
    }

    public Dependencies getDependencies() {
        return dependencies;
    }

}
