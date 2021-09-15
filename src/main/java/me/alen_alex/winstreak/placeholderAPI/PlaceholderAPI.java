package me.alen_alex.winstreak.placeholderAPI;

import me.alen_alex.winstreak.Winstreak;
import me.alen_alex.winstreak.configuration.Configuration;
import me.alen_alex.winstreak.leaderboard.LeaderboardManager;
import me.alen_alex.winstreak.utility.TimeUtility;
import me.alen_alex.winstreak.winstreak.WinstreakManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class PlaceholderAPI extends PlaceholderExpansion {

    private Winstreak plugin;

    public PlaceholderAPI(Winstreak plugin){
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "bw1058ws";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Alen_Alex";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public boolean persist(){
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String identifier){

        if(identifier.startsWith("topname")){
            String[] position = identifier.split("_");
            int _pos = Integer.parseInt(position[1]);
            if(position == null && _pos > 10 && _pos < 1)
                return "&c&lUnknown Position";
            return LeaderboardManager.getLeaderboardTopName(_pos);
        }

        if(identifier.startsWith("topstreak")){
            String[] position = identifier.split("_");
            int _pos = Integer.parseInt(position[1]);
            if(position == null && _pos > 10 && _pos < 1)
                return "&c&lUnknown Position";
            return LeaderboardManager.getLeaderboardTopStreak(_pos);
        }

        if(identifier.equals("current")){
            UUID uuid = player.getUniqueId();
            if(WinstreakManager.isDataLoaded(uuid))
                return String.valueOf(WinstreakManager.getCurrentStreak(uuid));
            else{
                if(WinstreakManager.isPlayerDataCached(uuid))
                    return String.valueOf(WinstreakManager.getCurrentStreakFromCache(uuid));
                else {
                    if (WinstreakManager.requestToCache(uuid))
                        return String.valueOf(WinstreakManager.getCurrentStreakFromCache(uuid));
                    else
                        return "&c&lN/A";
                }
            }
        }

        if(identifier.equals("best")){
            UUID uuid = player.getUniqueId();
            if(WinstreakManager.isDataLoaded(uuid))
                return String.valueOf(WinstreakManager.getBestStreak(uuid));
            else{
                if(WinstreakManager.isPlayerDataCached(uuid))
                    return String.valueOf(WinstreakManager.getCurrentStreakFromCache(uuid));
                else {
                    if (WinstreakManager.requestToCache(uuid))
                        return String.valueOf(WinstreakManager.getBestStreakFromCache(uuid));
                    else
                        return "&c&lN/A";
                }
            }
        }

        if(identifier.equals("refreshtimer")){
            return TimeUtility.getFormatedTime((Configuration.getUpdateLeaderboard()*60)- plugin.getTimer());
        }


        return null;
    }

}
