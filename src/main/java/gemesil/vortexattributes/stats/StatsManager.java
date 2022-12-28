package gemesil.vortexattributes.stats;

import gemesil.vortexattributes.VortexAttributes;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.File;
import java.io.IOException;


public class StatsManager {

    final int MAX_HEALTH_LEVEL = 100;
    final int MAX_ARMOR_LEVEL = 100;
    final int MAX_STRENGTH_LEVEL = 100;

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
            playerFile.set("healthXP",0);
            playerFile.set("armorXP",0);
            playerFile.set("strengthXP",0);

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

    // Check if a skill exists
    public static boolean doesSkillExist(String skillName) {
        String[] skills = {"health", "strength", "armor"};

        // Check if given skill is in our existing skills array
        for (String skill : skills) {
            if (skill.equalsIgnoreCase(skillName))
                return true;
        }

        // Skill doesn't exist, return false!
        return false;
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

    public static void setSkill(Player player, String skillName, Integer level) {
        FileConfiguration stats = loadPlayerStats(player);
        stats.set(skillName, level);

        // Save the new change to the config file
        try {
            stats.save(new File(VortexAttributes.getPlugin().getDataFolder(), player.getUniqueId() + ".yml"));
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Integer getSkill(Player player, String skillName) {
        FileConfiguration stats = loadPlayerStats(player);
        return stats.getInt(skillName);
    }

    public static void setSkillXP(Player player, String skillName, Integer level) {
        FileConfiguration stats = loadPlayerStats(player);
        stats.set(skillName + "XP", level);

        // Save the new change to the config file
        try {
            stats.save(new File(VortexAttributes.getPlugin().getDataFolder(), player.getUniqueId() + ".yml"));
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Integer getSkillXP(Player player, String skillName) {
        FileConfiguration stats = loadPlayerStats(player);
        return stats.getInt(skillName + "XP");
    }

    public static void updateSkillXP(Player player, String skillName, Integer xp) {
        FileConfiguration stats = loadPlayerStats(player);
        stats.set(skillName + "XP", stats.getInt(skillName + "XP") + xp);

        // Save the new change to the config file
        try {
            stats.save(new File(VortexAttributes.getPlugin().getDataFolder(), player.getUniqueId() + ".yml"));
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateSkill(Player player, String skillName, Integer level) {
        FileConfiguration stats = loadPlayerStats(player);
        stats.set(skillName, stats.getInt(skillName) + level);

        // Save the new change to the config file
        try {
            stats.save(new File(VortexAttributes.getPlugin().getDataFolder(), player.getUniqueId() + ".yml"));
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // XP per every skill
    public static void setStrengthXP(Player player, Integer xp) {
        FileConfiguration stats = loadPlayerStats(player);
        stats.set("strengthXP",xp);

        // Save the new change to the config file
        try {
            stats.save(new File(VortexAttributes.getPlugin().getDataFolder(), player.getUniqueId() + ".yml"));
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Integer getStrengthXP(Player player) {
        FileConfiguration stats = loadPlayerStats(player);
        return stats.getInt("strengthXP");
    }

    public static void setHealthXP(Player player, Integer xp) {
        FileConfiguration stats = loadPlayerStats(player);
        stats.set("healthXP",xp);

        // Save the new change to the config file
        try {
            stats.save(new File(VortexAttributes.getPlugin().getDataFolder(), player.getUniqueId() + ".yml"));
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Integer getHealthXP(Player player) {
        FileConfiguration stats = loadPlayerStats(player);
        return stats.getInt("healthXP");
    }

    public static void setArmorXP(Player player, Integer xp) {
        FileConfiguration stats = loadPlayerStats(player);
        stats.set("armorXP",xp);

        // Save the new change to the config file
        try {
            stats.save(new File(VortexAttributes.getPlugin().getDataFolder(), player.getUniqueId() + ".yml"));
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Integer getArmorXP(Player player) {
        FileConfiguration stats = loadPlayerStats(player);
        return stats.getInt("armorXP");
    }

}
