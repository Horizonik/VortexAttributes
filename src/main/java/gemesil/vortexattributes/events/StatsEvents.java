package gemesil.vortexattributes.events;

import gemesil.vortexattributes.stats.StatsManager;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import static gemesil.vortexattributes.stats.StatsManager.IsMissingSkills;
import static gemesil.vortexattributes.stats.StatsManager.ResetSkills;

public class StatsEvents implements Listener {

    // Apply skills on first time join
    @EventHandler
    public void onPlayerJoin (PlayerJoinEvent event) {

        Player player = event.getPlayer();

        // The player is missing skills from file -> supposedly first time join
        if (IsMissingSkills(player))
            ResetSkills(player);
    }

    // Leech levels when killing a player
    @EventHandler
    public void onPlayerKill(final PlayerDeathEvent e) {

        // If the killer wasn't a player -> we don't do anything
        if (e.getEntity().getKiller() == null) return;

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
