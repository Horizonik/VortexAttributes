package gemesil.vortexattributes.stats;

import gemesil.vortexattributes.VortexAttributes;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;


public class StatsManager {

    // Dictionary that contains the max level for every skill
    private static final HashMap<String, Integer> max_levels = new HashMap<String, Integer>() {{

        put("health", 100);
        put("strength", 100);
        put("armor", 500);
    }};

    // how much xp for one level?
    public static final int LEVEL_EXP_COST = 100;

    private static final String[] skills = {"health", "strength", "armor"};

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

    // ==========================================================

    public static void ResetSkills(Player player) {

        FileConfiguration stats = loadPlayerStats(player);

        // Go over all skill names
        for (String skill : skills) {

            // Set skill level to 1
            stats.set(skill, 1);

            // Set skill XP level to 0
            stats.set(skill + "XP", 0);
        }
    }

    public static boolean IsMissingSkills(Player player) {

        FileConfiguration stats = loadPlayerStats(player);

        // Go over all skill names
        for (String skill : skills) {

            // If a skill isn't in the player's file -> return true
            if (!stats.contains(skill)) return true;
        }

        // Otherwise, all skills are present -> return false
        return false;
    }

    // Check if a skill exists
    public static boolean skillExists(String skillName) {
        String[] skills = {"health", "strength", "armor"};

        // Check if given skill is in our existing skills array
        for (String skill : skills) {
            if (skill.equalsIgnoreCase(skillName))
                return true;
        }

        // Skill doesn't exist, return false!
        return false;
    }

    // Returns true if the player is gonna go over the max level
    private static boolean isGoingOverMaxLevel(Player player, String skillName, int level) {

        // Will the player go over the max level with the additional levels?
        return (getSkill(player, skillName) + level) > max_levels.get(skillName);
    }

    // Returns true if the player is already on max level
    private static boolean isMaxLevel(Player player, String skillName) {

        // Is the player's skill already maxed out?
        return getSkill(player, skillName).equals(max_levels.get(skillName));
    }

    // If the player's xp is above the limit, level them up and reset the xp
    // If not, dont do anything
    private static void levelUp(Player player, String skillName, int xp) {

        int currentXp = xp;

        // As long as the XP is above the requirements for a level
        while ((getSkillXP(player, skillName) + currentXp) >= LEVEL_EXP_COST) {

            // Level up the skill
            updateSkill(player, skillName, 1);

            // Reset xp for the skill
            setSkillXP(player, skillName, (getSkillXP(player, skillName) + currentXp) - LEVEL_EXP_COST);

            // How much xp is left
            currentXp -= LEVEL_EXP_COST;
        }
    }

    // ===========================================================================

    // Skills
    public static Integer getSkill(Player player, String skillName) {
        FileConfiguration stats = loadPlayerStats(player);
        return stats.getInt(skillName);
    }

    public static void setSkill(Player player, String skillName, Integer level) {

        FileConfiguration stats = loadPlayerStats(player);

        // If the wrong skill name was given -> exit function
        if (!skillExists(skillName)) return;

        // If the player has maxed out the skill -> exit function
        if (isMaxLevel(player, skillName)) return;

        // If the player's level is going to go over the max level
        if (isGoingOverMaxLevel(player, skillName, level)) {

            // Set their level to the max
            stats.set(skillName, max_levels.get(skillName));
        }

        else {

            // Otherwise, set their level to what was given in the input
            stats.set(skillName, level);
        }

        // Save the new change to the config file
        try {
            stats.save(new File(VortexAttributes.getPlugin().getDataFolder(), player.getUniqueId() + ".yml"));
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateSkill(Player player, String skillName, Integer levelsToAdd) {
        FileConfiguration stats = loadPlayerStats(player);
        stats.set(skillName, stats.getInt(skillName) + levelsToAdd);

        // Save the new change to the config file
        try {
            stats.save(new File(VortexAttributes.getPlugin().getDataFolder(), player.getUniqueId() + ".yml"));
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // XP
    public static Integer getSkillXP(Player player, String skillName) {
        FileConfiguration stats = loadPlayerStats(player);
        return stats.getInt(skillName + "XP");
    }

    public static void setSkillXP(Player player, String skillName, Integer xp) {

        FileConfiguration stats = loadPlayerStats(player);
        stats.set(skillName + "XP", xp);

        // Level up the skill if the xp exceeds the limit
        levelUp(player, skillName, xp);

        // Save the new change to the config file
        try {
            stats.save(new File(VortexAttributes.getPlugin().getDataFolder(), player.getUniqueId() + ".yml"));
        }

        catch (IOException e) {
            e.printStackTrace();
        }
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
}
