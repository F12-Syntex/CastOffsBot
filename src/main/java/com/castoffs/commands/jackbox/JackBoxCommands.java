package com.castoffs.commands.jackbox;

import java.util.List;

import javax.annotation.Nonnull;

import com.castoffs.commands.Command;
import com.castoffs.handler.Dependencies;

import net.dv8tion.jda.api.Permission;

public abstract class JackBoxCommands extends Command{

    protected String gameName;

    public JackBoxCommands(Dependencies dependencies, String gameName) {
        super(dependencies);
        this.gameName = gameName;
    }

    @Override
    public @Nonnull List<Permission>  getDefaultMemberPermissions() {
        return this.defaultCommandPermissions();
    }
    
    public String getGameName(){
        return this.gameName;
    }

}
