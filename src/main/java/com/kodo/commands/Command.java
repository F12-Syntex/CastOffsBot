package com.kodo.commands;

public abstract class Command {
    
    public CommandMeta getMetaInformation() {
        return this.getClass().getAnnotation(CommandMeta.class);
    } 

}
