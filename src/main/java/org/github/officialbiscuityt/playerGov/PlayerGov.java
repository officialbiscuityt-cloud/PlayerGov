package org.github.officialbiscuityt.playerGov;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.github.officialbiscuityt.playerGov.Managers.ConfigManager;
import org.github.officialbiscuityt.playerGov.Managers.NationDataManager;
import org.github.officialbiscuityt.playerGov.commands.PlayerGovSubCommands.*;
import org.github.officialbiscuityt.playerGov.listeners.BannedPlayersListener;

public class PlayerGov extends JavaPlugin {

    private NationDataManager nationDataManager;

    @Override
    public void onEnable() {

        // Save config
        saveDefaultConfig();
        // Welcome message
        getLogger().info("Enjoy PlayerGov! :)");

        // Load managers
        nationDataManager = new NationDataManager(this);
        ConfigManager configManager = new ConfigManager(this);
        CommandManager manager = new CommandManager();
        manager.register(new NationCreateCommand(nationDataManager, configManager));
        manager.register(new ReloadCommand(this));
        manager.register(new NationDisbandCommand(nationDataManager, configManager));
        manager.register(new NationInfoCommand(nationDataManager, configManager));
        manager.register(new NationTransferCommand(nationDataManager, configManager));
        manager.register(new DescriptionCommand(nationDataManager, configManager));

        // Load Listeners
        getServer().getPluginManager().registerEvents(new BannedPlayersListener(nationDataManager,this), this);

        // Register commands
        getCommand("playergov").setExecutor(new PlayerGovCommand(manager));
        getCommand("playergov").setTabCompleter(new PlayerGovTabCompleter(nationDataManager));
    }

    @Override
    public void onDisable() {
    }
}