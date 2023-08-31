package com.kodo.utils;

import java.awt.Color;
import java.time.Instant;
import java.time.LocalDateTime;

import org.jetbrains.annotations.NotNull;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class EmbedMaker {

private static final String codeLine = "```";

public static void replyError(String notice, String error_type, @NotNull SlashCommandInteractionEvent event, boolean ephemeral) {
    EmbedBuilder embedBuilder = new EmbedBuilder();
    embedBuilder.setTitle("Error Notice");
    embedBuilder.setDescription(notice);
    embedBuilder.setColor(Color.red);

    String localTime = LocalDateTime.now().toString();

    if (error_type != null && !localTime.isEmpty()) {
        embedBuilder.addField("Error Type", codeLine + error_type + codeLine, false);
    }

    embedBuilder.setFooter("User: " + event.getUser().getName() + " | ID: " + event.getUser().getId(), event.getUser().getAvatarUrl());
    embedBuilder.setTimestamp(Instant.now());    

    MessageEmbed embed = embedBuilder.build();

    event.replyEmbeds(embed).setEphemeral(ephemeral).queue();
}

    
}
