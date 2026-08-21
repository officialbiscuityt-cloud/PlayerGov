package org.github.officialbiscuityt.playerGov.Managers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class NationDataManager {

    private final File file;
    private FileConfiguration data;

    public NationDataManager(JavaPlugin plugin) {

        File dataFolder = new File(plugin.getDataFolder(), "data");

        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        file = new File(dataFolder, "nations.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        reload();
    }

    public void createNation(String name, String owner, String ownerUUID) {

        data.set("nations." + name + ".name", name);
        data.set("nations." + name + ".owner", owner);
        data.set("nations." + name + ".ownerUUID", ownerUUID);

        save();
    }
    public void deleteNation(String name) {
        data.set("nations." + name, null);
        save();
    }
    public void transferNation(String name, String newOwner, String newOwnerUUID) {
        data.set("nations." + name + ".owner", newOwner);
        data.set("nations." + name + ".ownerUUID", newOwnerUUID);
        save();
    }

    public String getName(String name) {
        return data.getString("nations." + name + ".name");
    }

    public String getOwner(String name) {
        return data.getString("nations." + name + ".owner");
    }

    public String getOwnerUUID(String name) {
        return data.getString("nations." + name + ".ownerUUID");
    }

    public boolean exists(String name) {
        return data.contains("nations." + name);
    }


    public void save() {
        try {
            data.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        data = YamlConfiguration.loadConfiguration(file);
    }
}