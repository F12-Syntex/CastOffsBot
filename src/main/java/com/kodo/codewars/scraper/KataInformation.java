package com.kodo.codewars.scraper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class KataInformation {

    private Set<String> cachedKata;
    private HashMap<String, CodewarsKata> katas;

    public KataInformation() {
        this.cachedKata = new HashSet<>();
        this.katas = new HashMap<>();

        this.cachedKata.add("64fbfe2618692c2018ebbddb");
    }
    
    public Set<String> getCachedKata() {
        return cachedKata;
    }

    public void setCachedKata(Set<String> cachedKata) {
        this.cachedKata = cachedKata;
    }

    public HashMap<String, CodewarsKata> getKatas() {
        return katas;
    }

    public void setKatas(HashMap<String, CodewarsKata> katas) {
        this.katas = katas;
    }

    public void addKata(String id, CodewarsKata kata){
        this.katas.put(id, kata);
    }

    public boolean containsKata(String id){
        return this.katas.containsKey(id);
    }
}
