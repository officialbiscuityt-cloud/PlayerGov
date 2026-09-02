package org.github.officialbiscuityt.playerGov.commands.PlayerGovSubCommands;

// Import Classes
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.github.officialbiscuityt.playerGov.Managers.NationDataManager;

import java.util.List;

public class NationCreateCommand implements SubCommand {

    private final NationDataManager nationDataManager;

    public NationCreateCommand(NationDataManager nationDataManager) {
        this.nationDataManager = nationDataManager;
    }

    // Overrides Subcommand Defaults
    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getDescription() {
        return "Creates a new nation!";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (args.length != 2) {
            sender.sendMessage(ChatColor.RED + "Usage: /playergov create <name>");
            return;
        }

        String nationName = args[1];

        List<String> blockedNames = Bukkit.getPluginManager()
                .getPlugin("PlayerGov")
                .getConfig()
                .getStringList("blockedNationNames");

        if (blockedNames.stream().anyMatch(nationName::equalsIgnoreCase)) {
            sender.sendMessage(
                    ChatColor.GRAY + "[" +
                            ChatColor.GOLD + "PLAYERGOV" +
                            ChatColor.GRAY + "] " +
                            ChatColor.RED + "That name is blocked on this server!"
            );
            return;
        }
// Checks if the nation already exists
        if (nationDataManager.exists(nationName)) {
            sender.sendMessage(
                    ChatColor.GRAY + "[" + ChatColor.GOLD + "PLAYERGOV" + ChatColor.GRAY + "] " + ChatColor.RED + "That nation already exists!"
            );
            return;
        }
// Ensures the sender is a player
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "Only players can create nations!");
            return;
        }
// Created nation
        nationDataManager.createNation(
                nationName,
                player.getName(),
                player.getUniqueId().toString()
        );

        sender.sendMessage(
                ChatColor.GRAY + "[" + ChatColor.GOLD + "PLAYERGOV" + ChatColor.GRAY + "] " + ChatColor.GREEN + "Nation " + ChatColor.YELLOW + nationName + ChatColor.GREEN + " has been created!"
        );
    }
}