package org.github.officialbiscuityt.playerGov.commands.PlayerGovSubCommands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.github.officialbiscuityt.playerGov.Managers.ConfigManager;
import org.github.officialbiscuityt.playerGov.Managers.NationDataManager;

public class NationInfoCommand implements SubCommand {
    private final NationDataManager nationDataManager;
    private final ConfigManager configManager;

    public NationInfoCommand(NationDataManager nationDataManager, ConfigManager configManager) {
        this.nationDataManager = nationDataManager;
        this.configManager = configManager;
    }

    // Overrides Subcommand Defaults
    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "Gets nation info!";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        // Checks length
        if (args.length != 2) {
            sender.sendMessage(ChatColor.RED + "Usage: /playergov info <name>");
            return;
        }
        // Checks permission
        String nationName = args[1];
        if (!sender.hasPermission("playergov.info")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            return;
        }
        // Gets nation ID from the entered name
        String NDMnationId = nationDataManager.getid(nationName);
        // Checks if nation exists
        if (NDMnationId == null || !nationDataManager.exists(NDMnationId)) {
            sender.sendMessage(configManager.getMessage("unknown-nation", sender.getName(), nationName));
            return;
        }
        // Gets nation information
        String NDMname = nationDataManager.getName(NDMnationId);
        String ownerName = nationDataManager.getOwner(NDMnationId);
        String ownerUUID = nationDataManager.getOwnerUUID(NDMnationId);
        String NDMDescription = nationDataManager.getNationDescription(NDMnationId);
        // Players must be the owner unless they have info.other
        if (sender instanceof Player player) {
            String playerUUID = player.getUniqueId().toString();
            if (!playerUUID.equals(ownerUUID) && !sender.hasPermission("playergov.info.other")) {
                sender.sendMessage(configManager.getMessage("no-permission", sender.getName(), NDMname));
                return;
            }
        }
        // Sends info if you are owner or admin
        String description = NDMDescription == null || NDMDescription.isEmpty() ? "N/A" : NDMDescription;
        sender.sendMessage(ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "--------" + ChatColor.RESET + ChatColor.WHITE + "  " + ChatColor.GOLD + ChatColor.BOLD + nationName + ChatColor.RESET + ChatColor.WHITE + "  " + ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "--------");
        sender.sendMessage(ChatColor.GRAY + "Nation: " + ChatColor.WHITE + nationName);
        sender.sendMessage(ChatColor.GRAY + "Owner: " + ChatColor.WHITE + ownerName);
        sender.sendMessage(ChatColor.GRAY + "Description: " + ChatColor.WHITE + description);
        sender.sendMessage(ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "-------------------");
    }
}