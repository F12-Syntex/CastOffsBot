package com.kodo.codewars.scraper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class KataInformation {

    private Set<String> cachedKata;
    private HashMap<String, CodewarsKata> katas;

    private final int SHORT_LENGTH = 1000;

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

    public CodewarsKata getRandomChallenge(int difficulty, boolean shortDescription){

        if(shortDescription){
            return this.getRandomShortChallenge(difficulty);
        }

        if(difficulty < 0){
            return this.getRandomChallenge();
        }
        List<CodewarsKata> challenges = this.getChallenges().stream().filter(o -> difficulty == o.getRank().getDifficulty()).collect(Collectors.toList());
        int randomIndex = ThreadLocalRandom.current().nextInt(challenges.size());
        return challenges.get(randomIndex);
    }

    public CodewarsKata getRandomChallenge(int difficulty){
        if(difficulty < 0){
            return this.getRandomChallenge();
        }
        List<CodewarsKata> challenges = this.getChallenges().stream().filter(o -> difficulty == o.getRank().getDifficulty()).collect(Collectors.toList());
        int randomIndex = ThreadLocalRandom.current().nextInt(challenges.size());
        return challenges.get(randomIndex);
    }

    public CodewarsKata getRandomShortChallenge(int difficulty){
        if(difficulty < 0){
            return this.getRandomShortChallenge();
        }
        List<CodewarsKata> challenges = this.getChallenges().stream().filter(o -> difficulty == o.getRank().getDifficulty() && o.getDescription().length() < SHORT_LENGTH).collect(Collectors.toList());
        int randomIndex = ThreadLocalRandom.current().nextInt(challenges.size());
        return challenges.get(randomIndex);
    }
    
    public CodewarsKata getRandomShortChallenge(){
        List<CodewarsKata> challenges = this.getChallenges().stream().filter(o -> o.getDescription().length() < SHORT_LENGTH).collect(Collectors.toList());
        int randomIndex = ThreadLocalRandom.current().nextInt(challenges.size());
        return challenges.get(randomIndex);
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
