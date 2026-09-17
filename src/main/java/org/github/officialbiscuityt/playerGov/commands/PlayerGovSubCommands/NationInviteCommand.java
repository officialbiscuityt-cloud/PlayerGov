package org.github.officialbiscuityt.playerGov.commands.PlayerGovSubCommands;

import org.bukkit.command.CommandSender;

public class NationInviteCommand implements SubCommand{
    @Override
    public String getName() {
        return "invite";
    }

    @Override
    public String getDescription() {
        return "Invite a player to your nation!";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

    }
}
