package com.castoffs.utils;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

/**
 * The `UserUtils` class is a utility class providing methods to check user-related permissions
 * and roles within a Discord server (guild).
 */
public class UserUtils {

    /**
     * Check if a user is an administrator in a specific guild.
     *
     * @param guild   The guild to check within.
     * @param userId  The user ID to check for administrator status.
     * @return `true` if the user is an administrator, `false` otherwise.
     */
    public static boolean isAdmin(Guild guild, String userId) {
        if (userId == null) return false;
        Member member = guild.getMemberById(userId);
        return member != null && (member.hasPermission(Permission.ADMINISTRATOR) || UserUtils.isDeveloper(userId));
    }

    /**
     * Check if a user is a developer based on their user ID.
     *
     * @param userId The user ID to check for developer status.
     * @return `true` if the user is a developer, `false` otherwise.
     */
    public static boolean isDeveloper(String userId) {
        return userId != null && (
                userId.equals("234004050201280512") //syntex
        );
    }
}
