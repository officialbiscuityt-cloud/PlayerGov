package org.github.officialbiscuityt.playerGov.commands.PlayerGovSubCommands;

import org.github.officialbiscuityt.playerGov.commands.PlayerGovSubCommands.SubCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {

    private final Map<String, SubCommand> commands = new HashMap<>();

    public void register(SubCommand command) {
        commands.put(command.getName().toLowerCase(), command);
    }
    public SubCommand getCommand(String name) {
        return commands.get(name.toLowerCase());
    }
    public Map<String, SubCommand> getCommands() {
        return commands;
    }
    public SubCommand get(String name)  {
        return commands.get(name.toLowerCase());
    }
}
