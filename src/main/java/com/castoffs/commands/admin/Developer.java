package com.castoffs.commands.admin;

import com.castoffs.commands.CommandMeta;
import com.castoffs.handler.Dependencies;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

/**
 * developer command used for testing purposes
 */
@CommandMeta(description = "Returns the developer command", name = "dev")
public class Developer extends AdminCommand{

    public Developer(Dependencies dependencies) {
        super(dependencies);
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {}


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
