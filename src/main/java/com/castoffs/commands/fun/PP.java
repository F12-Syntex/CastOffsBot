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

@CommandMeta(alias = {"pp", "penis"},
             description = "Check the length of someones pp",
             category = Category.FUN,
             nsfw = false,
             usage = {"pp <user:text optional>"},
             cooldown = 10 * 1000,
             examples = {"pp", "pp @syntexuwu"})
public class PP extends Command{

    public PP(Dependencies dependencies) {
        super(dependencies);
    }

    @Override
    public void onCommandRecieved(CommandRecivedEvent event) {

        int length = ThreadLocalRandom.current().nextInt(1, 10);
        String penisBuilder = "8" + "=".repeat(length) + "D";

        User user = event.getAuthor();

        List<net.dv8tion.jda.api.entities.Member> pinged = event.getMessage().getMentions().getMembers();
        if(pinged.size() > 0){
            user = pinged.get(0).getUser();
        }

        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle(user.getEffectiveName() + "'s pp");
        builder.setDescription("#\n" + penisBuilder);
        builder.setColor(Color.pink);

        event.getMessage().replyEmbeds(builder.build()).queue();
    }

    @Override
    public @Nonnull List<Permission> getDefaultMemberPermissions() {
        return this.defaultCommandPermissions();
    }
    
}
