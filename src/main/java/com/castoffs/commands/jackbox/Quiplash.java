package com.castoffs.commands.jackbox;

import com.castoffs.commands.Category;
import com.castoffs.commands.CommandMeta;
import com.castoffs.commands.CommandRecivedEvent;
import com.castoffs.handler.Dependencies;

@CommandMeta(alias = {"quiplash"},
             description = "JackBox minigame - Quiplash",
             category = Category.JACKBOX,
             usage = {"quiplash <game code:text required> <start:text optional>",
                      "quiplash <game code:text required> <stop:text optional>"},
             cooldown = 60 * 1000,
             examples = {"quiplash"})
public class Quiplash extends JackBoxCommands{

    public Quiplash(Dependencies dependencies) {
        super(dependencies, "Quiplash");
    }

    @Override
    public void onCommandRecieved(CommandRecivedEvent event) {
        Lobby queuedLobby = new Lobby(this, event.getGuildChannel().asTextChannel(), event.getAuthor(), 8, 1);
        queuedLobby.queue();

        queuedLobby.onLobbyReady(this::startGame);
    }

    public void startGame(Lobby lobby){

    }

    
    
}
