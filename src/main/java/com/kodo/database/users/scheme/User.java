package com.kodo.database.users.scheme;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

public class User {
    private String username;
    private String name;
    private int honor;
    private String clan;
    @SerializedName("leaderboardPosition")
    private int leaderboardPosition;
    private List<String> skills;
    private Ranks ranks;
    @SerializedName("codeChallenges")
    private CodeChallenges codeChallenges;

    // Default constructor
    public User() {
        this.username = "";
        this.name = "";
        this.honor = 0;
        this.clan = "";
        this.leaderboardPosition = 0;
        this.skills = Collections.emptyList();
        this.ranks = new Ranks();
        this.codeChallenges = new CodeChallenges();
    }

    // Getters and setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHonor() {
        return honor;
    }

    public void setHonor(int honor) {
        this.honor = honor;
    }

    public String getClan() {
        return clan;
    }

    public void setClan(String clan) {
        this.clan = clan;
    }

    public int getLeaderboardPosition() {
        return leaderboardPosition;
    }

    public void setLeaderboardPosition(int leaderboardPosition) {
        this.leaderboardPosition = leaderboardPosition;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public Ranks getRanks() {
        return ranks;
    }

    public void setRanks(Ranks ranks) {
        this.ranks = ranks;
    }

    public CodeChallenges getCodeChallenges() {
        return codeChallenges;
    }

    public void setCodeChallenges(CodeChallenges codeChallenges) {
        this.codeChallenges = codeChallenges;
    }
}

class Ranks {
    private Rank overall;
    private Map<String, Rank> languages;

    public Ranks() {
        this.overall = new Rank();
        this.languages = Collections.emptyMap();
    }

    // Getters and setters
    public Rank getOverall() {
        return overall;
    }

    public void setOverall(Rank overall) {
        this.overall = overall;
    }

    public Map<String, Rank> getLanguages() {
        return languages;
    }

    public void setLanguages(Map<String, Rank> languages) {
        this.languages = languages;
    }
}

class Rank {
    private int rank;
    private String name;
    private String color;
    private int score;

    public Rank() {
        this.rank = 0;
        this.name = "";
        this.color = "";
        this.score = 0;
    }

    // Getters and setters
    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

class CodeChallenges {
    @SerializedName("totalAuthored")
    private int totalAuthored;
    @SerializedName("totalCompleted")
    private int totalCompleted;

    public CodeChallenges() {
        this.totalAuthored = 0;
        this.totalCompleted = 0;
    }

    // Getters and setters
    public int getTotalAuthored() {
        return totalAuthored;
    }

    public void setTotalAuthored(int totalAuthored) {
        this.totalAuthored = totalAuthored;
    }

    public int getTotalCompleted() {
        return totalCompleted;
    }

    public void setTotalCompleted(int totalCompleted) {
        this.totalCompleted = totalCompleted;
    }
}
