package com.castoffs.bot;

import java.awt.Color;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.castoffs.utils.TimeUtils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class AutoBumpReminder {

    private boolean reminded = false;

    public AutoBumpReminder(){
        
        //create a scheduled task in a new thread and check every minute if the bump reminder should be sent
        // Create a single-threaded executor service
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        // Define the task to be executed
        Runnable task = () -> {
            this.check();
        };

        // Schedule the task to run after a delay of 1 second and then repeat every 5 seconds
        executor.scheduleAtFixedRate(task, 0L, 1, TimeUnit.SECONDS);
    }


    public void check(){
        Optional<Guild> guild = Castoffs.getInstance().getDiscord().getGuilds()
                .stream()
                .filter(o -> o.getId().equals("339615489246494722")).findFirst();

        if(!guild.isPresent()){
            System.out.println("Could not find guild");
            return;
        }

        Guild castoffs = guild.get();

        TextChannel channel = castoffs.getTextChannels().stream().filter(o -> o.getId().equals("743107904143622204")).findFirst().get();

        //get the when the last message was sent
        long lastMessage = channel.getHistory().retrievePast(1).complete().get(0).getTimeCreated().toInstant().toEpochMilli();
        long currentTime = System.currentTimeMillis();
        long difference = currentTime - lastMessage;

        long twoHours = 1000*60*60*2;

        System.out.println("Last message was " + TimeUtils.formatDuration((twoHours - difference)/1000) + " ago");

        if(difference > twoHours){

            if(!reminded){
                //toPing.sendMessage(modRole.getAsMention() + " It's bumping time, please bump the server.").queue();

                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setTitle("Bump reminder");
                embedBuilder.setDescription("It's bumping time, please bump the server. at " + channel.getAsMention());
                embedBuilder.setColor(Color.pink);
                embedBuilder.setTimestamp(Instant.now());

                List<Member> members = castoffs.findMembers(member -> {
                    return member.hasPermission(Permission.KICK_MEMBERS) && !member.getUser().isBot();
                }).get();
                
                members.forEach(o -> {
                    o.getUser().openPrivateChannel().queue((c) -> {
                        c.sendMessageEmbeds(embedBuilder.build()).queue();
                        System.out.println("Sent bump reminder to " + o.getUser().getName());
                    });
                });

                reminded = true;
            }

        }else{
            reminded = false;
        }
    }
}
