package com.kodo.commands.codewars;

import org.jetbrains.annotations.NotNull;

import com.kodo.codewars.CodeWars;
import com.kodo.commands.CommandMeta;
import com.kodo.database.users.scheme.User;
import com.kodo.embeds.EmbedMaker;
import com.kodo.handler.Dependencies;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;

@CommandMeta(description = "This command registers the user to the system.", name = "register")
public class Register extends CodeWarsCommand {

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

        EmbedMaker.runAsyncTask(event, () -> {

            try{       
                codeWars.registerUser(usernameString);
            }catch(Exception e){
                EmbedBuilder embed = EmbedMaker.ERROR(event.getUser(), "Could not register the user", e.getLocalizedMessage());
                event.getHook().editOriginalEmbeds(embed.build()).queue();
                return;
            }

            //we can be sure the user exists as we've just registered them
            User profile = codeWars.getUserProfile(usernameString).get();

            Button button = Button.primary("codewars_profile", "View Profile")
                .withUrl("https://www.codewars.com/users/" + usernameString)
                .withStyle(ButtonStyle.LINK);

            event.getHook().editOriginalEmbeds(this.getProfileEmbed(profile).build()).setActionRow(button).queue();

        });
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
