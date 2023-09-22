package com.castoffs.commands.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.castoffs.commands.Category;
import com.castoffs.commands.CommandMeta;
import com.castoffs.handler.Dependencies;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;

/**
 * developer command used for testing purposes
 */
@CommandMeta(description = "Returns the developer command", name = "dev", category = Category.ADMIN)
public class Developer extends AdminCommand{

    public Developer(Dependencies dependencies) {
        super(dependencies);
    }

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        event.getGuild().loadMembers().onSuccess(members -> {
            List<Button> buttons = new ArrayList<>();

            members.forEach(member -> {
                buttons.add(Button.primary(member.getId(), member.getEffectiveName()));
            });

            ReplyCallbackAction action = event.reply("Select user to horras");

            while (!buttons.isEmpty()) {
                List<Button> buttonsForRow = buttons.stream().limit(5).collect(Collectors.toList());
                buttons.removeAll(buttonsForRow);
                action.addActionRow(buttonsForRow);
            }

            action.queue();

        }).onError(error -> {
            // Handle any errors that occur while loading members
            System.err.println("Failed to load members: " + error.getMessage());
        });

    }

    


    @Override
    public SlashCommandData getSlashCommandData() {
        SlashCommandData options = this.getBareBonesSlashCommandData();
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
