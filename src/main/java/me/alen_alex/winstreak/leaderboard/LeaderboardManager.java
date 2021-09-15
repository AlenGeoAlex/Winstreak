package me.alen_alex.winstreak.leaderboard;
import me.alen_alex.winstreak.MySQL.DataManager;
import me.alen_alex.winstreak.Winstreak;
import me.alen_alex.winstreak.utility.DebugLevel;
import me.alen_alex.winstreak.utility.Message;
import org.bukkit.Bukkit;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.util.HashMap;

public class LeaderboardManager {

    private static HashMap<Integer,Leaderboard> serverLeaderboard = new HashMap<Integer,Leaderboard>();
    private static Winstreak plugin = Winstreak.getPlugin();

    public static void updateLeaderboard() throws SQLException {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                CachedRowSet getBoardData = null;
                try {
                    getBoardData = DataManager.getLeaderboardData();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                if(getBoardData == null)
                    return;
                serverLeaderboard.clear();
                Message.setDebugMessage("Leaderboard updation has been started", DebugLevel.RUNNING);
                for(int i = 1; i<=10 ; i++){
                    try {
                        if (getBoardData.absolute(i)){
                            serverLeaderboard.put(i,new Leaderboard(getBoardData.getString("name"),getBoardData.getInt("highest")));;
                        }else{
                            serverLeaderboard.put(i,new Leaderboard(Message.parseMessage("&cN/A"),0));
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
                try {
                    getBoardData.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                Message.setDebugMessage("Leaderboard has been successfully completed", DebugLevel.SUCCESS);
            }
        });

    }

    public static String getLeaderboardTopName(int position){
        return serverLeaderboard.get(position).getName();
    }

    public static String getLeaderboardTopStreak(int position){
        return String.valueOf(serverLeaderboard.get(position).getHighest());
    }


}
