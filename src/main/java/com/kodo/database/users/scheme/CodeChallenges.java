package com.kodo.database.users.scheme;

import com.google.gson.annotations.SerializedName;

public class CodeChallenges {
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
