package com.kodo.commands;

import java.util.HashSet;
import java.util.Set;

import org.reflections.Reflections;

public class CommandHandler {
    
    private final Set<Command> commands;

    public CommandHandler() {
        //Scan all the classes in this package for commands, then dynamically load them
        this.commands = new HashSet<>();
        this.loadCommands();
    }

    private void loadCommands() {
       
        Reflections reflections = new Reflections("path.to.package");
        Set<Class<? extends Command>> clazzes = reflections.getSubTypesOf(Command.class);

        clazzes.forEach(commandClazz -> {
            try {
                Command command = commandClazz.getConstructor().newInstance();
                this.commands.add(command);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

}
