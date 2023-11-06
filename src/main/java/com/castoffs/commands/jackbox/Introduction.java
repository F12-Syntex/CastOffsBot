package com.castoffs.commands.jackbox;

import java.awt.Color;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;

import com.castoffs.bot.Castoffs;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;
import net.dv8tion.jda.api.requests.restaction.MessageEditAction;

public class Introduction extends ListenerAdapter{

    public Lobby lobby;
    private Set<Long> readyPlayers;

    private LobbyReady onReady;

    public Introduction(Lobby lobby){
        this.lobby = lobby;
        this.readyPlayers = new HashSet<>();

        Castoffs.getInstance().getDiscord().addEventListener(this);
    }

    public void start(){
        this.render();
    }

    public void setOnReady(LobbyReady onReady){
        this.onReady = onReady;
    }

    public void render(){

        if(this.readyPlayers.size() == this.lobby.getPlayers().size()){
            this.onReady.onLobbyReady(lobby);
            return;
        }

        Message game = lobby.getLobbyMessage();

        EmbedBuilder builder = new EmbedBuilder();
        builder.setDescription("#\n" + lobby.getJackBox().getGameName() + " - How to play\n" + 
                                " - Once everyone has joined, the game will present a series of prompts or questions.\r\n" + //
                                " - Each prompt will have two blanks that players need to fill in with creative and funny answers.\r\n" + //
                                " - Players should try to come up with responses that they think will make others laugh or vote for their answer.\r\n" + //
                                " - Once all players have submitted their answers, the game will randomly pair up the responses and present them to the group.\r\n" + //
                                " - Players then vote on which response they think is the funniest or most clever.\r\n" + //
                                " - After voting, the game reveals the winning response, and players earn points based on the number of votes their answer received.\r\n" + //
                                " - The process repeats with new prompts until the game ends.\r\n" + //
                                " - The player with the most points at the end of the game is declared the winner.");

        builder.setColor(Color.pink);
        builder.setFooter("gamecode: " + this.lobby.getGameCode() + " | players: " + this.lobby.getPlayers().size() + "/" + this.lobby.getMaxPlayers() + " players ready: " + this.readyPlayers.size(), null);
        builder.setTimestamp(Instant.now());

        MessageEditAction action = game.editMessageEmbeds(builder.build());
        
        Button[] buttons = {
            Button.of(ButtonStyle.SECONDARY, this.lobby.getGameCode()+":introskip", "Ready")
        };

        action.setActionRow(buttons);
        action.setFiles();

        action.queue();
    }

    @Override
    public void onButtonInteraction(@Nonnull ButtonInteractionEvent event){
        String componentId = event.getComponentId();

        if(!componentId.contains(":")){
            return;
        }

        String[] id = componentId.split(":");
        String gameCode = id[0];
        String action = id[1];

        if(!gameCode.equals(this.lobby.getGameCode())){
            return;
        }

        if(action.equals("introskip")){
            
            System.out.println("Ready button clicked");
            User user = event.getUser();

            if(this.readyPlayers.contains(user.getIdLong())){
                event.deferEdit().queue();
                return;
            }

            this.readyPlayers.add(user.getIdLong());
            event.deferEdit().queue();
            this.render();
        }
    }
    
}
