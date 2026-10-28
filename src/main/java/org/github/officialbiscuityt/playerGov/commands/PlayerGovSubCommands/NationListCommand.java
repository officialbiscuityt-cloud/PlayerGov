package org.github.officialbiscuityt.playerGov.commands.PlayerGovSubCommands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.github.officialbiscuityt.playerGov.Managers.ConfigManager;
import org.github.officialbiscuityt.playerGov.Managers.NationDataManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NationListCommand implements SubCommand{
    private ConfigManager configManager;
    private NationDataManager nationDataManager;
    public NationListCommand(NationDataManager nationDataManager, ConfigManager configManager) {
        this.nationDataManager = nationDataManager;
        this.configManager = configManager;
    }

    @Override
    public String getName() {
        return "list";
    }

    @Override
    public String getDescription() {
        return "Display a list of created nations!";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        // Send the sender the list of nations
        if (!sender.hasPermission("playerGov.list")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return;
        }

        List<String> list = nationDataManager.getNationList();
        List<String> nameList = new ArrayList<>();

        for (String id : list) {
            String name = nationDataManager.getName(id);

            if (name != null) {
                nameList.add(name);
            }
        }
        sender.sendMessage(nameList.toString());
    }
}