package org.github.officialbiscuityt.playerGov;

// Imports
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.github.officialbiscuityt.playerGov.commands.playergov.reload;

// Clase defines
public class PlayerGov extends JavaPlugin{
private static PlayerGov instance;


    @Override
    public void onEnable() {
        instance = this;
        // Prints PlayerGov Welcoming Statement
        getLogger().info("Enjoy PlayerGov! :)");

        Bukkit.getPluginManager().registerEvents(new TestListener(), this);
    }

    @Override
    public void onDisable() { System.out.println("Shutting Down... :p");}

    public static PlayerGov getinstance() {return instance;}

    public void loadConfigurationValues() {
    }
}
