package org.github.officialbiscuityt.playerGov.commands.PlayerGovSubCommands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.github.officialbiscuityt.playerGov.Managers.ConfigManager;
import org.github.officialbiscuityt.playerGov.Managers.NationDataManager;

public class NationTransferCommand implements SubCommand {
    private final NationDataManager nationDataManager;
    private final ConfigManager configManager;

    public NationTransferCommand(NationDataManager nationDataManager, ConfigManager configManager) {
        this.nationDataManager = nationDataManager;
        this.configManager = configManager;
    }

    // Overrides Subcommand Defaults
    @Override
    public String getName() {
        return "transfer";
    }

    @Override
    public String getDescription() {
        return "Transfers a nation!";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("PlayerGov.transfer")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return;
        }
        if (args.length != 3) {
            sender.sendMessage(ChatColor.RED + "Usage: /player nation transfer <nation> <NewPlayer>");
            return;
        }
        String newPlayerName = args[2];
        String NDMId = nationDataManager.getid(args[1]);
        // Checks if nation exists
        if (!nationDataManager.exists(NDMId)) {
            sender.sendMessage(configManager.getMessage("unknown-nation", sender.getName(), args[1], ""));
            return;
        }

        String NDMname = nationDataManager.getName(NDMId);

// Works even if the target player is offline
        OfflinePlayer newPlayer = Bukkit.getOfflinePlayer(newPlayerName);
        String newPlayerUUID = newPlayer.getUniqueId().toString();

        boolean isOwner = false;

        if (sender instanceof Player player) {
            String ownerUUID = nationDataManager.getOwnerUUID(NDMId);
            isOwner = player.getUniqueId().toString().equals(ownerUUID);
        }

        if (!sender.hasPermission("playergov.transfer.other") && !isOwner) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to transfer this nation.");
            return;
        }

        if (!newPlayer.hasPlayedBefore()) {
            sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.GOLD + "PLAYERGOV" + ChatColor.GRAY + "]"
                    + ChatColor.RED + " Unknown Player!");
            return;
        }

// Check target player's nation limit
        int targetNationAmount = nationDataManager.getOwnedNationAmount(newPlayerUUID);
        int configNationLimit = Bukkit.getPluginManager()
                .getPlugin("PlayerGov").getConfig().getInt("nationLimit");

        int targetNationLimit = configNationLimit;

        for (int i = 100; i >= 1; i--) {
            if (newPlayer.isOnline()
                    && ((Player) newPlayer).hasPermission("PlayerGov.nationLimit." + i)) {
                targetNationLimit = i;
                break;
            }
        }

        if (targetNationLimit != -1 && targetNationAmount >= targetNationLimit) {
            sender.sendMessage(configManager.getMessage(
                    "targetPlayerExceedsAllowedNations", sender.getName(), NDMname, newPlayerName));
            return;
        }

        nationDataManager.transferNation(NDMId, newPlayerName, newPlayerUUID);

        sender.sendMessage(configManager.getMessage(
                "nation-transferred", sender.getName(), NDMname, newPlayerName));
    }
}