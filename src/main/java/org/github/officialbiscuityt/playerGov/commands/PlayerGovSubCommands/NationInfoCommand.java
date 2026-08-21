package org.github.officialbiscuityt.playerGov.commands.PlayerGovSubCommands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.github.officialbiscuityt.playerGov.Managers.NationDataManager;

public class NationInfoCommand implements SubCommand {
    private final NationDataManager nationDataManager;

    public NationInfoCommand(NationDataManager nationDataManager) {
        this.nationDataManager = nationDataManager;
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
        // Checks if nation exists
        String NDMnation = nationDataManager.getName(nationName);
        if (!nationName.equalsIgnoreCase(NDMnation)) {
            sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.GOLD + "PLAYERGOV" + ChatColor.GRAY + "]" + ChatColor.RED + " That nation does not exist!");
            return;
        }
        // Defines variables
        if (sender instanceof Player player) {
           String playerUUID = player.getUniqueId().toString();
           String ownerUUID = nationDataManager.getOwnerUUID(nationName);
           String ownerName = nationDataManager.getOwner(nationName);

           // Sends info if you are owner or admin
           if (playerUUID.equals(ownerUUID) || sender.hasPermission("playergov.info.other")) {
               sender.sendMessage(ChatColor.DARK_GRAY + "========== " + ChatColor.GOLD + ChatColor.BOLD + "Nation Info for " + nationName + ChatColor.RESET + ChatColor.DARK_GRAY + " ========== ");
               sender.sendMessage(ChatColor.GRAY + "Name: " + ChatColor.WHITE + nationName);
               sender.sendMessage(ChatColor.GRAY + "Owner: " + ChatColor.WHITE + ownerName);
           }
        }
    }
}
