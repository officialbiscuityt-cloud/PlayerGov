package org.github.officialbiscuityt.playerGov.commands.PlayerGovSubCommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class PlayerGovTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {

        List<String> completions = new ArrayList<>();

        if(args.length == 1) {
            completions.add("create");
            completions.add("reload");
            completions.add("disband");
            completions.add("info");
            completions.add("transfer");
        }

        return completions;
    }
}
