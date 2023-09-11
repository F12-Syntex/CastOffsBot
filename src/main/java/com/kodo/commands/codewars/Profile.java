package com.kodo.commands.codewars;

import java.util.ArrayList;
import java.util.List;

import com.kodo.commands.CommandMeta;
import com.kodo.database.users.scheme.Challenges;
import com.kodo.database.users.scheme.User;
import com.kodo.embeds.EmbedMaker;
import com.kodo.embeds.PagedEmbed;
import com.kodo.handler.Dependencies;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;

@CommandMeta(description = "Returns the users codewars profile", name = "profile", cooldown = 1000L * 3)
public class Profile extends CodeWarsCommand {

    public Profile(Dependencies dependencies) {
        super(dependencies);
    }

    @SuppressWarnings("null")
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event){

        OptionMapping usernameOptionMapping = event.getOption("username");

        String username = usernameOptionMapping.getAsString();

        EmbedMaker.runAsyncTask(event, () -> {

            //display user data from the api
            User profile = this.dependencies.getCodeWars().getApi().getProfileData(username);

            this.dependencies.getCodeWars().getApi().reCacheChallenges(username);

            Challenges challenges = this.dependencies.getStorage()
                                                     .getUserStorage()
                                                     .getUserData(username)
                                                     .get()
                                                     .getCompletedKatas()
                                                     .getChallenges();
            


            PagedEmbed builder = this.getProfileEmbed(profile, challenges);

            builder.setDescription(profile.getUsername() + " profile");

            Button button = Button.primary("codewars_profile", "View Profile")
                .withUrl("https://www.codewars.com/users/" + profile.getUsername())
                .withStyle(ButtonStyle.LINK);

            // System.out.println("Sending embed");
            // event.getHook().editOriginalEmbeds(builder.build()).setActionRow(button).queue();
            // event.replyEmbeds(builder.build()).addActionRow(button).queue();

            List<Button> buttons = new ArrayList<>();
            buttons.add(button);

            builder.sendEdit(event.getHook(), buttons);
            // builder.send(event);
        });

    }

    @Override
    public SlashCommandData getSlashCommandData() {
        SlashCommandData options = this.getBareBonesSlashCommandData()
                .addOption(OptionType.STRING, "username", "User's Codewars username", true, true);
        return options;
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
