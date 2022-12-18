package gemesil.vortexattributes.stats;

import gemesil.vortexattributes.VortexAttributes;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.File;
import java.io.IOException;


public class StatsManager {

    private static void doesDataFolderExist() {

        File dataFolder = VortexAttributes.getPlugin().getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
    }

    @NonNull
    private static File getPlayerFile(Player player) {

        // If it doesn't exist, creates one
        doesDataFolderExist();

        File file = new File(VortexAttributes.getPlugin().getDataFolder(), player.getUniqueId() + ".yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            FileConfiguration playerFile = loadPlayerStats(player);

            // Set base stats
            playerFile.set("health", 20);
            playerFile.set("armor",1);
            playerFile.set("strength",1);

            // Save the new change to the config file
            try {
                playerFile.save(new File(VortexAttributes.getPlugin().getDataFolder(), player.getUniqueId() + ".yml"));
            }

            catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
    }

    @NonNull
    private static FileConfiguration loadPlayerStats(Player player) {

        // Load configuration from file
        try {
            return YamlConfiguration.loadConfiguration(getPlayerFile(player));
        }
        // Error loading file
        catch (Exception e) {

            // Return default configuration
            return new YamlConfiguration();
        }
    }

    // Utils
    public static boolean containsPlayer(Player player) {

        FileConfiguration stats = loadPlayerStats(player);

        // Check if player has base stats
        return stats.contains("health") || stats.contains("strength") || stats.contains("armor");
    }

    // Gets & Sets
    public static void setStrength(Player player, Integer strength) {
        FileConfiguration stats = loadPlayerStats(player);
        stats.set("strength",strength);

        // Save the new change to the config file
        try {
            stats.save(new File(VortexAttributes.getPlugin().getDataFolder(), player.getUniqueId() + ".yml"));
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
            stats.save(new File(VortexAttributes.getPlugin().getDataFolder(), player.getUniqueId() + ".yml"));
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
            stats.save(new File(VortexAttributes.getPlugin().getDataFolder(), player.getUniqueId() + ".yml"));
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
