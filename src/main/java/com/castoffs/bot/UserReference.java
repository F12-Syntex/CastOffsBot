package com.castoffs.bot;

import java.util.Optional;

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

    public static boolean isUser(UserReference reference, User user){
        return user.getId().equals(reference.getId());
    }

    public static boolean isUser(UserReference reference, String id){
        return id.equals(reference.getId());
    }

    public Optional<UserReference> getUserReference(User user){
        for(UserReference reference : UserReference.values()){
            if(reference.getId().equals(user.getId())){
                return Optional.of(reference);
            }
        }
        return Optional.empty();
    }

}
