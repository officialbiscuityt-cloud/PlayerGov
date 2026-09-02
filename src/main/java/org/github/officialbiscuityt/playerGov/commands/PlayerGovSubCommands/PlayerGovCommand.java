package org.github.officialbiscuityt.playerGov.commands.PlayerGovSubCommands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PlayerGovCommand implements CommandExecutor {

    private final CommandManager manager;

    public PlayerGovCommand(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.GOLD + "PLAYERGOV" + ChatColor.GRAY + "]" + ChatColor.WHITE + " Use /playergov help");
            return true;
        }

        SubCommand sub = manager.get(args[0]);

        if (sub == null) {
            sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.GOLD + "PLAYERGOV" + ChatColor.GRAY + "]" + ChatColor.RED + " Unkown command.");
            return true;
        }

        sub.execute(sender, args);
        return true;
    }
}