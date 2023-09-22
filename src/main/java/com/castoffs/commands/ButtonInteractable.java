package com.castoffs.commands;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

@FunctionalInterface
public interface ButtonInteractable {
    public abstract void onButtonPressed(ButtonInteractionEvent event);
}
