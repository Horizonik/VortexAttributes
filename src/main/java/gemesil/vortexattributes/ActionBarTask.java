package gemesil.vortexattributes;

import gemesil.vortexattributes.stats.StatsManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ActionBarTask extends BukkitRunnable {

    AttributeInstance healthAttr, strengthAttr, armorAttr;

    @Override
    public void run() {

        for (Player player : Bukkit.getOnlinePlayers()) {

            // Handle action bar
            String message = ChatColor.translateAlternateColorCodes('&', "&c%current_health%/%health%&f\uE250    &eStrength&8: &7%strength%    &bArmor&8: &7%armor%");
            message = message.replace("%health%", StatsManager.getHealth(player).toString());
            message = message.replace("%current_health%", Integer.toString((int) player.getHealth()));
            message = message.replace("%strength%", StatsManager.getStrength(player).toString());
            message = message.replace("%armor%", StatsManager.getArmor(player).toString());

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));

            // Set new max health of player
            healthAttr = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            assert healthAttr != null;
            healthAttr.setBaseValue(Math.max(StatsManager.getHealth(player) / 5, 0));

            // Set new strength of player
            strengthAttr = player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
            assert strengthAttr != null;
            strengthAttr.setBaseValue(Math.max(StatsManager.getStrength(player) / 10, 0));

            // Set new armor of player
            armorAttr = player.getAttribute(Attribute.GENERIC_ARMOR);
            assert armorAttr != null;
            armorAttr.setBaseValue(Math.max(StatsManager.getArmor(player), 0));
        }
    }
}
