package com.castoffs.commands.developer;

import com.castoffs.commands.CommandMeta;
import com.castoffs.commands.CommandRecivedEvent;
import com.castoffs.handler.Dependencies;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;

import java.util.List;

import javax.annotation.Nonnull;

import com.castoffs.commands.Category;
import com.castoffs.commands.Command;

@CommandMeta(alias = {"dev", "developer"},
             description = "a developer command to test functionality",
             category = Category.ADMIN,
             nsfw = false,
             usage = {"dev"},
             cooldown = 0,
             examples = {"dev"})
public class Developer extends Command{

    public Developer(Dependencies dependencies) {
        super(dependencies);
    }

    @Override
    public void onCommandRecieved(CommandRecivedEvent event) {
        
    }

    @Override
    public @Nonnull List<Permission> getDefaultMemberPermissions() {
        List<Permission> perms = this.defaultCommandPermissions();
        perms.add(Permission.ADMINISTRATOR);
        return perms;
    }
    
}
