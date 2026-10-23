package org.github.officialbiscuityt.playerGov.commands.PlayerGovSubCommands;

// Import Classes
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.github.officialbiscuityt.playerGov.Managers.ConfigManager;
import org.github.officialbiscuityt.playerGov.Managers.NationDataManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public class NationCreateCommand implements SubCommand {

    private final NationDataManager nationDataManager;
    private final ConfigManager configManager;

    public NationCreateCommand(NationDataManager nationDataManager, ConfigManager configManager) {
        this.nationDataManager = nationDataManager;
        this.configManager = configManager;
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

        List<String> blockedNames = Bukkit.getPluginManager().getPlugin("PlayerGov").getConfig().getStringList("blockedNationNames");

        if (blockedNames.stream().anyMatch(nationName::equalsIgnoreCase)) {
            configManager.getMessage("blocked-name", sender.getName(), nationName, ""
            );
            return;
        }
// Checks if the nation already exists
        String NDMID = nationDataManager.getid(nationName);
        if (nationDataManager.exists(NDMID)) {
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

// Ensures a player can only have as many nations as defined in the config
        int NDMnationAmount = nationDataManager.getOwnedNationAmount(player.getUniqueId().toString());
        int configNationAmount = Bukkit.getPluginManager().getPlugin("PlayerGov").getConfig().getInt("nationLimit");
        if (!(NDMnationAmount <= configNationAmount) && !(configNationAmount == -1)) {
            sender.sendMessage(configManager.getMessage("playerExceedsAllowedNations", sender.getName(), nationName, ""));
            return;
        }
// Created nation
        String nationId = UUID.randomUUID().toString();
        nationDataManager.createNation(
                nationName, nationId,
                player.getName(),
                player.getUniqueId().toString()
        );

        sender.sendMessage(
                configManager.getMessage(
                        "nation-created",
                        sender.getName(),
                        nationName,
                        ""
                )
        );
    }
}