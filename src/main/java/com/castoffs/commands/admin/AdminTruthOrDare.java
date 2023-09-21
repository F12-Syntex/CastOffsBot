package com.castoffs.commands.admin;

import java.util.List;

import com.castoffs.commands.Category;
import com.castoffs.commands.CommandMeta;
import com.castoffs.embeds.EmbedMaker;
import com.castoffs.handler.Dependencies;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

/**
 * developer command used for testing purposes
 */
@CommandMeta(description = "adds custom tod options", name = "admintod", category = Category.ADMIN)
public class AdminTruthOrDare extends AdminCommand{

    public AdminTruthOrDare(Dependencies dependencies) {
        super(dependencies);
    }

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        List<OptionMapping> truth = event.getOptions();

        StringBuilder sb = new StringBuilder();

        boolean hasTruth = truth.stream().anyMatch(option -> option.getName().equals("truth"));
        boolean hasDare = truth.stream().anyMatch(option -> option.getName().equals("dare"));

        if(!(hasTruth && hasDare)){
            EmbedBuilder builder = EmbedMaker.ERROR(event.getUser(), "Invalid options", "You must provide a truth and/or a dare");
            event.replyEmbeds(builder.build()).queue();
            return;
        }

        if(hasTruth){
            String truthOption = truth.stream().filter(option -> option.getName().equals("truth")).findFirst().get().getAsString();
            this.dependencies.getStorage().getInformationStorage().getTruthOrDare().addTruth(truthOption);
            sb.append("truth added: " + truthOption + "\n");
        }

        if(hasDare){
            String dareOption = truth.stream().filter(option -> option.getName().equals("dare")).findFirst().get().getAsString();
            this.dependencies.getStorage().getInformationStorage().getTruthOrDare().addDare(dareOption);
            sb.append("dare added: " + dareOption + "\n");
        }

        EmbedBuilder builder = EmbedMaker.INFO(event.getUser(), "Entries updated.");
        builder.addField("Truths", "`" + this.dependencies.getStorage().getInformationStorage().getTruthOrDare().getTruthOrDare().getTruths().size() + "`", false);
        builder.addField("Dare", "`" + this.dependencies.getStorage().getInformationStorage().getTruthOrDare().getTruthOrDare().getDares().size() + "`", false);
        event.replyEmbeds(builder.build()).queue();
    }

    


    @Override
    public SlashCommandData getSlashCommandData() {
        SlashCommandData options = this.getBareBonesSlashCommandData()
                                        .addOption(OptionType.STRING, "truth", "add a truth", false)
                                        .addOption(OptionType.STRING, "dare", "add a dare", false);
        return options;
    }
    
    @Override
    public DefaultMemberPermissions getDefaultMemberPermissions() {
       return DefaultMemberPermissions.ENABLED;
    }

    @Override
    public void onButtonPressed(ButtonInteractionEvent event) {
        return;
    }
    
}
