package gemesil.vortexattributes.commands;

import gemesil.vortexattributes.VortexAttributes;
import gemesil.vortexattributes.stats.StatsManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetAttr implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Check if the executor is not a player
        if (!(sender instanceof Player)) {
            VortexAttributes.getVortexLogger().sendAlert("Must be a player to execute this command!");
            return true;
        }

        Player p = (Player) sender;

        // Check if player has permission for command
        if (!p.hasPermission("setAttr.use_command")) {
            VortexAttributes.getVortexLogger().sendNoPermsMsg(p);
            return true;
        }

        // If player, attr type and value were entered
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

            // Check what type of attribute was requested in the command
            switch (args[1]) {

                case "health":
                    StatsManager.setHealth(targetPlayer, Integer.parseInt(args[2]));
                    break;

                case "strength":
                    StatsManager.setStrength(targetPlayer, Integer.parseInt(args[2]));
                    break;

                case "armor":
                    StatsManager.setArmor(targetPlayer, Integer.parseInt(args[2]));
                    break;

                default:
                    VortexAttributes.getVortexLogger().sendChat(p, args[1] + " is not a valid attribute!", true);
                    return true;
            }

            VortexAttributes.getVortexLogger().sendChat(p, "Successfully set " + args[1] + " of player " + targetPlayer.getName() + " to " + args[2] + ".", true);
            VortexAttributes.getVortexLogger().sendChat(p, "Attribute " + args[1] + " has been set to " + args[2] + ".", true);

            return true;
        }

        else return false;
    }
}
