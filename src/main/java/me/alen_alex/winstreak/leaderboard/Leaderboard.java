package me.alen_alex.winstreak.leaderboard;

public class Leaderboard {

    private String name;
    private int highest;

    public Leaderboard(String name, int highest) {
        this.name = name;
        this.highest = highest;
    }

    public String getName() {
        return name;
    }

    public int getHighest() {
        return highest;
    }

}
