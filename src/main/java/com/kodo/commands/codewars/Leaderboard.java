package com.kodo.commands.codewars;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.kodo.commands.CommandMeta;
import com.kodo.database.users.scheme.User;
import com.kodo.embeds.PagedEmbed;
import com.kodo.handler.Dependencies;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

@CommandMeta(description = "Displays all the users sorted by score", name = "leaderboard")
public class Leaderboard extends CodeWarsCommand {

    private final int PAGE_SIZE = 5;

    public Leaderboard(Dependencies dependencies) {
        super(dependencies);
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        PagedEmbed embedBuilder = new PagedEmbed();

        List<User> users = this.dependencies
                                .getStorage()
                                .getUserStorage()
                                .getUsers()
                                .stream()
                                .map(config -> config.getProfile().getUser())
                                .sorted((a, b) -> b.getHonor() - a.getHonor())
                                .collect(Collectors.toList());


        List<EmbedBuilder> pages = new ArrayList<>();

        int rank = 0;
        for (int i = 0; i < users.size(); i+=PAGE_SIZE) {
            EmbedBuilder currentPage = new EmbedBuilder();
            currentPage.setColor(Color.ORANGE);
            currentPage.setTitle("CompSoc Leaderboard");
            currentPage.setDescription("Here are the top users of compsoc");
            currentPage.setThumbnail(this.dependencies.getDiscord().getSelfUser().getAvatarUrl());

            StringBuilder ranks = new StringBuilder();
            StringBuilder names = new StringBuilder();
            StringBuilder scores = new StringBuilder();
            StringBuilder completed = new StringBuilder();

            for (int j = i; j < i+PAGE_SIZE && j < users.size(); j++) {
                User user = users.get(j);
                names.append(this.getEmojiForRank(rank) + user.getUsername()).append("\n");
                ranks.append("#" + (++rank)).append("\n");
                scores.append(user.getHonor()).append("\n");
                completed.append(user.getCodeChallenges().getTotalCompleted()).append("\n");
            }

            currentPage.addField("Rank", "`" + ranks.toString() + "`\t", true);
            currentPage.addField("Author", names.toString() + "\t", true);
            currentPage.addField("Score", "`" + scores.toString() + "`\t", true);
            currentPage.addField("Completed", "`" + completed.toString() + "`\t", true);
            pages.add(currentPage);
        }      

        embedBuilder.setPages(pages);
        embedBuilder.send(event);
    }

    private String getEmojiForRank(int rank){
        if(rank == 0) return ":trophy:";
        if(rank == 1) return ":medal:";
        if(rank == 2) return ":military_medal:";
        if(rank <= 5) return ":star:"; 
        return ":rock:";
    }

    @Override
    public void onButtonPressed(ButtonInteractionEvent event) {
        return;
    }

    @Override
    public SlashCommandData getSlashCommandData() {
        SlashCommandData options = this.getBareBonesSlashCommandData();
        return options;
    }
    
    @Override
    public DefaultMemberPermissions getDefaultMemberPermissions() {
       return DefaultMemberPermissions.ENABLED;
    }
    
}
