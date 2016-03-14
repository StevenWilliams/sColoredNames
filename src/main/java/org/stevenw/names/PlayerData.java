package org.stevenw.names;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.*;

public class PlayerData {
    private FileConfiguration playerData = null;
    private File playerDataFile = null;
    private sColoredNames plugin;

    public PlayerData(sColoredNames plugin) {
        this.plugin = plugin;
        reloadPlayerData();
    }

    public FileConfiguration getPlayerData() {
        if (playerData == null) {
            reloadPlayerData();
        }
        return playerData;
    }

    public void saveColor(Player player, ChatColor color) {
        this.getPlayerData().set("players." + player.getUniqueId() + ".color", color.name());
        this.getPlayerData().set("players." + player.getUniqueId() + ".lastchanged", System.currentTimeMillis());
        this.savePlayerData();
    }
    public long getLastChanged(Player player) {
        return this.getPlayerData().getLong("players." + player.getUniqueId() + ".lastchanged", 0);
    }

    public void reloadPlayerData() {
        Reader defConfigStream = null;
        try {
            if (playerDataFile == null) {
                playerDataFile = new File(plugin.getDataFolder(), "players.yml");
                if(!playerDataFile.exists()) {
                    playerDataFile.createNewFile();
                }
            }
            playerData = YamlConfiguration.loadConfiguration(playerDataFile);
            //playerData.createSection("players");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void savePlayerData() {
        if (playerData == null || playerDataFile == null) {
            return;
        }
        try {
            getPlayerData().save(playerDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
