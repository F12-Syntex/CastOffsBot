package com.kodo.commands.codewars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.kodo.codewars.scraper.CodewarsKata;
import com.kodo.commands.CommandMeta;
import com.kodo.embeds.PagedEmbed;
import com.kodo.handler.Dependencies;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;

@CommandMeta(description = "Returns a random code wars challenge", name = "rkata", cooldown = 1000L)
public class RandomKata extends CodeWarsCommand {

    public RandomKata(Dependencies dependencies) {
        super(dependencies);
    }

    @SuppressWarnings("null")
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        int difficulty = -1;
        boolean shortDescription = false;

        if(event.getOption("difficulty") != null){
            difficulty = (int) event.getOption("difficulty").getAsLong();
        }

        if(event.getOption("short") != null){
            shortDescription = event.getOption("short").getAsBoolean();
        }

        CodewarsKata challenge = this.dependencies
                                        .getStorage()
                                        .getCodewarsStorage()
                                        .getChallenges()
                                        .getKataInformation()
                                        .getRandomChallenge(difficulty, shortDescription);

        EmbedBuilder page1 = new EmbedBuilder();
        page1.setColor(challenge.getRank().getColorEnum());
        page1.setTitle(challenge.getName(), challenge.getUrl());

        String description = challenge.getDescription();

        if(description.length() > 4000){
            description = description.substring(0, 4000) + "...";
        }

        page1.setDescription(description);

        EmbedBuilder page2 = new EmbedBuilder();

        page2.setColor(challenge.getRank().getColorEnum());
        page2.setTitle(challenge.getName(), challenge.getUrl());

        page2.addField("Tags", "```" + Arrays.toString(challenge.getTags().toArray()) + "```", false);
        page2.addField("Rank", "```" + challenge.getRank().getName() + "```", false);
        page2.addField("Category", "```" + challenge.getCategory() + "```", false);
        page2.addField("Languages", "```" + Arrays.toString(challenge.getLanguages().toArray()) + "```", false);
        page2.addField("Stars", "```" + challenge.getTotalStars() + "```", true);
        page2.addField("Completed", "```" + challenge.getTotalCompleted() + "```", true);
        page2.addField("Approved At", "```" + challenge.getApprovedAt() + "```", true);

        if(challenge.getApprovedBy() != null){
            page2.addField("Approved By",  "```" + challenge.getApprovedBy().getUsername()  + "```", true);
        }

        page2.addField("Author", "```" + challenge.getCreatedBy().getUsername()  + "```", true);
        page2.addField("Total Attempts", "```" + challenge.getTotalAttempts() + "```", true);
        page2.addField("Vote score", "```" + challenge.getVoteScore() + "```", true); 

        PagedEmbed builder = new PagedEmbed();
        builder.appendPages(page1, page2);

        Button button1 = Button.primary("codewars_profile", "View Kata")
            .withUrl(challenge.getUrl())
            .withStyle(ButtonStyle.LINK);

        List<Button> buttons = new ArrayList<>();
        buttons.add(button1);

        // event.replyEmbeds(builder.build()).setActionRow(button).queue();
        builder.send(event, buttons);
    }

    @Override
    public SlashCommandData getSlashCommandData() {
        SlashCommandData data =  this.getBareBonesSlashCommandData()
                        .addOption(OptionType.INTEGER, "difficulty", "The difficulty of the kata", false)
                        .addOption(OptionType.BOOLEAN, "short", "If the description should be short", false);
        
        OptionData difficulty = data.getOptions().get(0);
        difficulty.addChoice("1", 1);
        difficulty.addChoice("2", 2);
        difficulty.addChoice("3", 3);
        difficulty.addChoice("4", 4);
        difficulty.addChoice("5", 5);
        difficulty.addChoice("6", 5);
        difficulty.addChoice("7", 5);
        difficulty.addChoice("8", 5);

        return data;
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
