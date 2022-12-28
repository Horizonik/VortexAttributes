package gemesil.vortexattributes.commands;

import gemesil.vortexattributes.VortexAttributes;
import gemesil.vortexattributes.stats.StatsManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SendLevels implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Check if the executor is not a player
        if (!(sender instanceof Player)) {
            VortexAttributes.getVortexLogger().sendAlert("Must be a player to execute this command!");
            return true;
        }

        Player p = (Player) sender;

        // Check if player has permission for command
        if (!p.hasPermission("sendLevels.use_command")) {
            VortexAttributes.getVortexLogger().sendNoPermsMsg(p);
            return true;
        }

        // If player, attr type and value were entered
        // Command in this format: /sendlevels player-name skill amount
        if (args.length > 2) {

            // Check if player is online
            Player targetPlayer = null;

            // Check if that player is currently online
            for (Player currentOnlinePlayer : Bukkit.getServer().getOnlinePlayers()) {

                // If the player who the commandSender is trying to get is online
                if (args[0].equals(currentOnlinePlayer.getName())) {

                    // Get the player object and exit the loop
                    targetPlayer = currentOnlinePlayer;
                    break;
                }
            }

            // When our target wasn't found
            if (targetPlayer == null) {

                // Alert the command sender the player wasn't found
                VortexAttributes.getVortexLogger().sendChat(p, "Player " + args[0] + " is either not online, or doesn't actually exist!", true);
                return true;
            }

            // Check if the given skill actually exists in the db
            if (StatsManager.doesSkillExist(args[1])) {

                // Does the player have enough levels to give away?
                if (StatsManager.getSkill(p, args[1]) >= Integer.parseInt(args[2])) {

                    // Send the levels to the target player
                    StatsManager.setSkill(targetPlayer, args[1], Integer.parseInt(args[2]));

                    // Remove the levels from the command sender
                    StatsManager.setSkill(p, args[1], StatsManager.getSkill(p, args[1]) - Integer.parseInt(args[2]));
                }
            }

            // The skill name that was entered is not of a real skill
            else
                VortexAttributes.getVortexLogger().sendChat(p, args[1] + " is not a valid attribute!", true);

            // Send prompt to command sender
            VortexAttributes.getVortexLogger().sendChat(p, "Successfully sent "  + args[2] + " " + args[1] + " levels to player " + targetPlayer.getName() + ".", true);

            // Send prompt to the target player
            VortexAttributes.getVortexLogger().sendChat(targetPlayer, "Received " + args[2] + " " + args[1] + " levels from " + p.getName() + ".", true);

            return true;
        }

        else return false;
    }
}
