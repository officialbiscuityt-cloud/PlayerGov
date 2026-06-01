package org.github.officialbiscuityt.playerGov;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class TestListener implements Listener {

@EventHandler
public void onBlockPlace(BlockPlaceEvent event) {
    Material type1 = event.getBlock().getType();
    if (type1 == Material.TORCH) {
        Player player = event.getPlayer();
        player.sendMessage(ChatColor.GREEN + "Travel wise");
    }
}
@EventHandler
public void onBlockBreak(BlockBreakEvent event) {
    Material type2 = event.getBlock().getType();
    if (type2 == Material.BLACK_BED) {
        Player player = event.getPlayer();
        player.sendMessage(ChatColor.RED + "You have removed your bed, you will no longer respawn at this location!");
    }
}

}