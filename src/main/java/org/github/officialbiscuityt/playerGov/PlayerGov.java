package org.github.officialbiscuityt.playerGov;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerGov extends JavaPlugin
{

    @Override
    public void onEnable() {
        // Plugin startup logic

        // Print PlayerGov to console
        getLogger().info("""

██████╗ ██╗      █████╗ ██╗   ██╗███████╗██████╗  ██████╗  ██████╗ ██╗   ██╗
██╔══██╗██║     ██╔══██╗╚██╗ ██╔╝██╔════╝██╔══██╗██╔════╝ ██╔═══██╗██║   ██║
██████╔╝██║     ███████║ ╚████╔╝ █████╗  ██████╔╝██║  ███╗██║   ██║██║   ██║
██╔═══╝ ██║     ██╔══██║  ╚██╔╝  ██╔══╝  ██╔══██╗██║   ██║██║   ██║╚██╗ ██╔╝
██║     ███████╗██║  ██║   ██║   ███████╗██║  ██║╚██████╔╝╚██████╔╝ ╚████╔╝
╚═╝     ╚══════╝╚═╝  ╚═╝   ╚═╝   ╚══════╝╚═╝  ╚═╝ ╚═════╝  ╚═════╝   ╚═══╝
""");

            saveResource("config.yml", /* replace */ false);


            // You can also use this for configuration files:
            saveDefaultConfig();
            // Where the default config.yml will be saved if it does not already exist

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void loadConfigurationValues() {
    }
}
