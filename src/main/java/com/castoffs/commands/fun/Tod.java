package com.castoffs.commands.fun;

import java.util.List;

import javax.annotation.Nonnull;

import com.castoffs.commands.Category;
import com.castoffs.commands.Command;
import com.castoffs.commands.CommandMeta;
import com.castoffs.commands.CommandRecivedEvent;
import com.castoffs.handler.Dependencies;

import net.dv8tion.jda.api.Permission;

@CommandMeta(alias = {"tod"},
             description = "tod",
             category = Category.FUN,
             usage = {"tod"},
             examples = {"tod"})
public class Tod extends Command{

    public Tod(Dependencies dependencies) {
        super(dependencies);
    }

    @Override
    public void onCommandRecieved(CommandRecivedEvent event) {

        
    }

    @Override
    public @Nonnull List<Permission> getDefaultMemberPermissions() {
        return this.defaultCommands();
    }
    
}
