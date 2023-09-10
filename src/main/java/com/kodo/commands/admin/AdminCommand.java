package com.kodo.commands.admin;

import java.awt.Color;

import org.jetbrains.annotations.NotNull;

import com.kodo.commands.Command;
import com.kodo.embeds.EmbedMaker;
import com.kodo.handler.Dependencies;
import com.kodo.utils.UserUtils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;

public abstract class AdminCommand extends Command{

    public AdminCommand(Dependencies dependencies) {
        super(dependencies);
    }

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
