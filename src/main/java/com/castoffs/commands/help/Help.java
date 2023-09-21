package com.castoffs.commands.help;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.castoffs.commands.Category;
import com.castoffs.commands.Command;
import com.castoffs.commands.CommandMeta;
import com.castoffs.handler.Dependencies;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;

@CommandMeta(name = "help", description = "returns all the commands and their information", category = Category.INFO)
public class Help extends Command{

    public Help(Dependencies dependencies) {
        super(dependencies);
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        EmbedBuilder help = new EmbedBuilder();
        help.setTitle("Help menu");
        help.setColor(Color.green);
        help.setDescription("A list of every command available.");

        for(Category category : Category.values()){
            StringBuilder commands = new StringBuilder();

            for(Command command : dependencies.getCommandHandler().getCommands()){
                if(command.getMetaInformation().category() == category){
                    commands.append("/" + command.getMetaInformation().name() + ": " + command.getMetaInformation().description() + "\n");
                }
            }

            if(commands.isEmpty()) continue;

            help.addField(category.getEmoji() + " " + category.name(), "```" + commands + "```", false);
        }

        event.replyEmbeds(help.build()).queue();

    }

    @Override
    public void onButtonPressed(ButtonInteractionEvent event) {
        return;
    }

    @Override
    public SlashCommandData getSlashCommandData() {
        return this.getBareBonesSlashCommandData();
    }

    @Override
    public DefaultMemberPermissions getDefaultMemberPermissions() {
        return DefaultMemberPermissions.ENABLED;
    }
    
}
