package com.kodo.database.users.scheme;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Challenges {

    private List<Challenge> challenges;
    private Set<Integer> cachedPages;

    public Challenges() {
        this.challenges = new ArrayList<>();
        this.cachedPages = new HashSet<>();
    }

    public void addCachedPage(int page) {
        this.cachedPages.add(page);
    }

    public boolean isPageCached(int page) {
        return this.cachedPages.contains(page);
    }

    public Challenges(List<Challenge> challenges) {
        this.challenges = challenges;
        this.cachedPages = new HashSet<>();
    }

    public List<Challenge> getChallenges() {
        return challenges;
    }

    public void addChallenge(Challenge challenge, int page) {
        this.challenges.add(challenge);
        challenge.setPage(page);
    }

    public void removeChallenge(Challenge challenge) {
        this.challenges.remove(challenge);
    }

    public void setChallenges(List<Challenge> challenges) {
        this.challenges = challenges;
    }

    public boolean containsChallenge(Challenge challenge) {
        return this.challenges.contains(challenge);
    }

    public boolean containsChallenge(String id) {
        return this.challenges.stream().anyMatch(challenge -> challenge.getId().equals(id));
    }

    public Challenge getChallenge(String id) {
        return this.challenges.stream().filter(challenge -> challenge.getId().equals(id)).findFirst().orElse(null);
    }

    public void removeChallenge(String id) {
        this.challenges.removeIf(challenge -> challenge.getId().equals(id));
    }
    
}
