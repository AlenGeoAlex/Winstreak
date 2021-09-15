package me.alen_alex.winstreak.winstreak;

import me.alen_alex.winstreak.MySQL.DataManager;
import me.alen_alex.winstreak.configuration.Configuration;
import me.alen_alex.winstreak.utility.DebugLevel;
import me.alen_alex.winstreak.utility.Message;
import org.bukkit.Bukkit;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class WinstreakManager {

    private static HashMap<UUID, Winstreak> PlayerWinstreak = new HashMap<UUID,Winstreak>();
    private static HashMap<UUID, Winstreak> cachedWinstreak = new HashMap<>();

    public static boolean isDataLoaded(UUID _UUID){
        return PlayerWinstreak.containsKey(_UUID);
    }

    public static void loadData(UUID _UUID) throws SQLException {
        CachedRowSet playerData = DataManager.getPlayerData(_UUID);
        PlayerWinstreak.put(_UUID, new Winstreak(playerData.getString("name"), _UUID, playerData.getInt("current"), playerData.getInt("highest")));
        playerData.close();
    }

    public static void unloadData(UUID _UUID) {
        if(isDataLoaded(_UUID)){
            Winstreak streak = PlayerWinstreak.get(_UUID);
            DataManager.updatePlayerDataToDatabase(_UUID, streak.getCurrentStreak(), streak.getBestSteak());
            PlayerWinstreak.remove(_UUID);
        }
    }



    public static void calculateStreak(UUID _UUID){
        if(_UUID == null)
            return;
        if(!isDataLoaded(_UUID)){
            Message.setDebugMessage("WinstreakManager.calculateStreak Failed to load player data for "+(_UUID)+" while trying to add streak", DebugLevel.ERROR);
            return;
        }
        Winstreak streak = PlayerWinstreak.get(_UUID);
        streak.setCurrentStreak(streak.getCurrentStreak()+1);
        if(streak.getCurrentStreak()> streak.getBestSteak()){
            Message.setDebugMessage("WinstreakManager.calculateStreak Dectected a change in high streak for "+Bukkit.getPlayer(_UUID).getName()+" , starting calculation",DebugLevel.DEFAULT);
            streak.setBestSteak(streak.getCurrentStreak());
            if(Configuration.doSaveAfterEachGame())
                DataManager.setHighest(_UUID,streak.getBestSteak());
        }
        if(Configuration.doSaveAfterEachGame()){
            Message.setDebugMessage("WinstreakManager.calculateStreak Streak is saving to database for"+Bukkit.getPlayer(_UUID).getName(),DebugLevel.RUNNING);
            DataManager.addStreakToDatabase(_UUID);
            Message.setDebugMessage("WinstreakManager.calculateStreak Streak saved to database for "+Bukkit.getPlayer(_UUID).getName(),DebugLevel.SUCCESS);
        }
        Message.setDebugMessage("WinstreakManager.calculateStreak Finished.",DebugLevel.SUCCESS);
    }

    public static void resetStreak(UUID _UUID){
        if(!isDataLoaded(_UUID))
            return;

        Winstreak streak = PlayerWinstreak.get(_UUID);
        streak.setCurrentStreak(0);
        if(Configuration.doSaveAfterEachGame()){
            Message.setDebugMessage("WinstreakManager.resetStreak Streak is saving to database for "+Bukkit.getPlayer(_UUID).getName(),DebugLevel.RUNNING);
            DataManager.resetPlayerStreakOnDatabase(_UUID);
            Message.setDebugMessage("WinstreakManager.calculateStreak Streak saved to database for " +Bukkit.getPlayer(_UUID).getName(),DebugLevel.SUCCESS);
        }
        Message.setDebugMessage("WinstreakManager.resetStreak Finished.",DebugLevel.SUCCESS);
    }

    public static boolean isPlayerDataCached(UUID _UUID){
        return cachedWinstreak.containsKey(_UUID);
    }

    public static boolean requestToCache(UUID _UUID){
        if(!DataManager.isPlayerRegisteredInDatabase(_UUID))
            return false;

        CachedRowSet playerData = null;
        try {
            playerData = DataManager.getPlayerData(_UUID);
            cachedWinstreak.put(_UUID, new Winstreak(playerData.getString("name"), _UUID, playerData.getInt("current"), playerData.getInt("highest")));
            playerData.close();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public static int getCurrentStreak(UUID uuid){
        return PlayerWinstreak.get(uuid).getCurrentStreak();
    }

    public static int getCurrentStreakFromCache(UUID uuid){
        return cachedWinstreak.get(uuid).getCurrentStreak();
    }

    public static int getBestStreak(UUID uuid){
        return PlayerWinstreak.get(uuid).getBestSteak();
    }

    public static int getBestStreakFromCache(UUID uuid){
        return cachedWinstreak.get(uuid).getBestSteak();
    }



}
