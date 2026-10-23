package org.github.officialbiscuityt.playerGov.Managers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NationDataManager {

    // File Setup and exist check
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
    // Create | Delete | Transfer Nation

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

    // Add | Get | Delete Nation Type
    public void addNationType(String id, String type) {
        data.set("nations." + id + ".type", type);
        save();
    }

    public String getNationType(String id) {

        if (data.getConfigurationSection("nations") == null) {
            return null;
        }

        if (!data.contains("nations." + id)) {
            return null;
        }

        return data.getString("nations." + id + ".type" );
    }

    public void delNationType(String type, String id) {
        data.set("nations." + id + ".type", null);
        save();
    }

    // Add | Get | Delete Nation Type
    public void addNationDescription(String id, String description) {
        data.set("nations." + id + ".description", description);
        save();
    }
    public String getNationDescription(String id) {

        if (data.getConfigurationSection("nations") == null) {
            return null;
        }

        if (!data.contains("nations." + id)) {
            return null;
        }

        return data.getString("nations." + id + ".description");
    }

    public void delNationDescription(String id) {
data.set("nations." + id + ".description", null);
    }

    // Get Automatically created nationId from nation's name
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

    // Add | Check | Remove a nation's public status
    public Boolean addPublicNation(String id) {
        data.set("nations." + id + ".public", true);
        return null;
    }

    public Boolean checkIfPublicNation(String id) {
        if (data.getConfigurationSection("nations" + id) == null) {
            return null;
        }
                if (data.getBoolean("nations." + id + ".public")) {
                    return true;
                } else {
                    data.set("nations." + id + ".public", false);
                }
                return null;
    }

    public Boolean removePublicNation(String id) {
        data.set("nations." + id + ".public", false);
        return null;
    }

    // Gets a player's owned nations
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

    // Gets a player's owned nation ids
    public List<String> getOwnedNationsId(String ownerUUID) {
        List<String> ownedNations = new ArrayList<>();

        if (data.getConfigurationSection("nations") == null) {
            return ownedNations;
        }

        for (String id : data.getConfigurationSection("nations").getKeys(false)) {

            String savedOwnerUUID =
                    data.getString("nations." + id + ".ownerUUID");

            if (ownerUUID.equalsIgnoreCase(savedOwnerUUID)) {
                ownedNations.add(id);
            }
        }

        return ownedNations;
    }
    // Gets the amount of nation's a player owns
    public int getOwnedNationAmount(String ownerUUID) {
        List<String> ownedNations = getOwnedNations(ownerUUID);
        if (data.getConfigurationSection("nations") == null) {
        }
        int totalElements = ownedNations.size();
        return ownedNations.size();
    }
    // Gets a nation's name from id
    public String getName(String id) {
        return data.getString("nations." + id + ".name");
    }
    // Gets a nation's owner
    public String getOwner(String id) {
        return data.getString("nations." + id + ".owner");
    }
    // Gets a nation's ownerId
    public String getOwnerUUID(String id) {
        return data.getString("nations." + id + ".ownerUUID");
    }
    // Gets whether a nation exists
    public boolean exists(String id) {
        return data.contains("nations." + id);
    }

    // Saves the file data
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