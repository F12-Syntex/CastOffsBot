package com.kodo.commands.admin;

import com.kodo.commands.CommandMeta;
import com.kodo.embeds.EmbedMaker;
import com.kodo.embeds.PagedEmbed;
import com.kodo.handler.Dependencies;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

@CommandMeta(description = "Returns the developer command", name = "dev")
public class Developer extends AdminCommand{

    public Developer(Dependencies dependencies) {
        super(dependencies);
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        PagedEmbed pagedEmbed = new PagedEmbed();

        pagedEmbed.appendPages(
            EmbedMaker.INFO(event.getUser(), "PAGE 1"),
            EmbedMaker.INFO(event.getUser(), "PAGE 2"),
            EmbedMaker.INFO(event.getUser(), "PAGE 3")
        );

        pagedEmbed.send(event);

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
    
}
