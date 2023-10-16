package com.castoffs.commands.help;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.json.JSONArray;
import org.json.JSONObject;

import com.castoffs.bot.Settings;
import com.castoffs.commands.Category;
import com.castoffs.commands.Command;
import com.castoffs.commands.CommandMeta;
import com.castoffs.commands.CommandRecivedEvent;
import com.castoffs.handler.Dependencies;
import com.castoffs.utils.HtmlUtils;
import com.castoffs.utils.TimeUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;

@CommandMeta(alias = {"help"},
             description = "provides documentation for this bot and it's commands.",
             category = Category.INFO, usage = {"help", "help {command:text (optional)}"},
             examples = {"help - shows the help menu", "help tod - shows the help menu for the tod command"})
public class Help extends Command{

    public Help(Dependencies dependencies) {
        super(dependencies);
    }

    public void getCommandInfo(CommandRecivedEvent event) {

        String command = event.getArgs()[1];

        //search for the command
        Optional<Command> cmdOptional = this.dependencies.getCommandHandler().getCommand(command);

        //

        if(!cmdOptional.isPresent()){
            throw new IllegalArgumentException("'" + command + "' is not a recognized command");
        }

        Command cmd = cmdOptional.get();

        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle(cmd.getMetaInformation().category().getEmoji() + " " + cmd.getMetaInformation().alias()[0]);
        embed.setColor(Color.pink);

        String description = cmd.getMetaInformation().description();

        StringBuilder usage = new StringBuilder();

        usage.append("`");
        for(String i : cmd.getMetaInformation().usage()){
            usage.append(Settings.PREFIX + i + "\n");
        }
        usage.append("`");

        StringBuilder permissions = new StringBuilder();

        for(Permission i : cmd.getDefaultMemberPermissions()){
            permissions.append("`" + i.getName() + "`, ");
        }

        StringBuilder examples = new StringBuilder();

        examples.append("`");
        for(String i : cmd.getMetaInformation().examples()){
            examples.append(Settings.PREFIX + i + "\n");
        }
        examples.append("`");

        String usageString = usage.toString();
        String examplesString = examples.toString();
        String permissionsString = permissions.toString().substring(0, permissions.length() - 2);

        embed.setDescription(description);

        if(description == null || usageString == null || permissionsString == null || examplesString == null){
            throw new IllegalArgumentException("an internal error has accured at " + this.getClass().getName() + ", please contact the developer");
        }

        embed.addField("Permissions", permissionsString, true);
        embed.addField("Cooldown", "`" + TimeUtils.formatDuration(this.getMetaInformation().cooldown() / 1000) + "`", true);
        embed.addField("Examples", examplesString, false);

        embed.addField("Usage", usageString, false);


        event.getMessage().replyEmbeds(embed.build()).queue();
    }

    @Override
    public void onCommandRecieved(CommandRecivedEvent event) {

        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Commands");
        embed.setDescription("Quick tip. Here are a list of commands! In order to view detailed information about a command please type " + Settings.PREFIX + this.getMetaInformation().alias()[0] + " <command>");
        embed.setColor(Color.pink);

        if(event.isArgumentValidAtIndex(    1)){
            this.getCommandInfo(event);
            return;
        }

        for(Category category : Category.values()){
            List<Command> commands = dependencies.getCommandHandler().getCommands(category);

            if(commands.isEmpty()) continue;

            StringBuilder builder = new StringBuilder();
            for(Command command : commands){
                builder.append("`;" + command.getMetaInformation().alias()[0] + "`, ");
            }

            String name = category.name().toUpperCase();
            String content = builder.substring(0, builder.length() - 2);

            if(name == null || content == null) return;

            embed.addField(category.getEmoji() + " " + name, content, false);
        }

        //add reaction commands
        this.appendReactionCommands(embed);

        embed.setFooter("type " + Settings.PREFIX + this.getMetaInformation().alias()[0] + " <command> for more info on a command");    
       
        event.getMessage().replyEmbeds(embed.build()).queue();
    }

    public void appendReactionCommands(EmbedBuilder embed){

        JSONObject reactionCommands = new JSONObject(HtmlUtils.getHtml("https://api.otakugifs.xyz/gif/allreactions"));
        JSONArray reactions = reactionCommands.getJSONArray("reactions");

        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < reactions.length(); i++){
            String reaction = reactions.getString(i);
            if(reaction.contains("kiss")) continue;
            builder.append("`;" + reaction + "`, ");
        }

        String content = builder.substring(0, builder.length() - 2);

        if(content == null) return;

        embed.addField(Category.REACTIONS + " " + "reactions", content, false);

    }

    @Override
    public @Nonnull List<Permission> getDefaultMemberPermissions() {
        return this.defaultCommandPermissions();
    }
    
}
