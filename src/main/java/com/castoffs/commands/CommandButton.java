package com.castoffs.commands;

import java.util.UUID;

import javax.annotation.Nonnull;

import com.castoffs.bot.Castoffs;

import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;
import net.dv8tion.jda.internal.interactions.component.ButtonImpl;
import net.dv8tion.jda.internal.utils.Checks;

public class CommandButton extends ButtonImpl implements EventListener{

    private ButtonInteractable interaction;

    public CommandButton(String string, String label, ButtonStyle primary, boolean b, Emoji emoji, ButtonInteractable interaction) {
        super(string, label, primary, b, emoji);
        this.interaction = interaction;
        Castoffs.getInstance().getDiscord().addEventListener(this);
    }

    @Nonnull
    public static CommandButton primary(@Nonnull String label, @Nonnull ButtonInteractable interaction) {
      UUID uuid = UUID.randomUUID();
      Checks.notEmpty(label, "Label");
      Checks.notLonger(uuid.toString(), 100, "Id");
      Checks.notLonger(label, 80, "Label");
      return new CommandButton(uuid.toString(), label, ButtonStyle.PRIMARY, false, (Emoji)null, interaction);
    }

    @Nonnull
    public static CommandButton secondary(@Nonnull String label, @Nonnull ButtonInteractable interaction) {
      UUID uuid = UUID.randomUUID();
      Checks.notEmpty(label, "Label");
      Checks.notLonger(uuid.toString(), 100, "Id");
      Checks.notLonger(label, 80, "Label");
      return new CommandButton(uuid.toString(), label, ButtonStyle.SECONDARY, false, (Emoji)null, interaction);
    }

    @Nonnull
    public static CommandButton success(@Nonnull String label, @Nonnull ButtonInteractable interaction) {
      UUID uuid = UUID.randomUUID();
      Checks.notEmpty(label, "Label");
      Checks.notLonger(uuid.toString(), 100, "Id");
      Checks.notLonger(label, 80, "Label");
      return new CommandButton(uuid.toString(), label, ButtonStyle.SUCCESS, false, (Emoji)null, interaction);
    }

    @Nonnull
    public static CommandButton danger(@Nonnull String label, @Nonnull ButtonInteractable interaction) {
      UUID uuid = UUID.randomUUID();
      Checks.notEmpty(label, "Label");
      Checks.notLonger(uuid.toString(), 100, "Id");
      Checks.notLonger(label, 80, "Label");
      return new CommandButton(uuid.toString(), label, ButtonStyle.DANGER, false, (Emoji)null, interaction);
    }

    public void disable(){
        Castoffs.getInstance().getDiscord().removeEventListener(this);
    }

    @Override
    public void onEvent(@Nonnull GenericEvent event) {
        if(event instanceof ButtonInteractionEvent){
            ButtonInteractionEvent buttonEvent = (ButtonInteractionEvent) event;
            if(buttonEvent.getComponentId().equals(this.getId())){
                interaction.onButtonPressed(buttonEvent);
            }
        }
    }

}
