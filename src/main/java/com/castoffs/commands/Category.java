package com.castoffs.commands;

import javax.annotation.Nonnull;

public enum Category {
    ADMIN("🔒"),
    FUN("🎉"),
    INFO(":pencil:"),
    MODERATION("🔧"),
    REACTIONS("🌸"),
    JACKBOX("🎮");

    private @Nonnull String emoji;

    Category(@Nonnull String emoji) {
        this.emoji = emoji;
    }

    public @Nonnull String getEmoji() {
        return emoji;
    }
}
