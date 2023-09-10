package com.kodo.codewars.scraper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class KataInformation {

    private Set<String> cachedKata;
    private HashMap<String, CodewarsKata> katas;

    public KataInformation() {
        this.cachedKata = new HashSet<>();
        this.katas = new HashMap<>();

        this.cachedKata.add("64fbfe2618692c2018ebbddb");
    }

    public List<CodewarsKata> getChallenges() {
        return new ArrayList<>(this.katas.values());
    }

    public CodewarsKata getRandomChallenge(){
        int randomIndex = ThreadLocalRandom.current().nextInt(this.katas.size());
        return this.getChallenges().get(randomIndex);
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
