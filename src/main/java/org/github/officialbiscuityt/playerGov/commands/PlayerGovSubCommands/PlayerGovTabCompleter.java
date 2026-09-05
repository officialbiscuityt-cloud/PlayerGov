package org.github.officialbiscuityt.playerGov.commands.PlayerGovSubCommands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.github.officialbiscuityt.playerGov.Managers.NationDataManager;

import java.util.ArrayList;
import java.util.List;

public class PlayerGovTabCompleter implements TabCompleter {

    private final NationDataManager nationDataManager;

    public PlayerGovTabCompleter(NationDataManager nationDataManager) {
        this.nationDataManager = nationDataManager;
    }

    @Override
    public List<String> onTabComplete(
            CommandSender sender,
            Command command,
            String s,
            String[] args) {

        List<String> completions = new ArrayList<>();

        // Subcommands
        if (args.length == 1) {
            completions.add("create");
            completions.add("reload");
            completions.add("disband");
            completions.add("info");
            completions.add("transfer");
        }

        // Nation names
        if (args.length == 2 &&
                (args[0].equalsIgnoreCase("disband") || args[0].equalsIgnoreCase("info") || args[0].equalsIgnoreCase("transfer"))) {

            if (!(sender instanceof Player player)) {
                return completions;
            }

            String input = args[1].toLowerCase();

            return nationDataManager.getOwnedNations(
                            player.getUniqueId().toString()
                    ).stream()
                    .filter(nation ->
                            nation.toLowerCase().startsWith(input))
                    .toList();
        }

        // Player names for transfer
        if (args.length == 3 &&
                args[0].equalsIgnoreCase("transfer")) {

            String input = args[2].toLowerCase();

            return Bukkit.getOnlinePlayers().stream().map(Player::getName).filter(name -> name.toLowerCase().startsWith(input)).toList();
        }

        return completions;
    }
}