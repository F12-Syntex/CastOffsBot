package com.kodo.utils;

import java.awt.Color;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Nonnull;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.components.ItemComponent;

public class EmbedMaker {

    private static final String codeLine = "```";
    private static ExecutorService executorService = Executors.newFixedThreadPool(3);

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
    public static EmbedBuilder ERROR_BASIC(User profile) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Error Notice");
        embedBuilder.setColor(Color.red);
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
     * This method returns a loading embed, meanwhile running the future task, be sure to edit the original message with the result
     * @param event
     * @param future
     * @return
     */
    public static void runAsyncTask(SlashCommandInteractionEvent event, Runnable runnable) {
        EmbedBuilder loadingEmbedBuilder = new EmbedBuilder();
        loadingEmbedBuilder.setTitle("INFO");
        loadingEmbedBuilder.setDescription("test");
        loadingEmbedBuilder.setColor(Color.green);
        loadingEmbedBuilder.setTimestamp(Instant.now());

        MessageEmbed loadedEmbed = loadingEmbedBuilder.build();

        //Send the loading embed
        event.replyEmbeds(loadedEmbed).queue();
        executorService.submit(() -> {
            try {
                runnable.run();
            } catch (Exception e) {
                EmbedBuilder builder = EmbedMaker.ERROR(event.getUser(), "Whoops, an error has occured.", e.getLocalizedMessage());
                event.getHook().editOriginalEmbeds(builder.build()).queue();
                e.printStackTrace();
            }
        });
    }

    /**
     * This method returns a loading embed, meanwhile running the future task, be sure to edit the original message with the result
     * @param event
     * @param future
     * @return
     */
    public static void sendAsyncMessage(SlashCommandInteractionEvent event, EmbedBuilderSupplier<EmbedBuilder> task) {
        EmbedMaker.runAsyncTask(event, () -> {
            try{
                EmbedBuilder embed = task.call(event.getHook());
                event.getHook().editOriginalEmbeds(embed.build()).queue();
            }catch(Exception e){
                EmbedBuilder builder = EmbedMaker.ERROR(event.getUser(), "Whoops, an error has occured.", e.getLocalizedMessage());
                event.getHook().editOriginalEmbeds(builder.build()).queue();
            }
        });
    }

    /**
     * This method returns a loading embed, meanwhile running the future task, be sure to edit the original message with the result
     * @param event
     * @param future
     * @return
     */
    @SuppressWarnings("null")
    public static void sendAsyncMessageWithActionItems(SlashCommandInteractionEvent event, EmbedBuilderSupplier<EmbedBuilder> task, ItemComponent... items) {
        EmbedMaker.runAsyncTask(event, () -> {
            try{
                EmbedBuilder embed = task.call(event.getHook());
                event.getHook().editOriginalEmbeds(embed.build()).setActionRow(items).queue();
            }catch(Exception e){
                EmbedBuilder builder = EmbedMaker.ERROR(event.getUser(), "Whoops, an error has occured.", e.getLocalizedMessage());
                event.getHook().editOriginalEmbeds(builder.build()).queue();
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
}
