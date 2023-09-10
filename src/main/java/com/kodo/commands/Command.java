package com.kodo.commands;

import javax.annotation.Nonnull;

import org.jetbrains.annotations.NotNull;

import com.kodo.embeds.EmbedMaker;
import com.kodo.handler.Dependencies;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

/*
 * This is the base class for all the commands
 * TODO: hook buttons to their command
 */
public abstract class Command implements EventListener{

    protected final CommandCooldown cooldown;
    protected final Dependencies dependencies;

    /**
     * This method is called when a slash command is invoked
     * @param event the event that was fired
     */
    public abstract void onSlashCommandInteraction(SlashCommandInteractionEvent event);

    /**
     * This method is called when a button is pressed
     * @param event the event that was fired
     */
    public abstract void onButtonPressed(ButtonInteractionEvent event);

    public void handleInteraction(SlashCommandInteractionEvent event) {

        try{
            if(!this.cooldown.isOnCooldown(event.getUser().getId(), event.getGuild())){
                this.onSlashCommandInteraction(event);
                this.cooldown.applyCooldown(event.getUser().getId());
            }else{
                EmbedBuilder builder = EmbedMaker.ERROR_BASIC(event.getUser());
                builder.setTitle("You're on cooldown!");
                builder.appendDescription("You can't do that command yet, please wait.");
                builder.addField("Time remaining", "```" + this.cooldown.getRemainingCooldownFormatted(event.getUser().getId()) + "```", true);
                event.replyEmbeds(builder.build()).queue();
            }
        }catch(Exception e){
            EmbedBuilder builder = EmbedMaker.ERROR(event.getUser(), "Whoops, an error has occured.", e.getLocalizedMessage());
            event.replyEmbeds(builder.build()).queue();
            e.printStackTrace();
        }
    }

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

        //get the duration from the command meta
        this.cooldown = new CommandCooldown(this.getMetaInformation().cooldown());

        this.dependencies.getDiscord().addEventListener(this);
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

    @Override
    public void onEvent(@Nonnull GenericEvent event){
        if (event instanceof ButtonInteractionEvent) {
            ButtonInteractionEvent messageEvent = (ButtonInteractionEvent) event;
            Button button = messageEvent.getButton();        
            String btnId = button.getId();

            if(btnId == null) return;

        }
    }

}
