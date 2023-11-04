package com.castoffs.commands.jackbox;

import java.util.List;

import javax.annotation.Nonnull;

import com.castoffs.commands.Command;
import com.castoffs.handler.Dependencies;

import net.dv8tion.jda.api.Permission;

public abstract class JackBoxCommands extends Command{

    public JackBoxCommands(Dependencies dependencies) {
        super(dependencies);
    }

    @Override
    public @Nonnull List<Permission>  getDefaultMemberPermissions() {
        return this.defaultCommandPermissions();
    }
    
}
