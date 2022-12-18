package gemesil.vortexattributes;

import gemesil.vortexattributes.commands.SetAttr;
import gemesil.vortexattributes.events.StatsEvents;
import gemesil.vortexattributes.stats.StatsManager;
import gemesil.vortexlogger.VortexLogger;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class VortexAttributes extends JavaPlugin {

    int sched;
    ActionBarTask task;

    // get custom logger plugin reference
    private static final VortexLogger vortexLogger = (VortexLogger) Bukkit.getServer().getPluginManager().getPlugin("VortexLogger");

    private static VortexAttributes plugin;

    @Override
    public void onEnable() {

        // Register events
        Bukkit.getServer().getPluginManager().registerEvents(new StatsEvents(), this);

        // Get plugin reference
        plugin = this;

        // Register commands to the plugin
        getCommand("setAttr").setExecutor(new SetAttr());

        // Start updating player's actionbars
        task = new ActionBarTask();
        task.runTaskTimer(this, 0, 20); // Run the task every 1 seconds (20 ticks)
    }

    @Override
    public void onDisable() {

        // Stop updating player's actionbars
        task.cancel();
    }

    public static VortexAttributes getPlugin() {
        return plugin;
    }

    public static VortexLogger getVortexLogger() {
        return vortexLogger;
    }

}

