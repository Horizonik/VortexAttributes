package gemesil.vortexattributes.commands;

import gemesil.vortexattributes.VortexAttributes;
import gemesil.vortexattributes.stats.StatsManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import static gemesil.vortexattributes.stats.StatsManager.LEVEL_EXP_COST;

public class AttrStatus implements CommandExecutor {

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String[] args) {

        // Check if the executor is not a player
        if (!(sender instanceof Player)) {
            VortexAttributes.getVortexLogger().sendAlert("Must be a player to execute this command!");
            return true;
        }

        Player p = (Player) sender;

        // Check if player has permission for command
        if (!p.hasPermission("attrStatus.use_command")) {
            VortexAttributes.getVortexLogger().sendNoPermsMsg(p);
            return true;
        }

        // Skill names
        final String[] skills = {"health", "strength", "armor"};

        // Print stat of skills screen
        VortexAttributes.getVortexLogger().sendChat(p, VortexAttributes.getVortexLogger().centerMessage("================"), false);
        VortexAttributes.getVortexLogger().sendChat(p, VortexAttributes.getVortexLogger().centerMessage("Skills Status"), false);
        VortexAttributes.getVortexLogger().sendChat(p, "", false);

        // Go over all skills, print each of their values in chat
        for (String skill : skills) {

            // Show skill level
            VortexAttributes.getVortexLogger().sendChat(p, skill.substring(0, 1).toUpperCase() + skill.substring(1) + " is level " + StatsManager.getSkill(p, skill) + ",", true);

            // Show current xp of skill
            VortexAttributes.getVortexLogger().sendChat(p, "with " + StatsManager.getSkillXP(p, skill) + "/" + LEVEL_EXP_COST + " current XP.", true);

            // Print empty line to separate fields
            VortexAttributes.getVortexLogger().sendChat(p, "", false);
        }

        // Print end of skills screen
        VortexAttributes.getVortexLogger().sendChat(p, VortexAttributes.getVortexLogger().centerMessage("================"));

        return true;
    }
}
