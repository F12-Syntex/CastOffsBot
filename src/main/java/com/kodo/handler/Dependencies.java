package com.kodo.handler;

import com.kodo.codewars.CodeWars;
import com.kodo.codewars.events.IntervalChallenge;
import com.kodo.commands.CommandHandler;
import com.kodo.database.StorageManager;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Dependencies {

    private JDABuilder builder;
    private JDA discord;
    private CommandHandler commandHandler;
    private CodeWars codeWars;
    private StorageManager storage;
    
    private IntervalChallenge dailyChallenges;
    private IntervalChallenge weeklyChallenges;

    public void setBuilder(JDABuilder builder) {
        this.builder = builder;
    }

    public void setDiscord(JDA discord) {
        this.discord = discord;
    }

    public void setCommandHandler(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    public void setCodeWars(CodeWars codeWars) {
        this.codeWars = codeWars;
    }

    public void setStorage(StorageManager storage) {
        this.storage = storage;
    }

    public void setDailyChallenges(IntervalChallenge dailyChallenges) {
        this.dailyChallenges = dailyChallenges;
    }

    public void setWeeklyChallenges(IntervalChallenge weeklyChallenges) {
        this.weeklyChallenges = weeklyChallenges;
    }

    public JDABuilder getBuilder() {
        return builder;
    }

    public JDA getDiscord() {
        return discord;
    }

    public CommandHandler getCommandHandler() {
        return commandHandler;
    }

    public CodeWars getCodeWars() {
        return codeWars;
    }

    public StorageManager getStorage() {
        return storage;
    }

    public IntervalChallenge getDailyChallenges() {
        return dailyChallenges;
    }

    public IntervalChallenge getWeeklyChallenges() {
        return weeklyChallenges;
    }
    
}
