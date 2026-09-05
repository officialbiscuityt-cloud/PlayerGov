package org.github.officialbiscuityt.playerGov.Managers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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


    public void createNation(String name, String id, String owner, String ownerUUID) {
        data.set("nations." + id + ".name", name);
        data.set("nations." + id + ".id", id);
        data.set("nations." + id + ".owner", owner);
        data.set("nations." + id + ".ownerUUID", ownerUUID);

        save();
    }
    public void deleteNation(String id) {
        data.set("nations." + id, null);
        save();
    }
    public void transferNation(String id, String newOwner, String newOwnerUUID) {
        data.set("nations." + id + ".owner", newOwner);
        data.set("nations." + id + ".ownerUUID", newOwnerUUID);
        save();
    }

    public String getid(String name) {
        if (data.getConfigurationSection("nations") == null) {
            return null;
        }
        for (String id : data.getConfigurationSection("nations").getKeys(false)) {

            String nationName = data.getString("nations." + id + ".name");

            if (nationName != null && nationName.equalsIgnoreCase(name)) {
                return id;
            }
        }

        return null;
    }

    public List<String> getOwnedNations(String ownerUUID) {
        List<String> ownedNations = new ArrayList<>();

        if (data.getConfigurationSection("nations") == null) {
            return ownedNations;
        }
        for (String id : data.getConfigurationSection("nations").getKeys(false)) {
            String savedOwnerUUID = data.getString("nations." + id + ".ownerUUID");
            if (ownerUUID.equals(savedOwnerUUID)) {
                String nationName = data.getString("nations." + id + ".name");
                if (nationName != null) {
                    ownedNations.add(nationName);
                }
            }
        }

        return ownedNations;
    }

    public String getName(String id) {
        return data.getString("nations." + id + ".name");
    }

    public String getOwner(String id) {
        return data.getString("nations." + id + ".owner");
    }

    public String getOwnerUUID(String id) {
        return data.getString("nations." + id + ".ownerUUID");
    }

    public boolean exists(String id) {
        return data.contains("nations." + id);
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