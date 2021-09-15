package me.alen_alex.winstreak.utility;

import me.alen_alex.winstreak.configuration.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Message {

    public static String parseMessage(String message){
        return ChatColor.translateAlternateColorCodes('&',message);
    }

    public static void sendMessage(Player player, String message,boolean showPrefix){
        if(showPrefix)
            player.sendMessage(Configuration.getPrefix()+parseMessage(message));
        else
            player.sendMessage(parseMessage(message));
    }

    public static void sendMessage(CommandSender sender, String message, boolean showPrefix){
        if(showPrefix)
            sender.sendMessage(Configuration.getPrefix()+parseMessage(message));
        else
            sender.sendMessage(parseMessage(message));
    }


    public static void sendAlert(Player player, String Message,boolean showPrefix){
        if(showPrefix)
            player.sendMessage(Configuration.getPrefix()+parseMessage(Message));
        else
            player.sendMessage(parseMessage(Message));
        player.playSound(player.getLocation(), Sound.NOTE_PLING,1.0F,1.0F);
    }

    public static void sendAlert(CommandSender sender, String Message,boolean showPrefix){
        if(showPrefix)
            sender.sendMessage(Configuration.getPrefix()+parseMessage(Message));
        else
            sender.sendMessage(parseMessage(Message));
        Player player = (Player) sender;
        player.playSound(player.getLocation(), Sound.NOTE_PLING,1.0F,1.0F);
    }

    public static void sendBroadcast(String Message,boolean showPrefix){
        if(showPrefix)
            Bukkit.getServer().broadcastMessage(Configuration.getPrefix()+parseMessage(Message));
        else
            Bukkit.getServer().broadcastMessage(Configuration.getPrefix()+parseMessage(Message));
    }

    public static void setDebugMessage(String message, DebugLevel level){
        DebugLevel _level = DebugLevel.DEFAULT;
        if(!Configuration.isDebuggingEnabled())
            return;
        if(level != null)
            _level = level;
        switch (_level){
            case ERROR:
                Bukkit.getLogger().info(parseMessage(ChatColor.GRAY+"["+ChatColor.YELLOW+"DEBUG"+ChatColor.GRAY+"]"+ChatColor.GRAY +"› "+ChatColor.RED+message));
                break;
            case ALERT:
                Bukkit.getLogger().info(parseMessage(ChatColor.GRAY+"["+ChatColor.YELLOW+"DEBUG"+ChatColor.GRAY+"]"+ChatColor.GRAY +"› "+ChatColor.YELLOW+message));
                break;
            case RUNNING:
                Bukkit.getLogger().info(parseMessage(ChatColor.GRAY+"["+ChatColor.YELLOW+"DEBUG"+ChatColor.GRAY+"]"+ChatColor.GRAY +"› "+ChatColor.AQUA+message));
                break;
            case SUCCESS:
                Bukkit.getLogger().info(parseMessage(ChatColor.GRAY+"["+ChatColor.YELLOW+"DEBUG"+ChatColor.GRAY+"]"+ChatColor.GRAY +"› "+ChatColor.GREEN+message));
                break;
            default:
                Bukkit.getLogger().info(parseMessage(ChatColor.GRAY+"["+ChatColor.YELLOW+"DEBUG"+ChatColor.GRAY+"]"+ChatColor.GRAY +"› "+ChatColor.WHITE+message));
        }
    }



}
