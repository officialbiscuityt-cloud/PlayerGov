package org.github.officialbiscuityt.playerGov;

import org.bukkit.plugin.java.JavaPlugin;
import org.github.officialbiscuityt.playerGov.Managers.NationDataManager;
import org.github.officialbiscuityt.playerGov.commands.PlayerGovSubCommands.*;

public class PlayerGov extends JavaPlugin {

    private NationDataManager nationDataManager;

    @Override
    public void onEnable() {

        // Save config
        saveDefaultConfig();

        // Load managers
        nationDataManager = new NationDataManager(this);
        CommandManager manager = new CommandManager();
        manager.register(new NationCreateCommand(nationDataManager));
        manager.register(new ReloadCommand(this));
        manager.register(new NationDisbandCommand(nationDataManager));
        manager.register(new NationInfoCommand(nationDataManager));

        // Welcome message
        getLogger().info("Enjoy PlayerGov! :)");

        // Register commands

        getCommand("playergov").setExecutor(new PlayerGovCommand(manager));

        getCommand("playergov").setTabCompleter(new PlayerGovTabCompleter());
    }

    @Override
    public void onDisable() {
    }
}