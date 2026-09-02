package org.github.officialbiscuityt.playerGov.commands.PlayerGovSubCommands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.github.officialbiscuityt.playerGov.Managers.NationDataManager;
import org.github.officialbiscuityt.playerGov.commands.PlayerGovSubCommands.SubCommand;

import java.util.UUID;

public class NationTransferCommand implements SubCommand {
    private final NationDataManager nationDataManager;

    public NationTransferCommand(NationDataManager nationDataManager) {
        this.nationDataManager = nationDataManager;
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
        String transferNationName = args[1];
        String newPlayerName = args[2];
        String NDMnation = nationDataManager.getName(transferNationName);

        Boolean isOwner = false;


// Works even if the target player is offline
        OfflinePlayer newPlayer = Bukkit.getOfflinePlayer(newPlayerName);
        String newPlayerUUID = newPlayer.getUniqueId().toString();

        if (sender instanceof Player player) {
            String ownerUUID = nationDataManager.getOwnerUUID(transferNationName);

            isOwner = player.getUniqueId().toString().equals(ownerUUID);
        }
    if (!sender.hasPermission("playergov.transfer.other") && !isOwner) {
        return;
    }

    // Checks if nation exists
        if (!nationDataManager.exists(transferNationName)) {
            sender.sendMessage(
                    ChatColor.GRAY + "[" + ChatColor.GOLD + "PLAYERGOV" + ChatColor.GRAY + "]" + ChatColor.RED + " That nation does not exist!");
            return;
        }

        if (!newPlayer.hasPlayedBefore()) {
            sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.GOLD + "PLAYERGOV" + ChatColor.GRAY + "]" + ChatColor.RED + " Unknown Player!");
            return;
        }

    nationDataManager.transferNation(
            transferNationName,
            newPlayerName,
            newPlayerUUID
    );
        sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.GOLD + "PLAYERGOV" + ChatColor.GRAY + "]" + ChatColor.GREEN + " Nation " + ChatColor.YELLOW + transferNationName + ChatColor.GREEN + " was transfered to " + ChatColor.YELLOW + newPlayerName + ChatColor.GREEN + "!");
    }
}