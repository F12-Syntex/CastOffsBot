package com.castoffs.commands.moderation;

import java.awt.Color;
import java.util.List;

import com.castoffs.commands.Category;
import com.castoffs.commands.CommandMeta;
import com.castoffs.commands.CommandRecivedEvent;
import com.castoffs.embeds.EmbedMaker;
import com.castoffs.handler.Dependencies;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;

@CommandMeta(alias = {"activity"},
             description = "Displays the activity of a channel",
             category = Category.MODERATION,
             usage = {"activity <channel:mention optional (defualt: general)>"},
             examples = {"activity #general 6"})
public class Activity extends ModerationCommand{

    public Activity(Dependencies dependencies) {
        super(dependencies);
    }

    @Override
    public void onCommandRecieved(CommandRecivedEvent event) {
        
        Guild guild = event.getGuild();
        List<GuildChannel> pingedChannels = event.getMessage().getMentions().getChannels();

        Long textChannelId = !pingedChannels.isEmpty() ? pingedChannels.get(0).getIdLong() : 1162076711756779590L;
        int days = event.isArgumentValidAtIndex(2) ? Integer.parseInt(event.getArgs()[2]) : 7;

        //validation checks
        boolean containsChannel = guild.getTextChannels().stream().map(o -> o.getIdLong()).anyMatch(o -> o.equals(textChannelId));
        // boolean validDays = days > 0 && days <= 30;

        if(!containsChannel){
            EmbedBuilder builder = EmbedMaker.ERROR(event.getAuthor(), "Please ping a valid (text) channel", "Invalid channel");
            event.getMessage().replyEmbeds(builder.build()).queue();
            return;
        }

        // if(!validDays){
        //     EmbedBuilder builder = EmbedMaker.ERROR(event.getAuthor(), "Please enter a valid number of days (1-30)", "Invalid number of days");
        //     event.getMessage().replyEmbeds(builder.build()).queue();
        //     return;
        // }

        TextChannel textChannel = guild.getTextChannelById(textChannelId);

        if(textChannel == null){
            EmbedBuilder builder = EmbedMaker.ERROR(event.getAuthor(), "Please ping a valid (text) channel", "Invalid channel");
            event.getMessage().replyEmbeds(builder.build()).queue();
            return;
        }

        MessageHistory history = MessageHistory.getHistoryFromBeginning(textChannel).complete();
        List<Message> messages = history.getRetrievedHistory();

        //print the average activity every hour
        int[] activity = new int[24];
        for(int i = 0; i < activity.length; i++){
            int finalI = i;
            activity[i] = (int) messages.stream().filter(o -> o.getTimeCreated().getHour() == finalI).count();
        }

        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Activity of " + textChannel.getAsMention());
        builder.setDescription("Average activity");
        builder.setColor(Color.pink);
        builder.addField("Average activity", "`" + String.format("%.2f", average(activity)) + " messages per hour" + "`", false);
        builder.addField("Most active hour", "`" + mostActiveHour(activity) + ":00" + "`", false);
        builder.addField("Least active hour", "`" + leastActiveHour(activity) + ":00" + "`", false);
        builder.addField("Total messages processed", "`" +  messages.size() + "`", false);

        event.getMessage().replyEmbeds(builder.build()).queue();

    }

    private double average(int[] array){
        int sum = 0;
        for(int i : array){
            sum += i;
        }
        return (double) sum / array.length;
    }

    private int mostActiveHour(int[] array){
        int max = 0;
        int index = 0;
        for(int i = 0; i < array.length; i++){
            if(array[i] > max){
                max = array[i];
                index = i;
            }
        }
        return index;
    }

    private int leastActiveHour(int[] array){
        int min = Integer.MAX_VALUE;
        int index = 0;
        for(int i = 0; i < array.length; i++){
            if(array[i] < min){
                min = array[i];
                index = i;
            }
        }
        return index;
    }


    
}
