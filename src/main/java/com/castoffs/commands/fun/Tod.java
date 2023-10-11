package com.castoffs.commands.fun;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import com.castoffs.commands.Category;
import com.castoffs.commands.Command;
import com.castoffs.commands.CommandButton;
import com.castoffs.commands.CommandMeta;
import com.castoffs.commands.CommandRecivedEvent;
import com.castoffs.database.tod.TruthOrDare;
import com.castoffs.database.tod.TruthOrDareType;
import com.castoffs.handler.Dependencies;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

@CommandMeta(alias = {"tod"},
             description = "tod",
             category = Category.FUN,
             usage = {"tod"},
             examples = {"tod"})
public class Tod extends Command{

    private long DELAY = 1000 * 60;
    private Color COLOR = Color.pink;

    public Tod(Dependencies dependencies) {
        super(dependencies);
    }

    @Override
    public void onCommandRecieved(CommandRecivedEvent event) {
        Message message = event.getMessage();
        this.reply(message, TruthOrDareType.any(), event.getAuthor());
    }

    @Override
    public @Nonnull List<Permission> getDefaultMemberPermissions() {
        return this.defaultCommandPermissions();
    }

    @SuppressWarnings("null")
    public void reply(Object messageInteraction, TruthOrDareType type, User caller){
        TruthOrDare tod = this.dependencies.getStorage().getInformationStorage().getTruthOrDare().getTruthOrDare();

        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(COLOR);
        embed.setFooter("Type: " + type.toString() + " | " + UUID.randomUUID().toString() + " | " + caller.getEffectiveName(), caller.getAvatarUrl());
        
        if(type == TruthOrDareType.TRUTH){
            embed.setTitle(tod.getWrapper().getRandomTruth());
        }
        if(type == TruthOrDareType.DARE){
            embed.setTitle(tod.getWrapper().getRandomDare());
        }

        CommandButton any = CommandButton.primary("Random", (e) -> {
            Message msg = e.getMessage();
            this.reply(msg, TruthOrDareType.any(), e.getUser());
            e.deferEdit().queue();
        });

        CommandButton truth = CommandButton.success("Truth", (e) -> {
            Message msg = e.getMessage();
            this.reply(msg, TruthOrDareType.TRUTH, e.getUser());
            e.deferEdit().queue();
        });

        CommandButton dare = CommandButton.danger("Dare", (e) -> {
            Message msg = e.getMessage();
            this.reply(msg, TruthOrDareType.DARE, e.getUser());
            e.deferEdit().queue();
        });

        CommandButton change = CommandButton.secondary("Change", (e) -> {
            Message msg = e.getMessage();
            
            if(type == TruthOrDareType.TRUTH){
                embed.setTitle(tod.getWrapper().getRandomTruth());
            }
            if(type == TruthOrDareType.DARE){
                embed.setTitle(tod.getWrapper().getRandomDare());
            }

            msg.editMessageEmbeds(embed.build()).queue();
            e.deferEdit().queue();
        });

        List<CommandButton> buttons = new ArrayList<>();
        buttons.add(any);
        buttons.add(truth);
        buttons.add(dare);
        buttons.add(change);

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
                        EmbedBuilder builder = embed.setFooter("Type: " + type.toString() + " | " + UUID.randomUUID().toString() + " | " + caller.getEffectiveName(), caller.getAvatarUrl());
                        event.getHook().editOriginalEmbeds(builder.build()).setActionRow(disabledButtons).queue();
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
                        EmbedBuilder builder = embed.setFooter("Type: " + type.toString() + " | " + UUID.randomUUID().toString() + " | " + caller.getEffectiveName(), caller.getAvatarUrl());
                        msg.editMessageEmbeds(builder.build()).setActionRow(disabledButtons).queue();
                    }
                }, DELAY);
            }, (fail -> {}));
        }
    }
}
