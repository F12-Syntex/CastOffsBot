package com.castoffs.commands;

import java.util.HashMap;
import java.util.Map;

import com.castoffs.utils.TimeUtils;
import com.castoffs.utils.UserUtils;

import net.dv8tion.jda.api.entities.Guild;

/**
 * This class is used to manage the cooldowns for a command
 */
public class CommandCooldown {

    /**
     * Key: string, the id of the user
     * Value: long, the time the cooldown ends
     */
    private Map<String, Long> cooldowns;
    private final long duration;

    public CommandCooldown(long cooldown) {
        this.cooldowns = new HashMap<>();
        this.duration = cooldown;
    }

    public CommandCooldown() {
        this.cooldowns = new HashMap<>();
        this.duration = 0L;
    }

    /**
     * Checks if a user is currently on cooldown.
     *
     * @param userId The ID of the user to check
     * @return true if the user is on cooldown, false otherwise
     */
    public boolean isOnCooldown(String userId, Guild guild) {
        if(!this.cooldowns.containsKey(userId) || UserUtils.isAdmin(guild, userId)) return false;
        
        if(this.getRemainingCooldown(userId) <= 0){
            this.removeCooldown(userId);
            return false;
        }

        return true;
    }

    /**
     * Adds a cooldown for a user.
     *
     * @param userId   The ID of the user
     */
    public void applyCooldown(String userId) {
        this.cooldowns.put(userId, System.currentTimeMillis() + this.duration);
    }

    /**
     * Removes the cooldown for a user.
     *
     * @param userId The ID of the user
     */
    public void removeCooldown(String userId) {
        this.cooldowns.remove(userId);
    }

    /**
     * Gets the end time of the cooldown for a user.
     *
     * @param userId The ID of the user
     * @return The end time of the cooldown in milliseconds
     */
    public long getCooldown(String userId) {
        return this.cooldowns.get(userId);
    }

    /**
     * Gets the remaining time until the cooldown ends for a user.
     *
     * @param userId The ID of the user
     * @return The remaining time in milliseconds
     */
    public long getRemainingCooldown(String userId) {
        return this.getCooldown(userId) - System.currentTimeMillis();
    }

    /**
     * Gets the formatted remaining time until the cooldown ends for a user.
     *
     * @param userId The ID of the user
     * @return The formatted remaining time (HH:MM:SS)
     */
    public String getRemainingCooldownFormatted(String userId) {
        long remaining = this.getRemainingCooldown(userId);
        return TimeUtils.formatDuration(remaining/1000);
    }
}
