package com.kodo.codewars.events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.annotation.Nonnull;

import com.kodo.codewars.scraper.CodewarsKata;
import com.kodo.handler.Dependencies;
import com.kodo.handler.ListUtils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;

public class IntervalChallenge implements EventListener{

    private ScheduledExecutorService scheduler;

    private Dependencies dependencies;

    private long interval;
    private TimeUnit unit;

    private final int MIN_DIFFICULTY;
    private final int MAX_DIFFICULTY;

    private final String CHANNEL_NAME;

    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    
    @Override
    public void onEvent(@Nonnull GenericEvent event) {
        this.sendKata();
    }

    public IntervalChallenge(Dependencies dependencies, long interval, TimeUnit unit, int MIN_DIFFICULTY, int MAX_DIFFICULTY, String channelName) {
        this.dependencies = dependencies;

        this.interval = interval;
        this.unit = unit;

        this.MIN_DIFFICULTY = MIN_DIFFICULTY;
        this.MAX_DIFFICULTY = MAX_DIFFICULTY;

        this.CHANNEL_NAME = channelName;

        Logger.getGlobal().info("IntervalChallenge created with interval: " + interval + " " + unit.toString());

        this.dependencies.getDiscord().addEventListener(this);
    }

    private CodewarsKata getRandomKata(){

        final int MAX_DESCRIPTION_LENGTH = 800;

        List<CodewarsKata> candidates = new ArrayList<>();

        List<CodewarsKata> katas = this.dependencies
                                        .getStorage()
                                        .getCodewarsStorage()
                                        .getChallenges()
                                        .getKataInformation()
                                        .getChallenges();

        for(CodewarsKata kata : katas){
            if(kata.getRank().getDifficulty() > MAX_DIFFICULTY) continue;
            if(kata.getRank().getDifficulty() < MIN_DIFFICULTY) continue;
            if(kata.getDescription().length() > MAX_DESCRIPTION_LENGTH) continue;
            if(!kata.getCategory().equals("algorithms")) continue;
            if(kata.getTags().contains("Mathematics")) continue;
            if(kata.getTags().contains("Puzzles")) continue;
            if(kata.getTags().contains("Games")) continue;
            if(!kata.getLanguages().contains("java") && !kata.getLanguages().contains("python")) continue;
            candidates.add(kata);
        }

        return ListUtils.getRandomElement(candidates);
    }

    @SuppressWarnings("null")
    public void sendKata(){
        executorService.submit(() -> {
            try {

                final CodewarsKata challenge = this.getRandomKata();
                
                dependencies.getDiscord().getGuilds().forEach(guild -> {
                    guild.getTextChannels().stream().filter(channel -> channel.getName().equals(this.CHANNEL_NAME)).forEach(channel -> {
                        

                        //check time since last challenge
                        List<Message> messages = channel.getHistory().retrievePast(1).complete();

                        if(!messages.isEmpty()){
                            Message lastMessage = messages.get(0);

                            long timeSinceLastChallenge = System.currentTimeMillis() - lastMessage.getTimeCreated().toInstant().toEpochMilli();

                            //check if last challenge was sent more than this.interval this.unit ago
                            if(timeSinceLastChallenge < this.unit.toMillis(this.interval)){
                                return;
                            }
                        }

                        EmbedBuilder embedBuilder = new EmbedBuilder();
                        embedBuilder.setColor(challenge.getRank().getColorEnum());
                        embedBuilder.setTitle(challenge.getName(), challenge.getUrl());
          
                        embedBuilder.addField("Rank", "```" + challenge.getRank().getDifficulty() + " Kyu```", true);
                        embedBuilder.addField("Category", "```" + challenge.getCategory() + "```", true);
                        embedBuilder.addField("Tags", "```" + Arrays.toString(challenge.getTags().toArray()) + "```", false);
                        embedBuilder.addField("Languages", "```" + Arrays.toString(challenge.getLanguages().toArray()) + "```", false);

                        String description = challenge.getDescription();

                        if(description.length() > 1000){
                            description = description.substring(0, 4000) + "...";
                        }

                        embedBuilder.addField("Description", description , false); 

                        
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
        });
    }

    // Method to stop the scheduler when no longer needed
    public void stopHost() {
        if (scheduler != null) {
            scheduler.shutdown();
        }
    }

}
