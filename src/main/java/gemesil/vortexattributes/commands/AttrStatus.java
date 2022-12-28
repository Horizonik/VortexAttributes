package gemesil.vortexattributes.commands;

import gemesil.vortexattributes.VortexAttributes;
import gemesil.vortexattributes.stats.StatsManager;
import gemesil.vortexlogger.VortexLogger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AttrStatus implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

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
        String[] skills = {"health", "strength", "armor"};

        VortexAttributes.getVortexLogger().sendChat(p, VortexAttributes.getVortexLogger().centerMessage("================"));
        VortexAttributes.getVortexLogger().sendChat(p, VortexAttributes.getVortexLogger().centerMessage("Skills Status"));

        // Go over all skills, print each of their values in chat
        for (String skill : skills) {

            // Show skill level
            VortexAttributes.getVortexLogger().sendChat(p, skill.substring(0, 1).toUpperCase() + skill.substring(1) + " is level " + StatsManager.getSkill(p, skill) + ",", true);

            // Show current xp of skill
            VortexAttributes.getVortexLogger().sendChat(p, "with " + StatsManager.getSkillXP(p, skill) + " current XP.", true);

            // TODO
            // Show how much xp is left until the next level
        }

        VortexAttributes.getVortexLogger().sendChat(p, VortexAttributes.getVortexLogger().centerMessage("================"));

        return true;
    }
}
