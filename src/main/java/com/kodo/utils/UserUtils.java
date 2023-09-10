package com.kodo.utils;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

public class UserUtils {

    /**
     * @param guild the guild to check
     * @param userId the user id to check
     * @return true if the user is an administrator, false otherwise
     */
    public static boolean isAdmin(Guild guild, String userId) {
        if(userId == null) return false;
        Member member = guild.getMemberById(userId);
        return member != null && (member.hasPermission(Permission.ADMINISTRATOR) || UserUtils.isDeveloper(userId));
    }

    public static boolean isDeveloper(String userId){
        return userId != null && (
            userId.equals("234004050201280512") //syntex
        );
    }

}
