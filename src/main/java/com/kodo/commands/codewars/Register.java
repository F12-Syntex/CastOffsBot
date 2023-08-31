package com.kodo.commands.codewars;

import org.jetbrains.annotations.NotNull;

import com.kodo.codewars.CodeWars;
import com.kodo.codewars.RegisterResult;
import com.kodo.commands.Command;
import com.kodo.commands.CommandMeta;
import com.kodo.handler.Dependencies;
import com.kodo.utils.EmbedMaker;

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
            EmbedMaker.replyError("Please specify a valid usernmae", "INVALID_USERNAME", event, true);
            return;
        }

        String usernameString = username.getAsString();
        CodeWars codeWars = this.dependencies.getCodeWars();

        RegisterResult result = codeWars.registerUser(usernameString);

        if(result != RegisterResult.USER_REGISTERED) {
            EmbedMaker.replyError("Could not register the user", result.toString(), event, true);
            return;
        }

        event.reply(usernameString + " has been registered to the system").setEphemeral(true).queue();
    }

    @Override
    public SlashCommandData getSlashCommandData() {
        return this.getBareBonesSlashCommandData()
                .addOption(OptionType.STRING, "username", "your codewars username", true, true);
    }

    @Override
    public DefaultMemberPermissions getDefaultMemberPermissions() {
       return DefaultMemberPermissions.DISABLED;
    }
    
}
