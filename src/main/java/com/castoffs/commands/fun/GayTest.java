package com.castoffs.commands.fun;

import java.awt.Color;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.Nonnull;

import com.castoffs.commands.Category;
import com.castoffs.commands.Command;
import com.castoffs.commands.CommandMeta;
import com.castoffs.commands.CommandRecivedEvent;
import com.castoffs.handler.Dependencies;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;

@CommandMeta(alias = {"hgay"},
             description = "Gayness of a person.",
             category = Category.FUN,
             usage = {"hgay <user:text required>"},
             examples = {"hgay @syntexuwu"})
public class GayTest extends Command{

    public GayTest(Dependencies dependencies) {
        super(dependencies);
    }

    @Override
    public void onCommandRecieved(CommandRecivedEvent event) {

        if(!event.isArgumentValidAtIndex(1)){
            throw new IllegalArgumentException("You need to mention a user.");
        }

        //find the user
        List<Member> users = event.getMessage().getMentions().getMembers();

        if(users.size() == 0){
            throw new IllegalArgumentException("You need to mention a user.");
        }

        //get the user
        Member user = users.get(0);

        EmbedBuilder EmbedBuilder = new EmbedBuilder();
        EmbedBuilder.setTitle("Gayness of " + user.getEffectiveName());
        EmbedBuilder.setColor(Color.pink);
        EmbedBuilder.setDescription("let us analyse this user to the best of our abilities.");


        String gayness = "";

        int score = ThreadLocalRandom.current().nextInt(0, 100 + 1);

        for(int i = 0; i < 10; i++){
            if(i < score/10){
                gayness += "▓";
            }else{
                gayness += "▒";
            }
        }

        EmbedBuilder.addField("Gay Score", score + "/100", true);
        EmbedBuilder.addField("Conclusion", this.getConclusion(score), true);
        EmbedBuilder.addField("Gay meter", gayness, true);



        event.getChannel().sendMessageEmbeds(EmbedBuilder.build()).queue();
    }

    private @Nonnull String getConclusion(int value){
        if(value > 90) return "Every-day Sausage Consumer";
        if(value > 80) return "Sausage Lover";
        if(value > 70) return "Carnivor";
        if(value > 60) return "Horny gay";
        if(value > 50) return "Gay";
        if(value > 40) return "Dude Fettish.";
        if(value > 30) return "I love fem boys";
        if(value > 20) return "Tingling";
        
        return "Straight";
    }

    @Override
    public @Nonnull List<Permission> getDefaultMemberPermissions() {
        return this.defaultCommands();
    }
    
}
