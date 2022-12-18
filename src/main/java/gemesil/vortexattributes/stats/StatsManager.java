package gemesil.vortexattributes.stats;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.File;
import java.io.IOException;


public class StatsManager {

    @NonNull
    private static FileConfiguration loadPlayerStats(Player player) {
        File file = new File(player.getWorld().getName(), player.getUniqueId() + ".yml");

        // Check if file is not found
        if (!file.exists()) {
            // Return default configuration
            return new YamlConfiguration();
        }

        // Assuming it was found, load its configuration
        try {
            // Load configuration from file
            return YamlConfiguration.loadConfiguration(file);
        }

        // Error loading file
        catch (Exception e) {
            // Return default configuration
            return new YamlConfiguration();
        }
    }

    // Utils
    public static boolean containsPlayer(Player player) {

        FileConfiguration stats = StatsManager.loadPlayerStats(player);

        // Check if player has base stats
        return stats.contains("health") || stats.contains("strength") || stats.contains("armor");
    }

    public static FileConfiguration loadConfig(String filePath) {
        File file = new File(filePath);

        if (!file.exists()) {

            // File not found, create a new file
            try {
                file.createNewFile();
            }

            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return YamlConfiguration.loadConfiguration(file);
    }

    // Gets & Sets
    public static void setStrength(Player player, Integer strength) {
        FileConfiguration stats = loadPlayerStats(player);
        stats.set("strength",strength);

        // Save the new change to the config file
        try {
            stats.save(new File(player.getWorld().getName(), player.getUniqueId() + ".yml"));
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Integer getStrength(Player player) {
        FileConfiguration stats = loadPlayerStats(player);
        return stats.getInt("strength");
    }

    public static void setArmor(Player player, Integer armor) {
        FileConfiguration stats = loadPlayerStats(player);
        stats.set("armor",armor);

        // Save the new change to the config file
        try {
            stats.save(new File(player.getWorld().getName(), player.getUniqueId() + ".yml"));
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Integer getArmor(Player player) {
        FileConfiguration stats = loadPlayerStats(player);
        return stats.getInt("armor");
    }

    public static void setHealth(Player player, Integer health) {
        FileConfiguration stats = loadPlayerStats(player);
        stats.set("health",health);

        // Save the new change to the config file
        try {
            stats.save(new File(player.getWorld().getName(), player.getUniqueId() + ".yml"));
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Integer getHealth(Player player) {
        FileConfiguration stats = loadPlayerStats(player);
        return stats.getInt("health");
    }
}
