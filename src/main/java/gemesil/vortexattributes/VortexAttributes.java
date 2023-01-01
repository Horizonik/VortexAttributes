package gemesil.vortexattributes;

import gemesil.vortexattributes.commands.AttrStatus;
import gemesil.vortexattributes.commands.SendLevels;
import gemesil.vortexattributes.commands.SetAttr;
import gemesil.vortexattributes.events.StatsEvents;
import gemesil.vortexlogger.VortexLogger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class VortexAttributes extends JavaPlugin {

    // Declare task class reference
    ActionBarTask task;

    // Get custom logger plugin reference
    private static final VortexLogger vortexLogger = (VortexLogger) Bukkit.getServer().getPluginManager().getPlugin("VortexLogger");

    // Get plugin reference
    private static VortexAttributes plugin;

    @Override
    public void onEnable() {

        // Register events
        Bukkit.getServer().getPluginManager().registerEvents(new StatsEvents(), this);

        // Get plugin reference
        plugin = this;

        // Register commands to the plugin
        Objects.requireNonNull(getCommand("attrSet")).setExecutor(new SetAttr());
        Objects.requireNonNull(getCommand("attrStatus")).setExecutor(new AttrStatus());
        Objects.requireNonNull(getCommand("sendLevels")).setExecutor(new SendLevels());

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

