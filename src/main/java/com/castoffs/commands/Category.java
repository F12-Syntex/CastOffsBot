package com.castoffs.commands;

public enum Category {
    ADMIN("🔒"),
    FUN("🎉"),
    INFO("ℹ️");

    private String emoji;

    Category(String emoji) {
        this.emoji = emoji;
    }

    public String getEmoji() {
        return emoji;
    }
}
