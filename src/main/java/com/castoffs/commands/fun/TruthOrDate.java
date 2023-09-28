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
import com.castoffs.database.tod.TruthOrDareType;
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

    private long DELAY = 1000 * 60;
    private Color COLOR = Color.pink;

    public TruthOrDate(Dependencies dependencies) {
        super(dependencies);
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        this.reply(event, TruthOrDareType.any());
    }

    @SuppressWarnings("null")
    public void reply(Object messageInteraction, TruthOrDareType type){
        TruthOrDare tod = this.dependencies.getStorage().getInformationStorage().getTruthOrDare().getTruthOrDare();

        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(COLOR);
        embed.setFooter("Type: " + type.toString() + " | " + UUID.randomUUID().toString());
        
        if(type == TruthOrDareType.TRUTH){
            embed.setTitle(tod.getRandomTruth());
        }
        if(type == TruthOrDareType.DARE){
            embed.setTitle(tod.getRandomDare());
        }

        CommandButton any = CommandButton.primary("Random", (e) -> {
            Message msg = e.getMessage();
            this.reply(msg, TruthOrDareType.any());
            e.deferEdit().queue();
        });

        CommandButton truth = CommandButton.success("Truth", (e) -> {
            Message msg = e.getMessage();
            this.reply(msg, TruthOrDareType.TRUTH);
            e.deferEdit().queue();
        });

        CommandButton dare = CommandButton.danger("Dare", (e) -> {
            Message msg = e.getMessage();
            this.reply(msg, TruthOrDareType.DARE);
            e.deferEdit().queue();
        });

        List<CommandButton> buttons = new ArrayList<>();
        buttons.add(any);
        buttons.add(truth);
        buttons.add(dare);

        if(messageInteraction instanceof SlashCommandInteractionEvent){
            SlashCommandInteractionEvent event = (SlashCommandInteractionEvent) messageInteraction;
            event.replyEmbeds(embed.build()).addActionRow(buttons).queue((msg) -> {
                //disable the button after 10 seconds
                Timer timer = new Timer();
                timer.schedule(new java.util.TimerTask() {
                    @Override
                    public void run() {
                        buttons.forEach(button -> button.disable());
                        //remove the button from the message
                        List<Button> disabledButtons = buttons.stream().map(o -> o.asDisabled()).collect(Collectors.toList());
                        event.getHook().editOriginalEmbeds(embed.build()).setActionRow(disabledButtons).queue();
                    }
                }, DELAY);
            }, (fail -> {}));
        }else{
            Message message = (Message) messageInteraction;
            message.replyEmbeds(embed.build()).addActionRow(buttons).queue((msg) -> {
                //disable the button after 10 seconds
                Timer timer = new Timer();
                timer.schedule(new java.util.TimerTask() {
                    @Override
                    public void run() {
                        buttons.forEach(button -> button.disable());
                        //remove the button from the message
                        List<Button> disabledButtons = buttons.stream().map(o -> o.asDisabled()).collect(Collectors.toList());
                        msg.editMessageEmbeds(embed.build()).setActionRow(disabledButtons).queue();
                    }
                }, DELAY);
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
