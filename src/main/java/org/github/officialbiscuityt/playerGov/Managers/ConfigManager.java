package org.github.officialbiscuityt.playerGov.Managers;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigManager {

    private final JavaPlugin plugin;

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public String getMessage(String path, String player, String nation, String targetplayer) {

        // Makes the prefix defined in the config.yml a string
        String prefix = plugin.getConfig().getString(
                "messages.prefix",
                ""
        + " ");
        // Gets message from path
        String message = plugin.getConfig().getString(
                "messages." + path,
                ""
        );
        // Allows color codes with "&", replaces %-% config variables
        return ChatColor.translateAlternateColorCodes(
                '&',
                prefix
                        + message
                        .replace("%player%", player)
                        .replace("%nation%", nation)
                        .replace("%target%", targetplayer)
        );
    }

    public String getMessageWithoutPrefix(String path, String player, String nation, String targetplayer) {
        // Gets message from path
        String message = plugin.getConfig().getString(
                "messages." + path,
                ""
        );
        // Allows color codes with "&", replaces %-% config variables
        return ChatColor.translateAlternateColorCodes(
                '&',
                         message
                        .replace("%player%", player)
                        .replace("%nation%", nation)
                        .replace("%target%", targetplayer)
        );
    }
}