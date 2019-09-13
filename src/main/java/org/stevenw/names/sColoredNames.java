package org.stevenw.names;


import ninja.amp.ampmenus.MenuListener;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.stevenw.names.commands.Color;
import org.stevenw.names.listeners.PlayerListener;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class sColoredNames extends JavaPlugin {
    private PlayerData playerData;
    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        MenuListener.getInstance().register(this);
        playerData = new PlayerData(this);
        this.getCommand("color").setExecutor(new Color(this));
        this.getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
    }
    public PlayerData getPlayerData() {
        return this.playerData;
    }
    public long getCooldown() {
        return this.getConfig().getLong("cooldown") * 1000;
    }
    public List<String> getNoPermissionMsg(String color) {
       // this.getLogger().info(color);
        if(this.getConfig().contains("colors." + color + ".no-permission") || !this.getConfig().getStringList("colors." + color + ".no-permission").isEmpty()) {
            return this.getConfig().getStringList("colors." + color + ".no-permission");
        } else {
            return this.getConfig().getStringList("no-permission");
        }
    }
    public void setChatColor(Player player, ChatColor color) {
        if(color == null) {
            player.setDisplayName(player.getName());
        } else {
            player.setDisplayName(color + player.getName());
        }
    }
    public void setTabColor(Player player, ChatColor color) {
        player.setPlayerListName(player.getDisplayName().length() > 15 ? player.getDisplayName().substring(0, 16) : player.getDisplayName());
    }
    public static String getDurationBreakdown(long millis) {
        if (millis < 0) {
            throw new IllegalArgumentException("Duration must be greater than zero!");
        }

        long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        StringBuilder sb = new StringBuilder(64);
        sb.append(days);
        sb.append(" Days ");
        sb.append(hours);
        sb.append(" Hours ");
        sb.append(minutes);
        sb.append(" Minutes ");
        sb.append(seconds);
        sb.append(" Seconds");

        return (sb.toString());
    }

}
