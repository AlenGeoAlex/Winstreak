package me.alen_alex.winstreak.listener;

import me.alen_alex.winstreak.MySQL.DataManager;
import me.alen_alex.winstreak.Winstreak;
import me.alen_alex.winstreak.utility.DebugLevel;
import me.alen_alex.winstreak.utility.Message;
import me.alen_alex.winstreak.winstreak.WinstreakManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.SQLException;
import java.util.UUID;

public class ConnectionListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Message.setDebugMessage("ConnectionListener.onJoin triggered for Player "+event.getPlayer().getName()+". Task will be executed within 5 ticks",DebugLevel.DEFAULT);
        Player player = event.getPlayer();
        if(player == null)
            return;

        if(!DataManager.isPlayerRegisteredInDatabase(player.getUniqueId())) {
            DataManager.RegisterInDatabase(player.getName(), player.getUniqueId());
            Message.setDebugMessage("ConnectionListener.onJoin is registering " + event.getPlayer().getName() + " to database", DebugLevel.ALERT);
        }
        Bukkit.getScheduler().runTaskLater(Winstreak.getPlugin(), new Runnable() {
            @Override
            public void run() {
                try {
                    Message.setDebugMessage("ConnectionListener.onJoin Started task for "+event.getPlayer().getName(),DebugLevel.RUNNING);
                    WinstreakManager.loadData(player.getUniqueId());
                    Message.setDebugMessage("ConnectionListener.onJoin loaded playerdata for " + event.getPlayer().getName() , DebugLevel.SUCCESS);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    Message.sendMessage(player,"&eUnable to load your player data. Please create a ticket at KGO Network", true);
                    Message.setDebugMessage("Unable to load player data for "+player.getName()+" on ConnectionListener.onJoin", DebugLevel.ERROR);
                }
            }
        },5);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        Message.setDebugMessage("ConnectionListener.onPlayerLeave triggered for Player "+event.getPlayer().getName(),DebugLevel.DEFAULT);
        Player player = event.getPlayer();
        if(player == null)
            return;
        if(WinstreakManager.isDataLoaded(player.getUniqueId())){
            Message.setDebugMessage("ConnectionListener.onPlayerLeave found playerdata for "+player.getName(),DebugLevel.RUNNING);
            WinstreakManager.unloadData(player.getUniqueId());
            Message.setDebugMessage("ConnectionListener.onJoin Unloaded playerdata for " + player.getName() , DebugLevel.SUCCESS);
        }else
        Message.setDebugMessage("ConnectionListener.onPlayerLeave seems as player doesnot have a playerdata to save " + player.getName() + " to database", DebugLevel.ALERT);
    }

}
