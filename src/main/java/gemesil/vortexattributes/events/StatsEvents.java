package gemesil.vortexattributes.events;

import com.google.common.math.Stats;
import gemesil.vortexattributes.VortexAttributes;
import gemesil.vortexattributes.stats.StatsManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class StatsEvents implements Listener {

    // Apply buffs on first time join
    @EventHandler
    public void onPlayerJoin (PlayerJoinEvent event) {

        Player player = event.getPlayer();

//        // Check if the player is not in the stats database
//        if (!StatsManager.containsPlayer(player)) {
//
//            // Set base stats
//            StatsManager.setHealth(player, 20);
//            StatsManager.setArmor(player, 1);
//            StatsManager.setStrength(player, 1);
//        }
    }

    // Leech levels when killing a player
    @EventHandler
    public void onPlayerKill(final PlayerDeathEvent e) {

        // If the killer wasn't a player -> we don't do anything
        if (!(e.getEntity().getKiller() instanceof Player)) {
            return;
        }

        // Save reference to killer and victim
        Player killerPlayer = e.getEntity().getKiller(); // the killer
        Player victimPlayer = e.getEntity().getPlayer(); // the killed

        // All the skill names that we would like to iterate over
        String[] skillNames = {"health", "strength", "armor"};

        // "Leech" skill levels from the victim to the killer
        for (String skill : skillNames) {

            // Save 20% of the skill's levels from the player that was killed
            int twentyPercent = StatsManager.getSkill(victimPlayer, skill) / 5;

            // Remove 20% of the levels from the player that was killed
            StatsManager.setSkill(victimPlayer, skill, Math.max(StatsManager.getSkill(victimPlayer, skill) - twentyPercent, 0));

            // Award those 20% to the killer
            StatsManager.setSkill(killerPlayer, skill, Math.max(StatsManager.getSkill(killerPlayer, skill) + twentyPercent, 0));
        }

        // Improve the strength skill xp by 5 for every kill
        StatsManager.updateSkillXP(killerPlayer, "strength", 5);
    }

    @EventHandler
    public void onEntityDeath(final EntityDeathEvent e) {

        // Is the entity a mob? -> exit if not
        if (!(e.getEntity() instanceof Monster))
            return;

        // Is the killer a player? -> exit if not
        if (!(e.getEntity() instanceof Player))
            return;

        Monster mob = (Monster) e.getEntity();
        Player p = mob.getKiller();

        StatsManager.updateSkillXP(p, "health", 1);
    }







}
