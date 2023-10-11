package com.castoffs.utils;

public class StringUtils {

    public static String beutifyString(String str){
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
    
}
