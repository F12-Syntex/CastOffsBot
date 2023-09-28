package com.castoffs.commands.fun;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.UUID;
import java.util.stream.Collectors;

import com.castoffs.commands.Category;
import com.castoffs.commands.Command;
import com.castoffs.commands.CommandButton;
import com.castoffs.commands.CommandMeta;
import com.castoffs.database.tod.TruthOrDare;
import com.castoffs.handler.Dependencies;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

@CommandMeta(name = "tod", description = "returns a random truth or dare", category = Category.INFO)
public class TruthOrDate extends Command{

    public TruthOrDate(Dependencies dependencies) {
        super(dependencies);
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        this.reply(event);
    }

    @SuppressWarnings("null")
    public void reply(Object messageInteraction){
        TruthOrDare tod = this.dependencies.getStorage().getInformationStorage().getTruthOrDare().getTruthOrDare();

        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.orange);
        embed.setTitle(tod.getRandomTruth());

        CommandButton button = CommandButton.primary("Random", (e) -> {
            Message msg = e.getMessage();
            this.reply(msg);
            e.deferEdit().queue();
        });

        List<Button> buttons = new ArrayList<>();
        buttons.add(button);

        if(messageInteraction instanceof SlashCommandInteractionEvent){
            SlashCommandInteractionEvent event = (SlashCommandInteractionEvent) messageInteraction;
            event.replyEmbeds(embed.build()).addActionRow(button).queue((msg) -> {
                //disable the button after 10 seconds
                Timer timer = new Timer();
                timer.schedule(new java.util.TimerTask() {
                    @Override
                    public void run() {
                        button.disable();
                        //remove the button from the message
                        List<Button> disabledButtons = buttons.stream().map(o -> o.asDisabled()).collect(Collectors.toList());
                        event.getHook().editOriginalEmbeds(embed.build()).setActionRow(disabledButtons).queue();
                    }
                }, 10000);
            }, (fail -> {}));
        }else{
            Message message = (Message) messageInteraction;
            message.replyEmbeds(embed.build()).addActionRow(button).queue((msg) -> {
                //disable the button after 10 seconds
                Timer timer = new Timer();
                timer.schedule(new java.util.TimerTask() {
                    @Override
                    public void run() {
                        button.disable();
                        //remove the button from the message
                        List<Button> disabledButtons = buttons.stream().map(o -> o.asDisabled()).collect(Collectors.toList());
                        msg.editMessageEmbeds(embed.build()).setActionRow(disabledButtons).queue();
                    }
                }, 10000);
            }, (fail -> {}));
        }

    }

    @Override
    public void onButtonPressed(ButtonInteractionEvent event) {}

    @Override
    public SlashCommandData getSlashCommandData() {
        return this.getBareBonesSlashCommandData();
    }

    @Override
    public DefaultMemberPermissions getDefaultMemberPermissions() {
        return DefaultMemberPermissions.ENABLED;
    }

}
