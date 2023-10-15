package com.castoffs.bot;

import net.dv8tion.jda.api.entities.User;

/**
 * This enum is used to store the id's of known members in the castsoffs server.
 * helper class used to quickly set custom instructions for known members.
 */
public enum UserReference {
    
    KOOKIE("873980184712331295"),
    SYNTEX("234004050201280512"),
    MAPLE("760189502063902750");

    private final String id;

    private UserReference(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }

    public static boolean hasUser(UserReference user, User... users){
        for(User u : users){
            if(u.getId().equals(user.getId())){
                return true;
            }
        }
        return false;
    }

}
