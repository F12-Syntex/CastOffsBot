package com.kodo.codewars.events;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.apache.logging.log4j.core.config.builder.api.LoggableComponentBuilder;

import com.kodo.codewars.scraper.CodewarsKata;
import com.kodo.handler.Dependencies;
import com.kodo.handler.ListUtils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;

public class IntervalChallenge {

    private ScheduledExecutorService scheduler;

    private Dependencies dependencies;

    private long interval;
    private TimeUnit unit;

    private final int MIN_DIFFICULTY;
    private final int MAX_DIFFICULTY;

    public IntervalChallenge(Dependencies dependencies, long interval, TimeUnit unit, int MIN_DIFFICULTY, int MAX_DIFFICULTY) {
        this.dependencies = dependencies;

        this.interval = interval;
        this.unit = unit;

        this.MIN_DIFFICULTY = MIN_DIFFICULTY;
        this.MAX_DIFFICULTY = MAX_DIFFICULTY;

        Logger.getGlobal().info("IntervalChallenge created with interval: " + interval + " " + unit.toString());
    }

    @SuppressWarnings("null")
    public void host(){
        // Create a scheduler with a single thread
        scheduler = Executors.newSingleThreadScheduledExecutor();

        // Schedule the code to run every day at a specific time (e.g., 12:00 AM)
        scheduler.scheduleAtFixedRate(() -> {
            try {
                
                final int MAX_DESCRIPTION_LENGTH = 800;

                final CodewarsKata challenge = ListUtils.getRandomElement(
                                this.dependencies
                                .getStorage()
                                .getCodewarsStorage()
                                .getChallenges()
                                .getKataInformation()
                                .getChallenges()
                                .stream()
                                .filter(kata -> kata.getRank().getDifficulty() <= MAX_DIFFICULTY &&
                                                kata.getRank().getDifficulty() >= MIN_DIFFICULTY &&
                                                kata.getDescription().length() < MAX_DESCRIPTION_LENGTH &&
                                                kata.getLanguages().contains("java") ||
                                                kata.getLanguages().contains("python")
                                ).collect(Collectors.toList()));

                dependencies.getDiscord().getGuilds().forEach(guild -> {
                    guild.getTextChannels().stream().filter(channel -> channel.getName().equals("daily-challenges")).forEach(channel -> {
                        
                        EmbedBuilder embedBuilder = new EmbedBuilder();
                        embedBuilder.setColor(challenge.getRank().getColorEnum());
                        embedBuilder.setTitle(challenge.getName(), challenge.getUrl());

                        String description = challenge.getDescription();

                        if(description.length() > 4000){
                            description = description.substring(0, 4000) + "...";
                        }

                        embedBuilder.setDescription(description);

                        
                        Button button = Button.primary("codewars_profile", "View Kata")
                            .withUrl(challenge.getUrl())
                            .withStyle(ButtonStyle.LINK);

                        channel.sendMessageEmbeds(embedBuilder.build()).setActionRow(button).queue();
                    });
                });

            } catch (Exception e) {
                // Handle any exceptions that might occur during execution
                e.printStackTrace();
            }
        }, 0L, this.interval, this.unit);
    }

    // Method to stop the scheduler when no longer needed
    public void stopHost() {
        if (scheduler != null) {
            scheduler.shutdown();
        }
    }

}
