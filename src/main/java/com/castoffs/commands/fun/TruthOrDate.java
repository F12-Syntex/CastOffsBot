package com.castoffs.commands.fun;

import java.awt.Color;

import com.castoffs.commands.Category;
import com.castoffs.commands.Command;
import com.castoffs.commands.CommandButton;
import com.castoffs.commands.CommandMeta;
import com.castoffs.database.tod.TruthOrDare;
import com.castoffs.handler.Dependencies;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

@CommandMeta(name = "tod", description = "returns a random truth or dare", category = Category.INFO)
public class TruthOrDate extends Command{

    public TruthOrDate(Dependencies dependencies) {
        super(dependencies);
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        TruthOrDare tod = this.dependencies.getStorage().getInformationStorage().getTruthOrDare().getTruthOrDare();

        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.orange);
        embed.setTitle(tod.getRandomTruth());

        CommandButton button = CommandButton.primary("Random", (e) -> {
            e.replyEmbeds(embed.setTitle(tod.getRandomTruth()).build()).queue();
            TruthOrDate.this.onSlashCommandInteraction(event);
        });

        event.replyEmbeds(embed.build()).addActionRow(button).queue();
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
