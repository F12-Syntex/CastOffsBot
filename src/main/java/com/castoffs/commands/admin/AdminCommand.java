package com.castoffs.commands.admin;

import java.awt.Color;

import org.jetbrains.annotations.NotNull;

import com.castoffs.commands.Command;
import com.castoffs.embeds.EmbedMaker;
import com.castoffs.handler.Dependencies;
import com.castoffs.utils.UserUtils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;

/**
 * This class is the base class for all admin commands
 */
public abstract class AdminCommand extends Command{

    public AdminCommand(Dependencies dependencies) {
        super(dependencies);
    }

    /**
     * This method is called when a slash command is invoked, prevents non developers/admins from using the command
     * @param event the slash command event
     */
    @Override
    public void handleInteraction(SlashCommandInteractionEvent event) {

        if(!UserUtils.isDeveloper(event.getUser().getId())){
            EmbedBuilder builder = EmbedMaker
                .ERROR(event.getUser(), "You can't do that.", "You are not a developer, this command is for developers only.")
                .setColor(Color.cyan);

            event.replyEmbeds(builder.build()).queue();
            return;
        }
        
        super.handleInteraction(event);
    }

    /**
     * This method is called to retrieve the default member permissions required for this command
     * @return the default member permissions
     */
    @Override
    public @NotNull DefaultMemberPermissions getDefaultMemberPermissions(){
        return DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR);
    }
    
}
