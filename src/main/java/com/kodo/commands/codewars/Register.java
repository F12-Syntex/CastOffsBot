package com.kodo.commands.codewars;

import javax.annotation.Nonnull;

import org.jetbrains.annotations.NotNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kodo.codewars.CodeWars;
import com.kodo.codewars.RegisterResult;
import com.kodo.commands.Command;
import com.kodo.commands.CommandMeta;
import com.kodo.database.users.UserConfiguration;
import com.kodo.database.users.UserProfileConfiguration;
import com.kodo.database.users.scheme.User;
import com.kodo.handler.Dependencies;
import com.kodo.utils.EmbedMaker;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

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

        RegisterResult result = codeWars.registerUser(usernameString);

        if(result != RegisterResult.USER_REGISTERED) {
            EmbedBuilder embed = EmbedMaker.ERROR(event.getUser(), "Could not register the user", result.getErrorMessage());
            event.replyEmbeds(embed.build()).queue();
            return;
        }

        UserConfiguration user = codeWars.getUser(usernameString);
        UserProfileConfiguration profile = user.getProfile();
        EmbedBuilder embed = EmbedMaker.INFO(event.getUser(), usernameString + " has been registered to the system");

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(profile.getUser(), User.class);
        String jsonPretty = System.lineSeparator() + json + System.lineSeparator() + "```";

        if(json != null){
            embed.addField("profile.json", "```py" + jsonPretty, false);
        }

        event.replyEmbeds(embed.build()).queue();
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
