package com.castoffs.commands.fun;

import java.awt.Color;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import com.castoffs.bot.UserReference;
import com.castoffs.commands.Category;
import com.castoffs.commands.Command;
import com.castoffs.commands.CommandMeta;
import com.castoffs.commands.CommandRecivedEvent;
import com.castoffs.handler.Dependencies;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;

@CommandMeta(alias = {"rizz"},
             description = "Rizz of a person.",
             category = Category.FUN,
             usage = {"rizz <user:text required>"},
             examples = {"rizz @syntexuwu"})
public class RizzTest extends Command{

    public RizzTest(Dependencies dependencies) {
        super(dependencies);
    }

    @Override
    public void onCommandRecieved(CommandRecivedEvent event) {
        
        //find the user
        List<Member> users = event.getMessage().getMentions().getMembers().stream().distinct().collect(Collectors.toList());

        if(users.size() == 0){
            users.add(event.getMember());
        }

        //get the user
        Member user = users.get(0);

        EmbedBuilder EmbedBuilder = new EmbedBuilder();
        EmbedBuilder.setTitle(user.getEffectiveName() + "'s rizz");
        EmbedBuilder.setColor(Color.pink);
        EmbedBuilder.setDescription("let us analyse this user to the best of our abilities.");


        String rizz = "";

        int score = ThreadLocalRandom.current().nextInt(0, 100 + 1);

        for(int i = 0; i < 10; i++){
            if(i < score/10){
                rizz += "▓";
            }else{
                rizz += "▒";
            }
        }


        if(user.getId().equals(UserReference.MAPLE.getId())){
            score = Integer.MAX_VALUE;
            rizz = "💖💖💖💖💖";
            EmbedBuilder.addField("Rizz Score", "`" + "Infinity Ꝏ" + "`", true);
            EmbedBuilder.addField("Rizz meter", rizz, true);
            EmbedBuilder.addField("Conclusion", "`" + this.getConclusion(score) + "`", false);
            event.getChannel().sendMessageEmbeds(EmbedBuilder.build()).queue();
            return;
        }


        EmbedBuilder.addField("Rizz Score", "`" + score + "/100" + "`", true);
        EmbedBuilder.addField("Rizz meter", rizz, true);
        EmbedBuilder.addField("Conclusion", "`" + this.getConclusion(score) + "`", false);



        event.getChannel().sendMessageEmbeds(EmbedBuilder.build()).queue();
    }

    private @Nonnull String getConclusion(int value){
        if(value > 100) return "Rizz incarnate.";
        if(value > 90) return "On a scale of 1 to 10, I would rate you as a 9 and that is just because I am the one you are missing.";
        if(value > 80) return "So, aside from taking my breath away, what do you do for a living?";
        if(value > 70) return "I ought to complain to Spotify for you not being named this week’s hottest single.";
        if(value > 60) return "Are you a parking ticket? ‘Cause you’ve got ‘fine’ written all over you.";
        if(value > 50) return "You’ve got a lot of beautiful curves, but your smile is absolutely my favorite.";
        if(value > 40) return "Do you have a map? I just got lost in your eyes.";
        if(value > 30) return "You know what you would look really beautiful in? My arms.";
        if(value > 20) return "I think there’s something wrong with my phone. Could you try calling it to see if it works?";
        if(value < 0) return "Anti-Rizz incarnate.";
        
        return "hi, i am rizz please love me.";
    }

    @Override
    public @Nonnull List<Permission> getDefaultMemberPermissions() {
        return this.defaultCommandPermissions();
    }
    
}

