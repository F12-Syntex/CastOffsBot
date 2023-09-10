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

        CodewarsKata challenge = this.dependencies
                                        .getStorage()
                                        .getCodewarsStorage()
                                        .getChallenges()
                                        .getKataInformation()
                                        .getRandomChallenge();

        
        System.out.println(challenge.getRank().getColor());

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
            .withStyle(ButtonStyle.SUCCESS);

        Button button2 = Button.primary("codewars_reload", "Refresh")
            .withUrl(challenge.getUrl())
            .withStyle(ButtonStyle.SUCCESS);

        List<Button> buttons = new ArrayList<>();
        buttons.add(button1);
        buttons.add(button2);

        // event.replyEmbeds(builder.build()).setActionRow(button).queue();
        builder.send(event, buttons);
    }

    @Override
    public SlashCommandData getSlashCommandData() {
        return this.getBareBonesSlashCommandData();
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
