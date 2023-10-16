package com.castoffs.handler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.castoffs.bot.AutoBumpReminder;
import com.castoffs.commands.CommandHandler;
import com.castoffs.database.StorageManager;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

/**
 * The `Dependencies` class is responsible for storing and managing the core dependencies
 * required by the bot. It acts as the central API gateway for the bot, providing access to
 * essential components such as the JDA (Java Discord API) builder, the JDA instance for
 * Discord interaction, a command handler for processing user commands, and a storage manager
 * for handling data storage and retrieval.
 */
public class Dependencies {

    private JDABuilder builder;          // The JDA builder instance for bot configuration
    private JDA discord;                 // The JDA instance representing the bot's connection to Discord
    private CommandHandler commandHandler; // The command handler for processing user commands
    private StorageManager storage;      // The storage manager for data storage and retrieval
    private AutoBumpReminder autoBumpReminder; // The auto bump reminder
    private final ExecutorService workerThreads = Executors.newFixedThreadPool(5); // The executor service for running tasks

    /**
     * Set the JDA builder instance used for configuring the bot.
     * @param builder the JDA builder instance to set
     */
    public void setBuilder(JDABuilder builder) {
        this.builder = builder;
    }

    /**
     * Set the JDA instance representing the bot's connection to Discord.
     * @param discord the JDA instance to set
     */
    public void setDiscord(JDA discord) {
        this.discord = discord;
    }

    /**
     * Set the command handler responsible for processing user commands.
     * @param commandHandler the command handler to set
     */
    public void setCommandHandler(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    /**
     * Set the storage manager for handling data storage and retrieval.
     * @param storage the storage manager to set
     */
    public void setStorage(StorageManager storage) {
        this.storage = storage;
    }

    /**
     * Set the auto bump reminder.
     * @param autoBumpReminder the auto bump reminder to set
     */
    public void setAutoBumpReminder(AutoBumpReminder autoBumpReminder) {
        this.autoBumpReminder = autoBumpReminder;
    }

    /**
     * Get the JDA builder instance used for configuring the bot.
     * @return the JDA builder instance
     */
    public JDABuilder getBuilder() {
        return builder;
    }

    /**
     * Get the JDA instance representing the bot's connection to Discord.
     * @return the JDA instance
     */
    public JDA getDiscord() {
        return discord;
    }

    /**
     * Get the command handler responsible for processing user commands.
     * @return the command handler instance
     */
    public CommandHandler getCommandHandler() {
        return commandHandler;
    }

    /**
     * Get the storage manager responsible for data storage and retrieval.
     * @return the storage manager instance
     */
    public StorageManager getStorage() {
        return storage;
    }

    /**
     * Get the auto bump reminder.
     * @return the auto bump reminder instance
     */
    public AutoBumpReminder getAutoBumpReminder() {
        return autoBumpReminder;
    }

    /**
     * Get the executor service for running tasks.
     * @return the executor service instance
     */
    public ExecutorService getWorkerThreads() {
        return workerThreads;
    }
}
