package me.alen_alex.winstreak.configuration;


import de.leonhard.storage.Config;
import de.leonhard.storage.LightningBuilder;
import de.leonhard.storage.internal.settings.ConfigSettings;
import de.leonhard.storage.internal.settings.DataType;
import de.leonhard.storage.internal.settings.ReloadSettings;
import me.alen_alex.winstreak.Winstreak;
import org.bukkit.ChatColor;

import java.io.File;

public class Configuration {

    private static Winstreak plugin = Winstreak.getPlugin();
    private static Config config;
    private static File file;
    //
    private static String mysqlHost,mysqlPort,mysqlUsername,mysqlPassword,mysqlDatabaseName;
    private static String prefix;
    private static boolean debugEnabled;
    private static boolean saveAfterEachGame;
    private static int updateLeaderboard;
    //

    public static void CreateConfiguration(){
        file = new File(plugin.getDataFolder(), "config.yml");
        if(!file.exists()){
            plugin.getLogger().info("File does not seems to exist. Creating one!");
            Config configUsingLightningBuilder = LightningBuilder
                    .fromFile(file)
                    .addInputStream(plugin.getResource("config.yml"))
                    .setDataType(DataType.SORTED)
                    .setConfigSettings(ConfigSettings.PRESERVE_COMMENTS)
                    .setReloadSettings(ReloadSettings.MANUALLY)
                    .createConfig();
        }

        config = new Config("config.yml","plugins/Winstreak");
        plugin.getLogger().info("Configuration founded and loaded");
        config.getOrSetDefault("version", 1.0);
        LoadConfigData();
    }

    private static void LoadConfigData(){
        updateLeaderboard = config.getInt("settings.leaderboard.updateLeaderboardInterval");
        updateLeaderboard = 5;
        mysqlHost = config.getString("mysql-server.host");
        mysqlPort = config.getString("mysql-server.port");
        mysqlUsername = config.getString("mysql-server.username");
        mysqlPassword = config.getString("mysql-server.password");
        mysqlDatabaseName = config.getString("mysql-server.database");
        prefix = config.getString("prefix");
        debugEnabled = config.getBoolean("debugger");
        debugEnabled = false;
        saveAfterEachGame = config.getBoolean("settings.saveGameInstantly");
        saveAfterEachGame = true;
    }


    public static Config getConfig() {
        return config;
    }

    public static String getMysqlHost() {
        return mysqlHost;
    }

    public static boolean isDebuggingEnabled() {
        return debugEnabled;
    }

    public static String getMysqlPort() {
        return mysqlPort;
    }

    public static String getMysqlUsername() {
        return mysqlUsername;
    }

    public static String getMysqlPassword() {
        return mysqlPassword;
    }

    public static String getMysqlDatabaseName() {
        return mysqlDatabaseName;
    }

    public static boolean doSaveAfterEachGame() {
        return saveAfterEachGame;
    }

    public static String getPrefix() {
        return ChatColor.translateAlternateColorCodes('&',prefix);
    }

    public static int getUpdateLeaderboard() {
        return updateLeaderboard;
    }
}
