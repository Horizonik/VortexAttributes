package gemesil.vortexattributes.commands;

import gemesil.vortexattributes.VortexAttributes;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import static gemesil.vortexattributes.stats.StatsManager.*;

public class SetAttr implements CommandExecutor {

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
        if (!p.hasPermission("setAttr.use_command")) {
            VortexAttributes.getVortexLogger().sendNoPermsMsg(p);
            return true;
        }

        // If player, attr type and value were entered
        if (args.length > 2) {

            // If the skill name is invalid -> send error message and exit
            if (!skillExists(args[1])) {

                VortexAttributes.getVortexLogger().sendChat(p, args[1] + " is not a valid skill name!", true);
                return true;
            }

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

            // Set the skill to the requested amount
            setSkill(targetPlayer, args[1], Integer.parseInt(args[2]));

            VortexAttributes.getVortexLogger().sendChat(p, "Successfully set " + args[1] + " of player " + targetPlayer.getName() + " to " + args[2] + ".", true);
            VortexAttributes.getVortexLogger().sendChat(targetPlayer, "Attribute " + args[1] + " has been set to " + args[2] + ".", true);

            return true;
        }

        else return false;
    }
}
