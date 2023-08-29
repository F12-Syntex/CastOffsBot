package com.kodo.bot;

import com.kodo.commands.CommandHandler;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class Kodo extends ListenerAdapter {

    private JDABuilder builder;
    private JDA discord;

    //TODO: move handlers to a universal handler
    private CommandHandler commandHandler;
    
    public static void main(String[] args) {
        Kodo kodo = new Kodo();
        kodo.build();
    }

    public Kodo() {
        String authToken = System.getenv("DISCORD_TOKEN");
        System.out.println("Token: " + authToken);

        this.builder = JDABuilder.createDefault(authToken)
                .disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE)
                .setBulkDeleteSplittingEnabled(false)
                .setActivity(Activity.watching("TV"));
    }

    public void build() {
        try {
            
            this.discord = this.builder.build();
            this.discord.addEventListener(this);

            this.commandHandler = new CommandHandler(); 
            


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
