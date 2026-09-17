package org.github.officialbiscuityt.playerGov.listeners;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.github.officialbiscuityt.playerGov.Managers.ConfigManager;
import org.github.officialbiscuityt.playerGov.Managers.NationDataManager;
import org.github.officialbiscuityt.playerGov.PlayerGov;

import java.util.List;

public class BannedPlayersListener implements Listener {

    private final NationDataManager nationDataManager;
    private final JavaPlugin plugin;

    public BannedPlayersListener(NationDataManager nationDataManager, JavaPlugin plugin) {
        this.nationDataManager = nationDataManager;
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerBan(PlayerKickEvent event) {

        // Check if the player is actually banned
        if (!Bukkit.getBanList(BanList.Type.NAME)
                .isBanned(event.getPlayer().getName())) {
            return;
        }

        String playerUUID = event.getPlayer()
                .getUniqueId()
                .toString();
        boolean disbandOnBan = Bukkit.getPluginManager().getPlugin("PlayerGov").getConfig().getBoolean("disbandAfterBan");
        // Get every nation owned by the player
        if (!disbandOnBan) {
            return;
        }

            List<String> ownedNationIds =
                    nationDataManager.getOwnedNationsId(playerUUID);

            for (String nationId : ownedNationIds) {
                nationDataManager.deleteNation(nationId);
            }

                plugin.getLogger().info("Disbanded all nations of " + event.getPlayer().getName() + " as they were banned!");
            }
        }