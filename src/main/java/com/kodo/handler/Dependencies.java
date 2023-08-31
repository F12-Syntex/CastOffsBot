package com.kodo.handler;

import com.kodo.codewars.CodeWars;
import com.kodo.commands.CommandHandler;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Dependencies {

    private JDABuilder builder;
    private JDA discord;
    private CommandHandler commandHandler;
    private CodeWars codeWars;

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
    
}
