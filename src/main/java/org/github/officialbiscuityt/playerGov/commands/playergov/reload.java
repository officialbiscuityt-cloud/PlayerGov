package org.github.officialbiscuityt.playerGov.commands.playergov;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.github.officialbiscuityt.playerGov.PlayerGov;

public class reload implements CommandExecutor {

    private final PlayerGov plugin;

    public reload(PlayerGov plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Check if the player has the required permission node
        if (!sender.hasPermission("playergov.reload")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }

        // Check if the user typed '/playergov reload'
        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {

            // 1. Reload the config file from disk
            plugin.reloadConfig();

            // 2. Pass the update to your managers/variables
            plugin.loadConfigurationValues();

            sender.sendMessage(ChatColor.GREEN + "PlayerGov configuration successfully reloaded!");
            return true;
        }

        sender.sendMessage(ChatColor.RED + "Usage: /Playergov reload");
        return true;
    }
}

