package me.alen_alex.winstreak.listener;

import com.andrei1058.bedwars.api.events.gameplay.GameEndEvent;
import com.andrei1058.bedwars.api.events.player.PlayerJoinArenaEvent;
import me.alen_alex.winstreak.Winstreak;
import me.alen_alex.winstreak.utility.DebugLevel;
import me.alen_alex.winstreak.utility.Message;
import me.alen_alex.winstreak.winstreak.WinstreakManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;
import java.util.UUID;

public class BedwarsListener implements Listener {

    @EventHandler
    public void onArenaJoinEvent(PlayerJoinArenaEvent event){
        Player player = event.getPlayer();
        if(player == null)
            return;
        if(!WinstreakManager.isDataLoaded(player.getUniqueId())){
            event.setCancelled(true);
            Message.sendMessage(player,"&cSeems like your data is not fetched from the server. Disconnect and rejoin and if the issue still persist, Contact an adminstrator",true);
            return;
        }
    }

    @EventHandler
    public void onGameEnd(GameEndEvent event){
        Message.setDebugMessage("BedwarsListener.onGameEnd Triggered Game End Event", DebugLevel.DEFAULT);
        List<UUID> gameWinners = event.getWinners();
        List<UUID> gameLossers = event.getLosers();

        Message.setDebugMessage("BedwarsListener.onGameEnd Starting Async Task For Streak Calculation",DebugLevel.DEFAULT);
        Bukkit.getScheduler().runTaskAsynchronously(Winstreak.getPlugin(), new Runnable() {
            @Override
            public void run() {
                Message.setDebugMessage("BedwarsListener.onGameEnd Started Task For Streak Calculation On Winners",DebugLevel.RUNNING);
                gameWinners.forEach(WinstreakManager::calculateStreak);
                gameLossers.forEach(WinstreakManager::resetStreak);
                Message.setDebugMessage("BedwarsListener.onGameEnd Successfully completed task for streak calculation.",DebugLevel.SUCCESS);
            }
        });
        Message.setDebugMessage("BedwarsListener.onGameEnd Finished Async Task For Streak Calculation",DebugLevel.SUCCESS);

    }

}
