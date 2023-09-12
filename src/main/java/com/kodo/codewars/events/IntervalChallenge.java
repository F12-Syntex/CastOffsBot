package com.kodo.codewars.events;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.kodo.codewars.scraper.CodewarsKata;
import com.kodo.handler.Dependencies;
import com.kodo.handler.ListUtils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;

public class IntervalChallenge {

    private ScheduledExecutorService scheduler;

    private Dependencies dependencies;

    private long interval;
    private TimeUnit unit;

    private final int MIN_DIFFICULTY;
    private final int MAX_DIFFICULTY;

    private final String CHANNEL_NAME;

    public IntervalChallenge(Dependencies dependencies, long interval, TimeUnit unit, int MIN_DIFFICULTY, int MAX_DIFFICULTY, String channelName) {
        this.dependencies = dependencies;

        this.interval = interval;
        this.unit = unit;

        this.MIN_DIFFICULTY = MIN_DIFFICULTY;
        this.MAX_DIFFICULTY = MAX_DIFFICULTY;

        this.CHANNEL_NAME = channelName;

        Logger.getGlobal().info("IntervalChallenge created with interval: " + interval + " " + unit.toString());
    }

    public void host(){
        // Create a scheduler with a single thread
        scheduler = Executors.newSingleThreadScheduledExecutor();

        // Schedule the code to run every day at a specific time (e.g., 12:00 AM)
        scheduler.scheduleAtFixedRate(() -> {
            this.sendKata();
        }, 0L, 1L, TimeUnit.HOURS);
    }

    @SuppressWarnings("null")
    public void sendKata(){
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
                                                kata.getCategory().equals("algorithms") &&
                                                kata.getLanguages().contains("java") ||
                                                kata.getLanguages().contains("python")
                                ).collect(Collectors.toList()));

                dependencies.getDiscord().getGuilds().forEach(guild -> {
                    guild.getTextChannels().stream().filter(channel -> channel.getName().equals(this.CHANNEL_NAME)).forEach(channel -> {
                        

                        //check time since last challenge
                        Message lastMessage = channel.getHistory().retrievePast(1).complete().get(0);
                        long timeSinceLastChallenge = System.currentTimeMillis() - lastMessage.getTimeCreated().toInstant().toEpochMilli();

                        //check if last challenge was sent more than this.interval this.unit ago
                        if(timeSinceLastChallenge < this.unit.toMillis(this.interval)){
                            return;
                        }

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
        }

    // Method to stop the scheduler when no longer needed
    public void stopHost() {
        if (scheduler != null) {
            scheduler.shutdown();
        }
    }

}
