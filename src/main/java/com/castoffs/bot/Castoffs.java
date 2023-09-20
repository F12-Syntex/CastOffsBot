package com.castoffs.bot;

import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.castoffs.commands.CommandHandler;
import com.castoffs.database.StorageManager;
import com.castoffs.handler.Dependencies;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

/**
 * the main class for the bot which handles the startup and shutdown of the bot
 */
public final class Castoffs extends ListenerAdapter {

    private JDABuilder builder;
    private JDA discord;
    private CommandHandler commandHandler;

    private final Dependencies dependencies = new Dependencies();
    
    public static void main(String[] args) {
        Castoffs.getInstance().build();
    }

    /**
     * @return
     */
    @Deprecated
    public static Castoffs getInstance() {
        if (Castoffs.instance == null) {
            Castoffs.instance = new Castoffs();
        }
        return Castoffs.instance;
    }

    private static Castoffs instance;

    private Castoffs() {
        this.configureLogger();

        //load the storage
        StorageManager storage = new StorageManager(this.dependencies);
        storage.startup();
        dependencies.setStorage(storage);

        String authToken = System.getenv("DISCORD_TOKEN_CASTOFF");
        System.out.println("Token: " + authToken);

        this.builder = JDABuilder.createDefault(authToken)
                .disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE)
                .setBulkDeleteSplittingEnabled(false)
                .setActivity(Activity.watching("The cast offs"));

        dependencies.setBuilder(builder);
    }

    /**
     * builds the bot 
     */
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * configure the logger for the bot in the format of "class name: log message"
     */
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

    /**
     * get the discord instance, the instance is null until the bot is built
     * @return
     */
    public JDA getDiscord() {
        return discord;
    }

    /**
     * get the command handler instance
     * @return
     */
    public CommandHandler getCommandHandler() {
        return commandHandler;
    }

    /**
     * get all the dependencies used by the bot
     * @return
     */
    public Dependencies getDependencies() {
        return dependencies;
    }

}