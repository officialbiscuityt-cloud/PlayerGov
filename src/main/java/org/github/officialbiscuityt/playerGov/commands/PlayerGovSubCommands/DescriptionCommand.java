package org.github.officialbiscuityt.playerGov.commands.PlayerGovSubCommands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.github.officialbiscuityt.playerGov.Managers.ConfigManager;
import org.github.officialbiscuityt.playerGov.Managers.NationDataManager;

public class DescriptionCommand implements SubCommand {

    private final NationDataManager nationDataManager;
    private final ConfigManager configManager;

    public DescriptionCommand(NationDataManager nationDataManager, ConfigManager configManager) {
        this.nationDataManager = nationDataManager;
        this.configManager = configManager;
    }

    @Override
    public String getName() {
        return "description";
    }

    @Override
    public String getDescription() {
        return "Set and Delete Nation Descriptions!";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (args.length < 2) {
            sender.sendMessage(
                    ChatColor.RED + "Usage: /playergov description <action>"
            );
            return;
        }

        if (!args[0].equalsIgnoreCase("description")) {
            return;
        }

// Description Set Command
        if (args[1].equalsIgnoreCase("set")) {
            if (args.length < 4) {
                sender.sendMessage(
                        ChatColor.RED + "Usage: /playergov description set <nation> <description>"
                );
                return;
            }
            String nationName = args[2];
            // Get nation ID
            String NDMnationId = nationDataManager.getid(nationName);

            // Check if nation exists
            if (NDMnationId == null || !nationDataManager.exists(NDMnationId)) {
                sender.sendMessage(
                        configManager.getMessage(
                                "unknown-nation",
                                sender.getName(),
                                nationName
                        )
                );
                return;
            }

            // Check owner/admin
            boolean IsOwner = false;
            boolean IsAdmin = false;

            if (sender instanceof Player player) {

                String ownerUUID =
                        nationDataManager.getOwnerUUID(NDMnationId);

                IsOwner =
                        player.getUniqueId().toString().equals(ownerUUID);

                IsAdmin =
                        player.hasPermission(
                                "PlayerGov.description.set.other"
                        );
            }

            if (!IsOwner && !IsAdmin) {
                sender.sendMessage(
                        configManager.getMessage(
                                "no-permission",
                                sender.getName(),
                                nationName
                        )
                );
                return;
            }

            // Gets the entire description
            String description = String.join(
                    " ",
                    java.util.Arrays.copyOfRange(args, 3, args.length)
            );

            // Saves description
            nationDataManager.addNationDescription(
                    NDMnationId,
                    description
            );

            sender.sendMessage(configManager.getMessage("setDescription", sender.getName(), nationName));
        }



        if (args[1].equalsIgnoreCase("delete")) {

            if (args.length < 3) {
                sender.sendMessage(
                        ChatColor.RED + "Usage: /playergov description delete <nation>"
                );
                return;
            }
            String nationName = args[2];
            // Get nation ID
            String NDMnationId = nationDataManager.getid(nationName);

            // Check if nation exists
            if (NDMnationId == null || !nationDataManager.exists(NDMnationId)) {
                sender.sendMessage(
                        configManager.getMessage(
                                "unknown-nation",
                                sender.getName(),
                                nationName
                        )
                );
                return;
            }

            // Check owner/admin
            boolean IsOwner = false;
            boolean IsAdmin = false;

            if (sender instanceof Player player) {

                String ownerUUID =
                        nationDataManager.getOwnerUUID(NDMnationId);

                IsOwner =
                        player.getUniqueId().toString().equals(ownerUUID);

                IsAdmin =
                        player.hasPermission(
                                "PlayerGov.description.set.other"
                        );
            }

            if (!IsOwner && !IsAdmin) {
                sender.sendMessage(
                        configManager.getMessage(
                                "no-permission",
                                sender.getName(),
                                nationName
                        )
                );
                return;
            }

            nationDataManager.delNationDescription(NDMnationId);
            sender.sendMessage(configManager.getMessage("deletedDescription", sender.getName(), nationName));
        }
    }
}
