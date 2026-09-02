package org.github.officialbiscuityt.playerGov.commands.PlayerGovSubCommands;

import org.bukkit.command.CommandSender;

public interface SubCommand {

    String getName();
    String getDescription();

    void execute(CommandSender sender, String[] args);
}