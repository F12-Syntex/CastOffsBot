package com.castoffs.commands.developer;

import java.util.List;

import javax.annotation.Nonnull;

import com.castoffs.commands.Category;
import com.castoffs.commands.Command;
import com.castoffs.commands.CommandMeta;
import com.castoffs.commands.CommandRecivedEvent;
import com.castoffs.embeds.EmbedMaker;
import com.castoffs.handler.Dependencies;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;

@CommandMeta(alias = {"ping"},
             description = "check the ping of the bot",
             category = Category.ADMIN,
             nsfw = false,
             usage = {"ping"},
             cooldown = 0,
             examples = {"ping"})
public class Ping extends Command{

    public Ping(Dependencies dependencies) {
        super(dependencies);
    }

    @Override
    public void onCommandRecieved(CommandRecivedEvent event) {
        EmbedBuilder builder = EmbedMaker.INFO(event.getAuthor(), "Ping: " + event.getJDA().getGatewayPing() + "ms");
        event.getMessage().replyEmbeds(builder.build()).queue();
    }

    @Override
    public @Nonnull List<Permission> getDefaultMemberPermissions() {
        return this.defaultCommandPermissions();
    }
    
}
