package org.github.officialbiscuityt.playerGov.Managers;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigManager {

    private final JavaPlugin plugin;

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public String getMessage(String path, String player, String nation) {

        String prefix = plugin.getConfig().getString(
                "messages.prefix",
                ""
        );

        String message = plugin.getConfig().getString(
                "messages." + path,
                ""
        );

        return ChatColor.translateAlternateColorCodes(
                '&',
                prefix
                        + message
                        .replace("%player%", player)
                        .replace("%nation%", nation)
        );
    }
}