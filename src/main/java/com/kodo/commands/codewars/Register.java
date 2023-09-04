package com.kodo.commands.codewars;

import java.awt.Color;
import java.util.Objects;

import javax.annotation.Nonnull;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kodo.codewars.CodeWars;
import com.kodo.commands.Command;
import com.kodo.commands.CommandMeta;
import com.kodo.database.users.scheme.User;
import com.kodo.handler.Dependencies;
import com.kodo.utils.EmbedMaker;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;

@SuppressWarnings("null")
@CommandMeta(description = "This command registers the user to the system.", name = "register")
public class Register extends Command {

    public Register(Dependencies dependencies) {
        super(dependencies);
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        OptionMapping username = event.getOption("username");

        if(username == null) {
            EmbedBuilder embed = EmbedMaker.ERROR(event.getUser(), "Please specify a valid usernmae", "INVALID_USERNAME");
            event.replyEmbeds(embed.build()).queue();
            return;
        }

        String usernameString = username.getAsString();
        CodeWars codeWars = this.dependencies.getCodeWars();

        try{       
            codeWars.registerUser(usernameString);
        }catch(Exception e){
            EmbedBuilder embed = EmbedMaker.ERROR(event.getUser(), "Could not register the user", e.getLocalizedMessage());
            event.replyEmbeds(embed.build()).queue();
            return;
        }

  

        User profile = codeWars.getUserProfile(usernameString);
        EmbedBuilder embedBuilder = new EmbedBuilder();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(profile, User.class);

        if (json != null) {
            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
            JsonObject overall = jsonObject.getAsJsonObject("ranks").getAsJsonObject("overall");
            
            embedBuilder.setTitle("Success!");
            embedBuilder.setDescription(usernameString + " has been registered to the system!");
            embedBuilder.setColor(Color.green);
            embedBuilder.setThumbnail(this.dependencies.getDiscord().getSelfUser().getAvatarUrl());

            String ranking = jsonObject.get("leaderboardPosition").getAsString();

            embedBuilder.addField("Username", "`" +  usernameString + "`", true);
            embedBuilder.addField("Honor", "`" +  jsonObject.get("honor").getAsString() + "`", true);
            embedBuilder.addField("Clan", "`" +  jsonObject.get("clan").getAsString() + "`", true);
            if(ranking != null && ranking != "0") embedBuilder.addField("Ranking", "`" +  "#" + ranking + "`", true);
            embedBuilder.addField("Rank", "`" + overall.get("rank").getAsString() + "`", true);
            embedBuilder.addField("Score", "`" + overall.get("score").getAsString() + "`", true);            
        }

        
        Button button = Button.primary("codewars_profile", "View Profile")
            .withUrl("https://www.codewars.com/users/" + usernameString)
            .withStyle(ButtonStyle.LINK);

        event.replyEmbeds(embedBuilder.build()).addActionRow(button).queue();

    }

    @Override
    public SlashCommandData getSlashCommandData() {
        return this.getBareBonesSlashCommandData()
                .addOption(OptionType.STRING, "username", "your codewars username", true, true);
    }

    @Override
    public DefaultMemberPermissions getDefaultMemberPermissions() {
       return DefaultMemberPermissions.ENABLED;
    }
    
}
