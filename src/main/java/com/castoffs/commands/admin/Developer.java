package com.castoffs.commands.admin;

import java.util.Optional;

import com.castoffs.bot.Castoffs;
import com.castoffs.commands.Category;
import com.castoffs.commands.CommandMeta;
import com.castoffs.handler.Dependencies;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

/**
 * developer command used for testing purposes
 */
@CommandMeta(description = "Returns the developer command", name = "dev", category = Category.ADMIN)
public class Developer extends AdminCommand{

    public Developer(Dependencies dependencies) {
        super(dependencies);
    }

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        Optional<Guild> guild = Castoffs.getInstance().getDiscord().getGuilds()
                .stream()
                .filter(o -> o.getId().equals("339615489246494722")).findFirst();

        if(!guild.isPresent()){
            System.out.println("Could not find guild");
            return;
        }

        Guild castoffs = guild.get();

        //print the bots perms for this server
        castoffs.getSelfMember().getPermissions().forEach(perm -> {
            System.out.println(perm.getName());
        });
    
    }

    


    @Override
    public SlashCommandData getSlashCommandData() {
        SlashCommandData options = this.getBareBonesSlashCommandData();
        return options;
    }
    
    @Override
    public DefaultMemberPermissions getDefaultMemberPermissions() {
       return DefaultMemberPermissions.ENABLED;
    }

    @Override
    public void onButtonPressed(ButtonInteractionEvent event) {
        return;
    }
    
}
