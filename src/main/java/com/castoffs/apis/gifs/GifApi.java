package com.castoffs.apis.gifs;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import com.castoffs.utils.HtmlUtils;

public class GifApi {

    public static List<String> query(String query){
        try{
            String modifier = "anime"; 
            String url = "https://tenor.com/search/" + modifier + "-" + query.replace(" ", "-");

            String content = HtmlUtils.getHtml(url);
            String[] urls = content.split("http");

            List<String> gifs = new ArrayList<>();

            for(String i : urls){
                String rawUrl = URLDecoder.decode("http" + i.split("[\"| ]")[0], "UTF-8");
                if(rawUrl.endsWith(".gif") && 
                  !rawUrl.contains("\\") &&
                  rawUrl.contains(query.replace(" ", "-"))){
                        System.out.println(rawUrl);
                        gifs.add(rawUrl);
                }
            }

            return gifs;
        }catch(Exception e){
            e.printStackTrace();
            throw new IllegalArgumentException();
        }   
    }
    
}
