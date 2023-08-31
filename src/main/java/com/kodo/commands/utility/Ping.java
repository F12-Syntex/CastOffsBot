package com.kodo.commands.utility;

import java.awt.Color;
import java.time.Instant;

import com.kodo.commands.Command;
import com.kodo.commands.CommandMeta;
import com.kodo.handler.Dependencies;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

@CommandMeta(name = "ping", description = "Checks the latency of the bot")
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
		
			event.replyEmbeds(ping.build()).setEphemeral(true).queue();
		});

    }

    @Override
    public SlashCommandData getSlashCommandData() {
        return this.getBareBonesSlashCommandData();
    }

    @Override
    public DefaultMemberPermissions getDefaultMemberPermissions() {
        return DefaultMemberPermissions.DISABLED;
    }
}
