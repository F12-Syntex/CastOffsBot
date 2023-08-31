package com.kodo.commands;

import org.jetbrains.annotations.NotNull;

import com.kodo.handler.Dependencies;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public abstract class Command {
    public abstract void onSlashCommandInteraction(SlashCommandInteractionEvent event);

    public abstract @NotNull SlashCommandData getSlashCommandData();
    public abstract @NotNull DefaultMemberPermissions getDefaultMemberPermissions();

    protected final Dependencies dependencies;

    public @NotNull CommandMeta getMetaInformation() {
        return this.getClass().getAnnotation(CommandMeta.class);
    } 

    public Command(Dependencies dependencies) {
        this.dependencies = dependencies;
    }


    protected SlashCommandData getBareBonesSlashCommandData() {
        String name = this.getMetaInformation().name();
        String description = this.getMetaInformation().description();
        DefaultMemberPermissions permissions = this.getDefaultMemberPermissions();

        if(name == null || description == null || permissions == null) throw new NullPointerException("Command name or description is null");

        return Commands.slash(name, description).setDefaultPermissions(permissions);
    }


}
