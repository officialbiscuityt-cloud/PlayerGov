package org.github.officialbiscuityt.playerGov.commands.PlayerGovSubCommands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.github.officialbiscuityt.playerGov.Managers.ConfigManager;
import org.github.officialbiscuityt.playerGov.Managers.NationDataManager;

public class NationDisbandCommand implements SubCommand{
    private final NationDataManager nationDataManager;
    private final ConfigManager configManager;

    public NationDisbandCommand(NationDataManager nationDataManager, ConfigManager configManager) {
        this.nationDataManager = nationDataManager;
        this.configManager = configManager;
    }
    // Overrides Subcommand Defaults
    @Override
    public String getName() {
        return "disband";
    }

    @Override
    public String getDescription() {
        return "Disbands a created Nation!";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        // Checks length
        if (args.length != 2) {
            sender.sendMessage(ChatColor.RED + "Usage: /playergov disband <name>");
            return;
        }
        String nationName = args[1];
        // Checks disband permission
        if (!sender.hasPermission("playergov.nations.disband")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return;
        }
        String NDMID = nationDataManager.getid(nationName);
        // Defines variables
        boolean IsConsole = !(sender instanceof Player player);
        boolean IsAdmin = (sender.hasPermission("playergov.nations.*"));
        boolean IsOwner = false;

        String NDMname = nationDataManager.getName(NDMID);

        // Checks if the sender is a player
        if (sender instanceof Player player) {

            String ownerUUID = nationDataManager.getOwnerUUID(NDMID);
            IsOwner = player.getUniqueId().toString().equals(ownerUUID);
        }
        // Checks if nation exists
        if (!nationDataManager.exists(NDMID)) {
            sender.sendMessage(configManager.getMessage("unknown-nation", sender.getName(), NDMname));

            return;
        }
        // Disbands nation
        if (IsOwner || IsConsole || IsAdmin) {
            nationDataManager.deleteNation(NDMID);
            sender.sendMessage(configManager.getMessage("nation-disbanded", sender.getName(), NDMname));
        }

        }
    }

