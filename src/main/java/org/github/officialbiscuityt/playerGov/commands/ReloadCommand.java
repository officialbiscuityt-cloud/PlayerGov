package org.github.officialbiscuityt.playerGov.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class ReloadCommand implements CommandExecutor {

    private final JavaPlugin plugin;
    public ReloadCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender.hasPermission("PlayerGov.reload")) {
            plugin.reloadConfig();
            sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.GOLD + ChatColor.BOLD + "PLAYERGOV"
                    + ChatColor.RESET + ChatColor.GRAY + "]" + ChatColor.GRAY + " Plugin Reloaded Sucessfully!");
        }
        if (!sender.hasPermission("PlayerGov.reload")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
        }
        else {
            sender.sendMessage(ChatColor.RED + "An error has occured! If you need help; please contact PlayerGov support!");
        }
        return true;
    }
}
