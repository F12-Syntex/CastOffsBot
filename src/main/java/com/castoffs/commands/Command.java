package com.castoffs.commands;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.jetbrains.annotations.NotNull;

import com.castoffs.handler.Dependencies;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

/*
 * This is the base class for all the commands
 * TODO: hook buttons to their command
 * TODO: create a custom button with listeners in built, this will allow for more control over buttons and reduces need for event listeners
 */
public abstract class Command {

    protected final CommandCooldown cooldown;
    protected final Dependencies dependencies;

    protected final List<Button> buttons = new ArrayList<>();

    /**
     * this method is called after all the commands are registered
     */
    public void postCommandRegisteration(){}

    /**
     * This method is called when a command is recieved
     * @param event the CommandRecivedEvent that triggered this command
     */
    public abstract void onCommandRecieved(CommandRecivedEvent event);

    /**
     * This method is called to retrieve the default member permissions required for this command
     * @return the default member permissions
     */
    public abstract @Nonnull List<Permission> getDefaultMemberPermissions();

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
    }

    public CommandCooldown getCooldown() {
        return cooldown;
    }

    public @Nonnull List<Permission> defaultCommands() {
        List<Permission> permissions = new ArrayList<>();
        permissions.add(Permission.MESSAGE_HISTORY);
        return permissions;
    }

}
