package org.github.officialbiscuityt.playerGov;

// Imports
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.github.officialbiscuityt.playerGov.Managers.NationDataManager;
import org.github.officialbiscuityt.playerGov.commands.NationCreateCommand;
import org.github.officialbiscuityt.playerGov.commands.ReloadCommand;
//import org.github.officialbiscuityt.playerGov.commands.playergov.reload;


// Clase defines
public class PlayerGov extends JavaPlugin{
private static PlayerGov instance;
private NationDataManager nationDataManager;

    @Override
    public void onEnable() {
        instance = this;
        // Loads Managers
        nationDataManager = new NationDataManager(this);

        // Saves config File
        saveDefaultConfig();
        // Prints PlayerGov Welcoming Statement
        getLogger().info("Enjoy PlayerGov! :)");

        // Registers Commands
        this.getCommand("test-createnation").setExecutor(new NationCreateCommand(nationDataManager));
        this.getCommand("test-reload").setExecutor(new ReloadCommand(this));

        // Loads Other
    }


    @Override
    public void onDisable() { getLogger().info("Shutting Down... :p");}


}


