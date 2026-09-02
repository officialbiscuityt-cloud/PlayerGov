package org.github.officialbiscuityt.playerGov.commands.PlayerGovSubCommands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.github.officialbiscuityt.playerGov.PlayerGov;

public class ReloadCommand implements SubCommand {

    private final JavaPlugin plugin;
    public ReloadCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "Reloads the plugin!";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender.hasPermission("PlayerGov.reload")) {
            sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.GOLD + "PLAYERGOV" + ChatColor.GRAY + "]" + ChatColor.GREEN + " Reloading Plugin...");
            plugin.reloadConfig();
            sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.GOLD + "PLAYERGOV" + ChatColor.GRAY + "]" + ChatColor.WHITE + " Plugin Reloaded Sucessfully!");
            if (sender instanceof Player) {
                plugin.getLogger().info("PlayerGOV was reloaded by: " + sender.getName());
            }
        }
    }
}