package me.alen_alex.winstreak.MySQL;

import me.alen_alex.winstreak.Winstreak;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class DataManager {
    private static Winstreak plugin = Winstreak.getPlugin();

    public static void RegisterInDatabase(String name, UUID UUID) {
        try {
            plugin.getMySQLDatabase().update("INSERT INTO winstreak (name,uuid,current,highest) VALUES ('" + name + "','" + UUID.toString() + "','0','0');");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static boolean isPlayerRegisteredInDatabase(UUID _UUID) {
        AtomicBoolean registered = new AtomicBoolean(false);
        try {
            plugin.getMySQLDatabase().query("SELECT name FROM winstreak WHERE `uuid` = '" + _UUID.toString() + "';", resultSet -> {
                while (resultSet.next())
                    registered.set(true);
                resultSet.getStatement().close();
            });
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return registered.get();
    }

    public static void updatePlayerDataToDatabase(UUID UUID, int current, int highest) {
        try {
            plugin.getMySQLDatabase().update("UPDATE winstreak SET `current` = '" + String.valueOf(current) + "', `highest` = '" + String.valueOf(highest) + "' WHERE `uuid` = '" + UUID.toString() + "';");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void setupDatabase() {
        try {
            plugin.getMySQLDatabase().update("CREATE TABLE IF NOT EXISTS winstreak (`id` int NOT NULL AUTO_INCREMENT PRIMARY KEY, `name` VARCHAR(30) NOT NULL, `uuid` VARCHAR(50) NOT NULL, `current` INT(3) NOT NULL, `highest` INT(3) NOT NULL );");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static CachedRowSet getPlayerData(UUID _UUID) throws SQLException {
        CachedRowSet set = plugin.getMySQLDatabase().queryRowSet("SELECT * FROM winstreak WHERE `uuid` = '"+_UUID.toString()+"';");
        while (set.next())
            return set;
        return null;
    }

    public static CachedRowSet getLeaderboardData() throws SQLException {
        CachedRowSet rowSet = plugin.getMySQLDatabase().queryRowSet("SELECT name , highest FROM winstreak ORDER BY highest DESC;");
        while (rowSet.next())
            return rowSet;
        return null;
    }

    public static void addStreakToDatabase(UUID _UUID){
        try {
            plugin.getMySQLDatabase().update("UPDATE winstreak SET `current` = `current` + 1 WHERE uuid = '"+_UUID.toString()+"';");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void resetPlayerStreakOnDatabase(UUID _UUID){
        try {
            plugin.getMySQLDatabase().update("UPDATE winstreak SET `current` = '0' WHERE uuid = '"+_UUID.toString()+"';");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void setHighest(UUID _UUID, int highest){
        try {
            plugin.getMySQLDatabase().update("UPDATE winstreak set `highest` = '"+highest+"' WHERE UUID ='"+_UUID.toString()+"';");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }




}
