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
        String NDMnationId = nationDataManager.getid(nationName);
        String NDMnationName = nationDataManager.getName(nationName);
        String argNationId = nationDataManager.getid(args[1]);

        // Defines variables
        if (sender instanceof Player player) {
           String playerUUID = player.getUniqueId().toString();
           String ownerUUID = nationDataManager.getOwnerUUID(NDMnationId);
           String ownerName = nationDataManager.getOwner(NDMnationId);

            if (!playerUUID.equals(ownerUUID) && !sender.hasPermission("playergov.info.other")) {
                return;
            }
            if (!nationDataManager.exists(NDMnationId)) {
                sender.sendMessage(
                        ChatColor.GRAY + "[" + ChatColor.GOLD + "PLAYERGOV" + ChatColor.GRAY + "] " + ChatColor.RED + "Nation " + ChatColor.YELLOW + nationName + ChatColor.RED + " does not exist!");
                return;
            }

           // Sends info if you are owner or admin
               sender.sendMessage(ChatColor.DARK_GRAY + "======= " + ChatColor.GRAY + "Nation Info for " + ChatColor.GOLD + ChatColor.BOLD + nationName + ChatColor.RESET + ChatColor.DARK_GRAY + " ======= ");
               sender.sendMessage(ChatColor.GRAY + "Name: " + ChatColor.WHITE + nationName);
               sender.sendMessage(ChatColor.GRAY + "Owner: " + ChatColor.WHITE + ownerName);
        }
    }
}
