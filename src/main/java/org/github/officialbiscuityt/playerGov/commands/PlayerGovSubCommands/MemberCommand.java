package org.github.officialbiscuityt.playerGov.commands.PlayerGovSubCommands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.github.officialbiscuityt.playerGov.Managers.ConfigManager;
import org.github.officialbiscuityt.playerGov.Managers.NationDataManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MemberCommand implements SubCommand {
    private final NationDataManager nationDataManager;
    private final ConfigManager configManager;

    public MemberCommand(NationDataManager nationDataManager, ConfigManager configManager) {
        this.nationDataManager = nationDataManager;
        this.configManager = configManager;
    }

    @Override
    public String getName() {
        return "member";
    }

    @Override
    public String getDescription() {
        return "Invite a player to your nation!";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        // Check arg length
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Usage: /pg member <invite/kick/list> <nation> <player>");
            return;
        }

        // /pg member invite
        if (args[1].equalsIgnoreCase("invite")) {
            if (args.length != 4) {
                sender.sendMessage(
                        ChatColor.RED + "Usage: /pg member invite <nation> <player>"
                );
                return;
            }
            String nationName = args[2];
            String invitedPlayerArg = args[3];
            String invitedPlayerName = Bukkit.getOfflinePlayer(invitedPlayerArg).getName();
            String invitedPlayerId = Bukkit.getOfflinePlayer(invitedPlayerName).getUniqueId().toString();
            String nationId = nationDataManager.getid(nationName).toString();
            if (!nationDataManager.exists(nationId)) {
                sender.sendMessage(configManager.getMessage("unknown-nation", sender.getName(), nationName, invitedPlayerName));
                return;
            }

            if (!(sender instanceof Player player)) {
                sender.sendMessage(ChatColor.RED + "Only players can add players to nations!");
                return;
            }
            if (player.getUniqueId().equals(invitedPlayerId)) {
                sender.sendMessage(configManager.getPrefix() + "" + ChatColor.RED +"You cannot invite yourself!");
                return;
            }

            String senderUUID = player.getUniqueId().toString();

            if (!nationDataManager.getOwnerUUID(nationId).equals(senderUUID) || !sender.hasPermission("playergov.admininvite")) {
                sender.sendMessage(configManager.getPrefix() + " " + ChatColor.RED + "You are not the owner of this nation!");
            }
            Player invited = Bukkit.getPlayer(invitedPlayerId);
            if (invited != null) {
                sender.sendMessage(configManager.getMessage("pending-invite", sender.getName(), nationName, invitedPlayerId));
            }

            nationDataManager.addPendingInvite(nationId, invitedPlayerId);
            sender.sendMessage(configManager.getMessage("invite-success", sender.getName(), nationName, invitedPlayerName));
        }

        // /pg member kick
        if (args[1].equalsIgnoreCase("kick")) {
            if (args.length != 4) {
                sender.sendMessage(
                        ChatColor.RED + "Usage: /pg member kick <nation> <player>"
                );
                return;
            }

            String nationName = args[2];
            String kickedPlayerArg = args[3];
            String kickedPlayerName = Bukkit.getOfflinePlayer(kickedPlayerArg).getName();
            String kickedPlayerId = Bukkit.getOfflinePlayer(kickedPlayerName).getUniqueId().toString();
            String nationId = nationDataManager.getid(nationName).toString();
            if (!sender.hasPermission("playergov.kick")) {
                sender.sendMessage(configManager.getPrefix() + " " + ChatColor.RED + "You do not have permission to use this command!");
            }
            if (!nationDataManager.exists(nationId)) {
                sender.sendMessage(configManager.getMessage("unknown-nation", sender.getName(), nationName, kickedPlayerName));
                return;
            }

            if (!(sender instanceof Player player)) {
                sender.sendMessage(ChatColor.RED + "Only players can kick players from nations!");
                return;
            }
            String senderUUID = player.getUniqueId().toString();

            if (!nationDataManager.getOwnerUUID(nationId).equals(senderUUID) || !sender.hasPermission("playergov.adminkick")) {
                sender.sendMessage(configManager.getPrefix() + " " + ChatColor.RED + "You are not the owner of this nation!");
            }

            nationDataManager.removeMember(nationId, kickedPlayerId);
            sender.sendMessage(configManager.getMessage("kick-success", sender.getName(), nationName, kickedPlayerName));
        }

        // /pg member list
        if (args[1].equalsIgnoreCase("list")) {
            if (args.length != 3) {
                sender.sendMessage(
                        ChatColor.RED + "Usage: /pg member list <nation> <player>"
                );
                return;

            }
            if (!sender.hasPermission("playergov.member.list")) {
                sender.sendMessage(configManager.getPrefix() + " " + ChatColor.RED + "You do not have permission to use this command!");
            }
            String nationName = args[2];
            String nationId = nationDataManager.getid(nationName).toString();

            List<String> list = new ArrayList<>();
            for (String s : nationDataManager.getMemberIds(nationId)) {
                list.add(s);
            }
            List<String> playerUUIDs = new ArrayList<>(list);
            List<String> playerNames = new ArrayList<>();

            for (String uuid : playerUUIDs) {
                OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
                playerNames.add(player.getName() != null ? player.getName() : uuid);
            }

            sender.sendMessage(playerNames.toString());
        }

        // /pg member accept
        if (args[1].equalsIgnoreCase("accept")) {
            if (args.length != 2) {
                sender.sendMessage(
                        ChatColor.RED + "Usage: /pg member accept"
                );
                return;
            }
            if (!sender.hasPermission("playergov.member.accept")) {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            }
            if (!(sender instanceof Player player)) {
                sender.sendMessage(ChatColor.RED + "Only players can use this command!");
                return;
            }
            String invitedPlayer = Bukkit.getPlayerUniqueId(sender.getName()).toString();

            if (nationDataManager.hasPendingInvite(invitedPlayer)) {

                List<String> invitedNations = nationDataManager.getPendingInviteInfo(invitedPlayer);

                nationDataManager.removePendingInvite(invitedNations.getFirst(), invitedPlayer);

                String firstInviteNation = nationDataManager.getPendingInviteInfo(invitedPlayer).getFirst();
                String firstInviteNationName = nationDataManager.getName(firstInviteNation);

                nationDataManager.addMember(firstInviteNation, invitedPlayer);
                sender.sendMessage(configManager.getMessage("invite-accepted", sender.getName(), firstInviteNationName, ""));
            }
        }

        // /pg member deny
        if (args[1].equalsIgnoreCase(("deny"))) {
            if (args.length != 2) {
                sender.sendMessage(
                        ChatColor.RED + "Usage: /pg member deny"
                );
                return;
            }
            if (!sender.hasPermission("playergov.member.deny")) {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            }
            if (!(sender instanceof Player player)) {
                sender.sendMessage(ChatColor.RED + "Only players can use this command!");
                return;
            }
            String invitedPlayer = Bukkit.getPlayerUniqueId(sender.getName()).toString();

            if (nationDataManager.hasPendingInvite(invitedPlayer)) {

                List<String> invitedNations = nationDataManager.getPendingInviteInfo(invitedPlayer);

                nationDataManager.removePendingInvite(invitedNations.getFirst(), invitedPlayer);

                String firstInviteNation = nationDataManager.getPendingInviteInfo(invitedPlayer).getFirst();
                String firstInviteNationName = nationDataManager.getName(firstInviteNation);

                sender.sendMessage(configManager.getMessage("invite-denied", sender.getName(), firstInviteNationName, ""));
            }
        }
    }
}