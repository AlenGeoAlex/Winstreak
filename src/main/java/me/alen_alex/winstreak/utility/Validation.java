package me.alen_alex.winstreak.utility;

import me.alen_alex.winstreak.configuration.Configuration;
import org.bukkit.Bukkit;

public class Validation {

    public static boolean CheckForDependency(){
        boolean hasPlaceholderAPI = false,hasBW1058 = false;
        if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI"))
            hasPlaceholderAPI = true;
        else
            Bukkit.getLogger().severe("PlaceholderAPI is missing. Its an hard-dependency, Install It");
        
        if(Bukkit.getPluginManager().isPluginEnabled("BedWars1058"))
            hasBW1058 = true;
        else
            Bukkit.getLogger().severe("BedWars1058 is missing. Its an hard-dependency, Install It");
        
        if(hasPlaceholderAPI && hasBW1058)
            return true;
        else 
            return false;
            
    }

    public static boolean ValidateMYSQLCreditinals(){
        boolean valid = false;
        if(Configuration.getMysqlHost() != null && Configuration.getMysqlUsername() != null && Configuration.getMysqlPort() != null && Configuration.getMysqlDatabaseName() != null)
            valid = true;

        return valid;
    }


}
