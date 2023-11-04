package com.castoffs.utils;

import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;

public class ImageUtils {

    @SuppressWarnings("deprecated")
    public static Image toImage(String url){
        ImageIcon icon;
        try {
            icon = new ImageIcon(new URL(url));
            return icon.getImage();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
