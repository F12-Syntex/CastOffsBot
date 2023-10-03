package com.castoffs.commands;

import java.awt.Color;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.reflections.Reflections;

import com.castoffs.bot.Castoffs;
import com.castoffs.bot.Settings;
import com.castoffs.embeds.EmbedMaker;
import com.castoffs.handler.Dependencies;
import com.castoffs.handler.Handler;
import com.castoffs.utils.HtmlUtils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
public class CommandHandler extends Handler{
    
    private final Set<Command> commands;
    private final Logger logger = Logger.getGlobal();
    private final Dependencies dependencies;

    /**
     * @param dependencies
     */
    public CommandHandler(Dependencies dependencies) {
        //Scan all the classes in this package for commands, then dynamically load them
        this.commands = new HashSet<>();
        this.dependencies = dependencies;
    }

    /**
     * dynamically load all the commands in this package
     */
    public void loadCommands() {
       
        //scan all classes in this package for commands
        Reflections reflections = new Reflections(this.getClass().getPackage().getName());

        //log the package we are scanning
        logger.info("Scanning for commands in package: " + this.getClass().getPackage().getName());

        //get all classes that extend Command
        Set<Class<? extends Command>> clazzes = reflections.getSubTypesOf(Command.class);

        //for every class ( which is a command ) add it to our commands set
        clazzes.forEach(commandClazz -> {
            
            if(commandClazz.isInterface() || Modifier.isAbstract(commandClazz.getModifiers())) return;

            try {
                
                Command command = commandClazz.getConstructor(Dependencies.class).newInstance(this.dependencies);

                CommandMeta meta = command.getMetaInformation();
                logger.info("Command loaded: " + meta.alias()[0]);
                
                this.commands.add(command);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        logger.info("Loaded " + this.commands.size() + " command(s)");

        logger.info("checking for duplicates");
        Set<String> aliases = new HashSet<>();
        this.commands.forEach(command -> {
            CommandMeta meta = command.getMetaInformation();
            for(String alias : meta.alias()){
                if(aliases.contains(alias)){
                    logger.warning("Duplicate command alias found: " + alias);
                    System.exit(0);
                }else{
                    aliases.add(alias);
                }
            }
        });
    }
    
    /**
     * @param <T> Instance of command
     * @param clazz the class of the command to get
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends Command> T getCommand(Class<? extends Command> clazz){
        return (T) this.commands.stream().filter((i) -> i.getClass().equals(clazz)).findFirst().orElse(null);
    }
    
    /**
     * @param name the name of the command to get
     * @return the command, or null if not found
     */
    public Optional<Command> getCommand(String name){
        for(Command command : this.getCommands()){
            for(String alias : command.getMetaInformation().alias()){
                if(alias.equalsIgnoreCase(name)){
                    return Optional.of(command);
                }
            }
        }
        return Optional.empty();
    }

    /**
     * @return the commands
     */
    public Set<Command> getCommands() {
        return commands;
    }

    /**
     * @param category the category to get commands from
     * @return the commands in the category
     */
    public List<Command> getCommands(Category category){
        return this.commands.stream().filter(o -> o.getMetaInformation().category().equals(category)).collect(Collectors.toList());
    }

    /**
     * This method is called when a slash command is used
     * @param event the slash command event
     */
    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {

        Castoffs.getInstance().tick();

        try{
            net.dv8tion.jda.api.entities.Member member = event.getMember();

            //ensure the user is a valid human
            if(member == null) return;
            if(member.getUser().isBot()) return;
            if(member.getUser().isSystem()) return;

            String message = event.getMessage().getContentRaw();

            //check if the message is a command
            if(!message.startsWith(Settings.PREFIX)) return;

            if(event.getGuild().getId().equals("339615489246494722") && Settings.DEBUG){
                EmbedBuilder error = EmbedMaker.ERROR(event.getAuthor(), "Sorry", "syntex daddy is working on the bot, please wait.");
                event.getMessage().replyEmbeds(error.build()).queue();
                return;
            }

            //get the command name
            String commandName = message.split(" ")[0].replace(Settings.PREFIX, "");

            //check if command exists
            Optional<Command> commandOptional = this.getCommand(commandName);

            if(commandOptional.isEmpty()){
                // EmbedBuilder error = EmbedMaker.ERROR(event.getAuthor(), "Command not found", "The command `" + commandName + "` was not found.");
                // event.getMessage().replyEmbeds(error.build()).queue();

                //send a gif instead
                String url = "https://giphy.com/search/anime-" + commandName.replace(" ", "-");

                String content = HtmlUtils.getHtml(url);

                String[] split = content.split("\"url\": \"");

                List<String> entries = new ArrayList<>();

                for(String i : split){
                    String embed = i.split("\"")[0];
                    if(embed.contains(".gif")){
                        entries.add(embed);
                    }
                }

                String raw = event.getMessage().getContentDisplay();
                String rawWithoutCommand = raw.replace(Settings.PREFIX, "").replace(commandName, "").trim();

                Collections.shuffle(entries);

                EmbedBuilder builder = new EmbedBuilder();
                builder.setImage(entries.get(0));
                
                if(rawWithoutCommand.length() > 0){
                    builder.setTitle(rawWithoutCommand);
                }

                builder.setColor(Color.pink);

                System.out.println(entries.get(0));

                event.getMessage().replyEmbeds(builder.build()).queue();

                return;
            }

            //get the command in question
            Command command = commandOptional.get();

            //check if the user has permissions to run the command
            List<Permission> permission = command.getDefaultMemberPermissions();

            if(!member.hasPermission(permission)){
                EmbedBuilder error = EmbedMaker.ERROR(event.getAuthor(), "Invalid permissions", "You are missing one or more of the following permissions: " + permission.toString());
                event.getMessage().replyEmbeds(error.build()).queue();
                return;
            }

            //check if the command is on cooldown
            CommandCooldown cooldown = command.getCooldown();
            String uuid = event.getAuthor().getId();

            //check if the user is on cooldown, if not, run the command
            if(!cooldown.isOnCooldown(uuid, event.getGuild())){

                CommandRecivedEvent commandRecivedEvent = new CommandRecivedEvent(
                                                                command,
                                                                event.getJDA(), 
                                                                event.getResponseNumber(),
                                                                event.getMessage());

                command.onCommandRecieved(commandRecivedEvent);
                cooldown.applyCooldown(uuid);
            }else{
                EmbedBuilder builder = EmbedMaker.ERROR_BASIC(event.getAuthor());
                builder.setTitle("You're on cooldown!");
                builder.appendDescription("You can't do that command yet, please wait.");
                builder.addField("Time remaining", "```" + cooldown.getRemainingCooldownFormatted(uuid) + "```", true);
                event.getMessage().replyEmbeds(builder.build()).queue();
            }

        }catch(Exception e){

            EmbedBuilder error = EmbedMaker.ERROR(event.getAuthor(), "Falure in " + this.getClass().getSimpleName(), e.getLocalizedMessage());
            event.getMessage().replyEmbeds(error.build()).queue();

            if(e instanceof IllegalArgumentException) return;

            e.printStackTrace();
        }
    }
}
    

