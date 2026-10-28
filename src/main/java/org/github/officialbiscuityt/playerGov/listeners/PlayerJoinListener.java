package org.github.officialbiscuityt.playerGov.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.github.officialbiscuityt.playerGov.Managers.ConfigManager;
import org.github.officialbiscuityt.playerGov.Managers.NationDataManager;

public class PlayerJoinListener implements Listener {
    private final NationDataManager nationDataManager;
    private final ConfigManager configManager;
    public PlayerJoinListener(NationDataManager nationDataManager, ConfigManager configManager) {
        this.nationDataManager = nationDataManager;
        this.configManager = configManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        String playerName = player.getName().toString();
        String playerUUID = player.getUniqueId().toString();

        if (nationDataManager.hasPendingInvite(playerUUID)) {
            event.getPlayer().sendMessage(configManager.getMessage("pending-invite", playerName, "", ""));
        }
    }
}
