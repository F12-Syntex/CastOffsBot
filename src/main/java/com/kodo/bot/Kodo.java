package com.kodo.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class Kodo extends ListenerAdapter{
    public static void main( String[] args ){
        String authToken = System.getenv("DISCORD_TOKEN");
        
        JDABuilder builder = JDABuilder.createDefault(authToken);

        // Disable parts of the cache
        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
        // Enable the bulk delete event
        builder.setBulkDeleteSplittingEnabled(false);
        // Set activity (like "playing Something")
        builder.setActivity(Activity.watching("TV"));
        
        JDA jda = builder
                    .addEventListeners(new Kodo())
                    .build();

        // Sets the global command list to the provided commands (removing all others)
        jda.updateCommands().addCommands(
            Commands.slash("ping", "Calculate ping of the bot")
        ).queue();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event){
        // make sure we handle the right command
        System.out.println(event.getName());
        switch (event.getName()) {
            case "ping":
                long time = System.currentTimeMillis();
                event.reply("Pong!").setEphemeral(true) // reply or acknowledge
                     .flatMap(v ->
                          event.getHook().editOriginalFormat("Pong: %d ms", System.currentTimeMillis() - time) // then edit original
                     ).queue(); // Queue both reply and edit
                break;
        }
    }
}