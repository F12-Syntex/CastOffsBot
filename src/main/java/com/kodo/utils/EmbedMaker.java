package com.kodo.utils;

import java.awt.Color;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.annotation.Nonnull;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class EmbedMaker {

private static final String codeLine = "```";

@Nonnull
public static EmbedBuilder ERROR(User profile, String notice, String error_type) {
    EmbedBuilder embedBuilder = new EmbedBuilder();
    embedBuilder.setTitle("Error Notice");
    embedBuilder.setDescription(notice);
    embedBuilder.setColor(Color.red);

    String localTime = LocalDateTime.now().toString();

    if (error_type != null && !localTime.isEmpty()) {
        embedBuilder.addField("Response", codeLine + error_type + codeLine, false);
    }

    embedBuilder.setFooter("User: " + profile.getName() + " | ID: " + profile.getId(), profile.getAvatarUrl());
    embedBuilder.setTimestamp(Instant.now());    

    return embedBuilder;
}

@Nonnull
public static EmbedBuilder INFO(User user, String notice) {
    EmbedBuilder embedBuilder = new EmbedBuilder();
    embedBuilder.setTitle("INFO");
    embedBuilder.setDescription(notice);
    embedBuilder.setColor(Color.green);
    embedBuilder.setTimestamp(Instant.now());
    embedBuilder.setFooter("User: " + user.getName() + " | ID: " + user.getId(), user.getAvatarUrl());

    return embedBuilder;
}

/**
 * This method returns a loading embed, then when the future is done, the future embed will display
 * @param future
 * @return
 */
/**
 * This method returns a loading embed, then when the future is done, the future embed will display
 * @param event
 * @param future
 * @return
 */
public static void getAsyncEmbed(SlashCommandInteractionEvent event, Future<EmbedBuilder> future) {

    EmbedBuilder loadingEmbedBuilder = new EmbedBuilder();
    loadingEmbedBuilder.setTitle("INFO");
    loadingEmbedBuilder.setDescription("test");
    loadingEmbedBuilder.setColor(Color.green);
    loadingEmbedBuilder.setTimestamp(Instant.now());

    MessageEmbed loadedEmbed = loadingEmbedBuilder.build();

    //Send the loading embed
    event.replyEmbeds(loadedEmbed).queue();

    //Create a completable future that will run the future in a separate thread
    CompletableFuture<EmbedBuilder> completableFuture = CompletableFuture.supplyAsync(() -> {
        try {
            return future.get(); // Wait for the future to complete and get the result
        } catch (InterruptedException | ExecutionException e) {
            // Handle any exceptions that occurred during the execution of the future
            e.printStackTrace();
            return null; // Return null or an error embed if necessary
        }
    });

    //When the future is done, edit the original message with the result
    completableFuture.thenAccept(embed -> {
        event.getHook().editOriginalEmbeds(embed.build()).queue();
    });
}





    
}
