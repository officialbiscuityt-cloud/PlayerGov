package org.github.officialbiscuityt.playerGov.commands.PlayerGovSubCommands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.github.officialbiscuityt.playerGov.Managers.ConfigManager;

public class ReloadCommand implements SubCommand {

    private final JavaPlugin plugin;
    private final ConfigManager configManager;
    public ReloadCommand(JavaPlugin plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
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
            sender.sendMessage(configManager.getPrefix() +  " Plugin Reloaded Sucessfully!");
            if (sender instanceof Player) {
                plugin.getLogger().info("reloaded by: " + sender.getName());
            }
        }
    }
}