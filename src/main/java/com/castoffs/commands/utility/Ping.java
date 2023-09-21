package com.castoffs.commands.utility;

import java.awt.Color;
import java.time.Instant;

import com.castoffs.commands.Category;
import com.castoffs.commands.Command;
import com.castoffs.commands.CommandMeta;
import com.castoffs.handler.Dependencies;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

/**
 * Ping command, used to check the latency of the bot
 */
@CommandMeta(name = "ping", description = "Checks the latency of the bot", category = Category.INFO)
public class Ping extends Command{

    public Ping(Dependencies dependencies) {
        super(dependencies);
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        EmbedBuilder ping = new EmbedBuilder();
		
		ping.setTitle(":ping_pong: Pong! :ping_pong:");
		ping.setColor(Color.decode("#85EF93"));
		ping.setThumbnail("https://cdn.discordapp.com/emojis/723073203307806761.gif?v=1");
		
		event.getJDA().getRestPing().queue(o -> {
			ping.addField("Client latency", "```" + o + "ms```", false);
			ping.addField("Websocket latency", "```" + event.getJDA().getGatewayPing() + "ms```", false);
			ping.addField("JSON latency", "```" + 0 + "ms```", false);
			
			ping.setFooter("User: " + event.getUser().getName() + " | ID: " + event.getUser().getId(), event.getUser().getAvatarUrl());
			ping.setTimestamp(Instant.now());
		
			event.replyEmbeds(ping.build()).queue();
		});

    }

    @Override
    public SlashCommandData getSlashCommandData() {
        return this.getBareBonesSlashCommandData();
    }

    @Override
    public DefaultMemberPermissions getDefaultMemberPermissions() {
        return DefaultMemberPermissions.ENABLED;
    }

    @Override
    public void onButtonPressed(ButtonInteractionEvent event) {
        return;
    }
}
