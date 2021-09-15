package me.alen_alex.winstreak.winstreak;

import java.util.UUID;

public class Winstreak {

    private String Name;
    private UUID UUID;
    private int CurrentStreak;
    private int BestSteak;

    public Winstreak(String name, UUID UUID, int currentStreak, int bestSteak) {
        this.Name = name;
        this.UUID = UUID;
        this.CurrentStreak = currentStreak;
        this.BestSteak = bestSteak;
    }

    public String getName() {
        return Name;
    }

    public UUID getUUID() {
        return UUID;
    }

    public int getCurrentStreak() {
        return CurrentStreak;
    }

    public void setCurrentStreak(int currentStreak) {
        CurrentStreak = currentStreak;
    }

    public int getBestSteak() {
        return BestSteak;
    }

    public void setBestSteak(int bestSteak) {
        BestSteak = bestSteak;
    }
}
