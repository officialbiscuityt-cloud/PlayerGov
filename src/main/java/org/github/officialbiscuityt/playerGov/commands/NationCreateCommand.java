package org.github.officialbiscuityt.playerGov.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.github.officialbiscuityt.playerGov.Managers.NationDataManager;

import java.util.List;

public class NationCreateCommand implements CommandExecutor {

    private final NationDataManager nationDataManager;

    public NationCreateCommand(NationDataManager nationDataManager) {
        this.nationDataManager = nationDataManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /nationcreate <name>");
            return false;
        }

        String nationName = args[0];

        List<String> blockedNames = Bukkit.getPluginManager()
                .getPlugin("PlayerGov")
                .getConfig()
                .getStringList("blockedNationNames");

        if (blockedNames.stream().anyMatch(nationName::equalsIgnoreCase)) {
            sender.sendMessage(
                    ChatColor.GRAY + "[" +
                            ChatColor.YELLOW + ChatColor.BOLD + "PLAYERGOV" +
                            ChatColor.RESET + ChatColor.GRAY + "]" +
                            ChatColor.RED + " This name is blocked on this server!"
            );
            return false;
        }

        if (nationDataManager.exists(nationName)) {
            sender.sendMessage(ChatColor.RED + "That nation already exists!");
            return false;
        }

        nationDataManager.createNation(nationName, sender.getName());

        sender.sendMessage(ChatColor.GREEN + "Nation " + nationName + " created!");

        return true;
    }
}
