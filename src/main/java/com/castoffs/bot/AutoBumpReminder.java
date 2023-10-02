package com.castoffs.bot;

import java.awt.Color;
import java.time.Instant;
import java.util.Optional;

import com.castoffs.utils.TimeUtils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.GenericMessageReactionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * all bump reminders are handled here
 */
public class AutoBumpReminder extends ListenerAdapter{

    private boolean reminded = false;

    public AutoBumpReminder(){
        Castoffs.getInstance().getDiscord().addEventListener(this);
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

                castoffs.findMembers(member -> {
                    return member.hasPermission(Permission.KICK_MEMBERS) && !member.getUser().isBot();
                }).onSuccess(succ -> {
                    succ.forEach(o -> {
                        o.getUser().openPrivateChannel().queue((c) -> {
                            c.sendMessageEmbeds(embedBuilder.build()).queue();
                            System.out.println("Sent bump reminder to " + o.getUser().getName());
                        });
                    });
                });

                reminded = true;
            }

        }else{
            reminded = false;
        }
    }

    @SuppressWarnings("null")
    public void log(GenericEvent event){

        if(event instanceof GenericMessageEvent){
            GenericMessageEvent messageEvent = (GenericMessageEvent) event;
            System.out.println("Channel: " + messageEvent.getChannel().getName());
            if(event instanceof GenericMessageReactionEvent){
                GenericMessageReactionEvent reactionEvent = (GenericMessageReactionEvent) event;
                System.out.println("\t" +  "Reaction: " + reactionEvent.getReaction().getEmoji().getFormatted());
            }
            if(event instanceof MessageReceivedEvent){
                MessageReceivedEvent messageReceivedEvent = (MessageReceivedEvent) event;
                if(messageReceivedEvent.getMember() == null){
                    return;
                }
                System.out.println("\t" + messageReceivedEvent.getMember().getEffectiveName() + ": " + messageReceivedEvent.getMessage().getContentRaw());
            }
        }
    }

    public void MessageReceivedEvent(MessageReceivedEvent event) {
        this.check();
        this.log(event);
    }

}
