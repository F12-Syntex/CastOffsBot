package com.castoffs.bot;

import java.awt.Color;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;

import com.castoffs.utils.TimeUtils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.channel.GenericChannelEvent;
import net.dv8tion.jda.api.events.channel.forum.GenericForumTagEvent;
import net.dv8tion.jda.api.events.channel.update.GenericChannelUpdateEvent;
import net.dv8tion.jda.api.events.emoji.GenericEmojiEvent;
import net.dv8tion.jda.api.events.guild.GenericGuildEvent;
import net.dv8tion.jda.api.events.guild.invite.GenericGuildInviteEvent;
import net.dv8tion.jda.api.events.guild.member.GenericGuildMemberEvent;
import net.dv8tion.jda.api.events.guild.override.GenericPermissionOverrideEvent;
import net.dv8tion.jda.api.events.guild.voice.GenericGuildVoiceEvent;
import net.dv8tion.jda.api.events.interaction.GenericAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.GenericContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.GenericComponentInteractionCreateEvent;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;
import net.dv8tion.jda.api.events.message.react.GenericMessageReactionEvent;
import net.dv8tion.jda.api.events.role.GenericRoleEvent;
import net.dv8tion.jda.api.events.session.GenericSessionEvent;
import net.dv8tion.jda.api.events.stage.GenericStageInstanceEvent;
import net.dv8tion.jda.api.events.sticker.GenericGuildStickerEvent;
import net.dv8tion.jda.api.events.thread.GenericThreadEvent;
import net.dv8tion.jda.api.events.thread.member.GenericThreadMemberEvent;
import net.dv8tion.jda.api.events.user.GenericUserEvent;
import net.dv8tion.jda.api.events.user.update.GenericUserPresenceEvent;
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

    public void onGenericSessionEvent(@Nonnull GenericSessionEvent event) {this.check();}
    public void onGenericInteractionCreate(@Nonnull GenericInteractionCreateEvent event) {this.check();}
    public void onGenericAutoCompleteInteraction(@Nonnull GenericAutoCompleteInteractionEvent event) {this.check();}
    public void onGenericComponentInteractionCreate(@Nonnull GenericComponentInteractionCreateEvent event) {this.check();}
    public void onGenericCommandInteraction(@Nonnull GenericCommandInteractionEvent event) {this.check();}
    public void onGenericContextInteraction(@Nonnull GenericContextInteractionEvent<?> event) {this.check();}
    public void onGenericMessage(@Nonnull GenericMessageEvent event) {this.check();}
    public void onGenericMessageReaction(@Nonnull GenericMessageReactionEvent event) {this.check();}
    public void onGenericUser(@Nonnull GenericUserEvent event) {this.check();}
    public void onGenericUserPresence(@Nonnull GenericUserPresenceEvent event) {this.check();}
    public void onGenericStageInstance(@Nonnull GenericStageInstanceEvent event) {this.check();}
    public void onGenericChannel(@Nonnull GenericChannelEvent event) {this.check();}
    public void onGenericChannelUpdate(@Nonnull GenericChannelUpdateEvent<?> event) {this.check();}
    public void onGenericThread(@Nonnull GenericThreadEvent event) {this.check();}
    public void onGenericThreadMember(@Nonnull GenericThreadMemberEvent event) {this.check();}
    public void onGenericGuild(@Nonnull GenericGuildEvent event) {this.check();}
    public void onGenericGuildInvite(@Nonnull GenericGuildInviteEvent event) {this.check();}
    public void onGenericGuildMember(@Nonnull GenericGuildMemberEvent event) {this.check();}
    public void onGenericGuildVoice(@Nonnull GenericGuildVoiceEvent event) {this.check();}
    public void onGenericRole(@Nonnull GenericRoleEvent event) {this.check();}
    public void onGenericEmoji(@Nonnull GenericEmojiEvent event) {this.check();}
    public void onGenericGuildSticker(@Nonnull GenericGuildStickerEvent event) {this.check();}
    public void onGenericPermissionOverride(@Nonnull GenericPermissionOverrideEvent event) {this.check();}
    public void onGenericForumTag(@Nonnull GenericForumTagEvent event) {this.check();}

}
