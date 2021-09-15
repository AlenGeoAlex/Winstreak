package me.alen_alex.winstreak;

import me.alen_alex.winstreak.MySQL.DataManager;
import me.alen_alex.winstreak.configuration.Configuration;
import me.alen_alex.winstreak.database.mysql.MySQL;
import me.alen_alex.winstreak.leaderboard.LeaderboardManager;
import me.alen_alex.winstreak.listener.BedwarsListener;
import me.alen_alex.winstreak.listener.ConnectionListener;
import me.alen_alex.winstreak.placeholderAPI.PlaceholderAPI;
import me.alen_alex.winstreak.utility.DebugLevel;
import me.alen_alex.winstreak.utility.Message;
import me.alen_alex.winstreak.utility.Validation;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class Winstreak extends JavaPlugin {

    private static Winstreak plugin;
    private MySQL MySQLDatabase;
    private static int timer = 0;

    @Override
    public void onEnable() {
        plugin = this;

        if(!Validation.CheckForDependency()){
            getLogger().severe("===============================================");
            getLogger().severe("There are missing dependency plugins.");
            getLogger().severe("Plugin will shutdown.");
            getLogger().severe("===============================================");
            getPluginLoader().disablePlugin(this);
            return;
        }
        Configuration.CreateConfiguration();
        if(!Validation.ValidateMYSQLCreditinals()){
            getLogger().severe("Supplied MYSQL Creditionals are not valid. There are some Invalid/Null values on the configuration section mysql-server:");
            getLogger().severe("This will disable the plugin.");
            getLogger().severe("If you have just started the plugin. Add the creditionals and restart the server again");
            return;
        }
        //MySQLDatabase = new MySQL("jdbc:mysql://"+Configuration.getMysqlHost()+":"+Configuration.getMysqlPort()+"/"+Configuration.getMysqlDatabaseName()+"?autoReconnect=true",Configuration.getMysqlUsername(),Configuration.getMysqlPassword());
        MySQLDatabase = new MySQL("jdbc:mysql://in04.bya.ac:3306/s174_winstreak?autoReconnect=true","u174_KqtOnUSd9g","U6wiFDD39ma!V!2QLIjOdK45");


        DataManager.setupDatabase();
        getServer().getPluginManager().registerEvents(new ConnectionListener(), this);
        getServer().getPluginManager().registerEvents(new BedwarsListener(), this);
        updateTimer();
        if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")){
            getLogger().info("Hooking into PlaceholderAPI");
            new PlaceholderAPI(this).register();
        }
    }

    private void updateTimer(){
      final int timerCount = Configuration.getUpdateLeaderboard()*60;
        try {
            LeaderboardManager.updateLeaderboard();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
          @Override
          public void run() {
              timer += 1;
              if(timer >= timerCount){
                  Message.setDebugMessage("Starting Synchronized Leaderboard Updation", DebugLevel.DEFAULT);
                  try {
                      LeaderboardManager.updateLeaderboard();
                      timer=0;
                  } catch (SQLException throwables) {
                      throwables.printStackTrace();
                  }
              }
          }
      },100,20);
    }

    public static Winstreak getPlugin() {
        return plugin;
    }

    public MySQL getMySQLDatabase() {
        return MySQLDatabase;
    }

    public  int getTimer() {
        return timer;
    }


}
