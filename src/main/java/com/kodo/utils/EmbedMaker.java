package com.kodo.utils;

import java.awt.Color;
import java.time.Instant;
import java.time.LocalDateTime;

import javax.annotation.Nonnull;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

public class EmbedMaker {

private static final String codeLine = "```";

@Nonnull
public static EmbedBuilder ERROR(User user, String notice, String error_type) {
    EmbedBuilder embedBuilder = new EmbedBuilder();
    embedBuilder.setTitle("Error Notice");
    embedBuilder.setDescription(notice);
    embedBuilder.setColor(Color.red);

    String localTime = LocalDateTime.now().toString();

    if (error_type != null && !localTime.isEmpty()) {
        embedBuilder.addField("Error Type", codeLine + error_type + codeLine, false);
    }

    embedBuilder.setFooter("User: " + user.getName() + " | ID: " + user.getId(), user.getAvatarUrl());
    embedBuilder.setTimestamp(Instant.now());    

    return embedBuilder;
}

@Nonnull
public static EmbedBuilder INFO(User user, String notice) {
    EmbedBuilder embedBuilder = new EmbedBuilder();
    embedBuilder.setTitle("INFO");
    embedBuilder.setDescription(notice);
    embedBuilder.setColor(Color.green);
    embedBuilder.setTimestamp(Instant.now());
    embedBuilder.setFooter("User: " + user.getName() + " | ID: " + user.getId(), user.getAvatarUrl());

    return embedBuilder;
}

    
}
