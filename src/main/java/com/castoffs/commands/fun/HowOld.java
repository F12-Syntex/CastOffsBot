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
import net.dv8tion.jda.api.entities.User;

@CommandMeta(alias = {"howold", "hold"},
             description = "Guesses how old a user is.",
             category = Category.FUN,
             usage = {"howold"},
             cooldown = 0,
             examples = {"howold"})
public class HowOld extends Command{

    public HowOld(Dependencies dependencies) {
        super(dependencies);
    }

    @Override
    public void onCommandRecieved(CommandRecivedEvent event) {
        int age = ThreadLocalRandom.current().nextInt(1, 30);

        User user = event.getAuthor();

        List<net.dv8tion.jda.api.entities.Member> pinged = event.getMessage().getMentions().getMembers();
        if(pinged.size() > 0){
            user = pinged.get(0).getUser();
        }

        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle(user.getEffectiveName() + "'s age");
        builder.setDescription(age + " years old");
        builder.setColor(Color.pink);

        event.getMessage().replyEmbeds(builder.build()).queue();
    }

    @Override
    public @Nonnull List<Permission> getDefaultMemberPermissions() {
        return this.defaultCommandPermissions();
    }
    
}
