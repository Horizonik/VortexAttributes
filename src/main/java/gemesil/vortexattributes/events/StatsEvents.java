package gemesil.vortexattributes.events;

import gemesil.vortexattributes.VortexAttributes;
import gemesil.vortexattributes.stats.StatsManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class StatsEvents implements Listener {

    // Apply buffs on first time join
    @EventHandler
    public void onPlayerJoin (PlayerJoinEvent event) {

        Player player = event.getPlayer();

        // Check if the player is not in the stats database
        if (!StatsManager.containsPlayer(player)) {

            // Set base stats
            StatsManager.setHealth(player, 20);
            StatsManager.setArmor(player, 1);
            StatsManager.setStrength(player, 1);
        }
    }
}
