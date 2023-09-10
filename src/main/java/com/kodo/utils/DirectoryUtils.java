package com.kodo.utils;

import java.io.File;

public class DirectoryUtils {
    public static File directoryBuilder(File root, String... dir){
        StringBuilder builder = new StringBuilder();
        for(String s : dir){
            builder.append(s + File.separator);
        }
        return new File(root, builder.toString());
    }
}
