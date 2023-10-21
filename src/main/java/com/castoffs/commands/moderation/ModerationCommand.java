package com.castoffs.commands.moderation;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.castoffs.commands.Command;
import com.castoffs.handler.Dependencies;

import net.dv8tion.jda.api.Permission;

public abstract class ModerationCommand extends Command{

    public ModerationCommand(Dependencies dependencies) {
        super(dependencies);
    }
    
    @Override
    public @Nonnull List<Permission> defaultCommandPermissions() {
        List<Permission> permissions = new ArrayList<>();
        permissions.add(Permission.MESSAGE_HISTORY);
        permissions.add(Permission.KICK_MEMBERS);
        return permissions;
    }

    @Override
    public @Nonnull List<Permission> getDefaultMemberPermissions(){
        return this.defaultCommandPermissions();
    }

}
