package com.castoffs.database.ship;

public class ShipEntry {

    private String user1;
    private String user2;
    private int score;

    public ShipEntry(String user1, String user2, int score) {
        this.user1 = user1;
        this.user2 = user2;
        this.score = score;
    }

    public String getUser1() {
        return this.user1;
    }

    public String getUser2() {
        return this.user2;
    }

    public int getScore() {
        return this.score;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
}
