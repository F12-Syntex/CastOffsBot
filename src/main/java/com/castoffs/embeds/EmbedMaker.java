package com.castoffs.embeds;

import java.awt.Color;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Nonnull;

import com.castoffs.utils.QuoteUtils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;

/**
 * utility class used to create embeds
 */
public class EmbedMaker {

    private static final String codeLine = "```";
    private static ExecutorService executorService = Executors.newFixedThreadPool(3);

    /**
     * generates an error embed for the user
     * @param profile the user who invoked the command
     * @param notice the notice to display to the user
     * @param error_type short description of the error
     * @return the embed
     */ 
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

    /**
     * generates an error embed for the user
     * @param profile the user who invoked the command
     * @return the embed
     */
    @Nonnull
    public static EmbedBuilder ERROR_BASIC(User profile) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Error Notice");
        embedBuilder.setColor(Color.red);
        embedBuilder.setFooter("User: " + profile.getName() + " | ID: " + profile.getId(), profile.getAvatarUrl());
        embedBuilder.setTimestamp(Instant.now());    
        return embedBuilder;
    }

    /**
     * generates an error embed for the user
     * @param user the user who invoked the command
     * @param notice the notice to display to the user
     * @return the embed
     */
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
     * This method returns a loading embed, meanwhile running the future task, be sure to edit the original message with the result
     * @param event the event
     * @param future the future task
     * @return the loading embed
     */
    public static void runAsyncTask(Message origin, User user, MessageSupplier runnable) {
        EmbedBuilder loadingEmbedBuilder = new EmbedBuilder();
        loadingEmbedBuilder.setTitle("Loading task...");
        loadingEmbedBuilder.setDescription("Hang tight while this task, this may take some time depending on the request. In the meantime, here are some quotes to keep you entertained.");
        loadingEmbedBuilder.setColor(Color.green);
        loadingEmbedBuilder.setTimestamp(Instant.now());

        loadingEmbedBuilder.setImage("https://media.tenor.com/RVvnVPK-6dcAAAAC/reload-cat.gif");

        loadingEmbedBuilder.setFooter("User: " + user.getName() + " | ID: " + user.getId(), user.getAvatarUrl());
        loadingEmbedBuilder.setTimestamp(Instant.now());
        loadingEmbedBuilder.addField("Quote", "```" + QuoteUtils.getRandomQuote() + "```", true);

        MessageEmbed loadedEmbed = loadingEmbedBuilder.build();

        //Send the loading embed
        MessageCreateAction action = origin.replyEmbeds(loadedEmbed);
        
        executorService.submit(() -> {
            try {
                runnable.call(action.complete());
            } catch (Exception e) {
                EmbedBuilder builder = EmbedMaker.ERROR(user, "Whoops, an error has occured.", e.getLocalizedMessage());
                origin.editMessageEmbeds(builder.build()).queue();
                e.printStackTrace();
                e.printStackTrace();
            }
        });
    }

    /**
     * This interface is used to pass the hook to the future
     * @param <T>
     */
    @FunctionalInterface
    public interface EmbedBuilderSupplier <T> {
        T call(InteractionHook hook);
    }    

    /**
     * This interface is used to pass the hook to the future
     * @param <T>
     */
    @FunctionalInterface
    public interface MessageSupplier {
        void call(Message hook);
    } 
}
