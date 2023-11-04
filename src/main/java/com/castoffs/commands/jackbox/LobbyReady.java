package com.castoffs.commands.jackbox;

@FunctionalInterface
public interface LobbyReady {
    public abstract void onLobbyReady(Lobby lobby);
}
