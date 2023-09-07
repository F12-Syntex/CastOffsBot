package com.kodo.commands;

import org.jetbrains.annotations.NotNull;

import com.kodo.handler.Dependencies;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

/*
 * This is the base class for all the commands
 */
public abstract class Command {
    /**
     * This method is called when a slash command is invoked
     * @param event the event that was fired
     */
    public abstract void onSlashCommandInteraction(SlashCommandInteractionEvent event);

    /**
     * This method is called to retrieve the slash command data for this command
     * @return the slash command data
     */
    public abstract @NotNull SlashCommandData getSlashCommandData();

    /**
     * This method is called to retrieve the default member permissions required for this command
     * @return the default member permissions
     */
    public abstract @NotNull DefaultMemberPermissions getDefaultMemberPermissions();

    protected final Dependencies dependencies;

    /**
     * This method is used to retrieve the meta information for this command
     * @return the meta information
     */
    public @NotNull CommandMeta getMetaInformation() {
        return this.getClass().getAnnotation(CommandMeta.class);
    } 

    /**
     * This is the constructor for the Command class
     * @param dependencies the dependencies required by the command
     */
    public Command(Dependencies dependencies) {
        this.dependencies = dependencies;
    }

    /**
     * This method is used to create the basic slash command data with name, description, and permissions
     * @return the bare bones slash command data
     */
    protected SlashCommandData getBareBonesSlashCommandData() {
        String name = this.getMetaInformation().name();
        String description = this.getMetaInformation().description();
        DefaultMemberPermissions permissions = this.getDefaultMemberPermissions();

        // Check if any of the required values are null
        if (name == null || description == null || permissions == null) {
            throw new NullPointerException("Command name, description, or permissions is null");
        }

        return Commands.slash(name, description).setDefaultPermissions(permissions);
    }
}
