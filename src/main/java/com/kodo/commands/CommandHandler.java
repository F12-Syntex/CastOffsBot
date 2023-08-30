package com.kodo.commands;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import org.reflections.Reflections;
public class CommandHandler {
    
    private final Set<Command> commands;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public CommandHandler() {
        //Scan all the classes in this package for commands, then dynamically load them
        this.commands = new HashSet<>();
    }

    public void loadCommands() {
       
        //scan all classes in this package for commands
        Reflections reflections = new Reflections(this.getClass().getPackage().getName());

        //log the package we are scanning
        logger.info("Scanning for commands in package: " + this.getClass().getPackage().getName());

        //get all classes that extend Command
        Set<Class<? extends Command>> clazzes = reflections.getSubTypesOf(Command.class);

        //for every class ( which is a command ) add it to our commands set
        clazzes.forEach(commandClazz -> {
            try {
                
                Command command = commandClazz.getConstructor().newInstance();

                CommandMeta meta = command.getMetaInformation();
                logger.info("Command loaded: " + meta.name());
                
                this.commands.add(command);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        logger.info("Loaded " + this.commands.size() + " command(s)");
    }

}
