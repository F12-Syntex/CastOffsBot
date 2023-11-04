package com.castoffs.commands.fun;

import java.util.List;

import javax.annotation.Nonnull;

import com.castoffs.commands.Category;
import com.castoffs.commands.Command;
import com.castoffs.commands.CommandMeta;
import com.castoffs.commands.CommandRecivedEvent;
import com.castoffs.handler.Dependencies;

import net.dv8tion.jda.api.Permission;

@CommandMeta(alias = {"guesswho", "gw"},
             description = "Guess the castoffs member",
             category = Category.FUN,
             usage = {"guesswho"},
             completed = false,
             examples = {"dh"})
public class GuessWho extends Command {

    public GuessWho(Dependencies dependencies) {
        super(dependencies);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void onCommandRecieved(CommandRecivedEvent event) {}

    @Override
    public @Nonnull List<Permission> getDefaultMemberPermissions() {
        return this.defaultCommandPermissions();
    }
    
}
