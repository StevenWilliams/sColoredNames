package org.stevenw.names.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.stevenw.names.sColoredNames;

public class PlayerListener implements Listener {
    private sColoredNames plugin;
    public PlayerListener(sColoredNames plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent e) {
        String lookupstring = "players." + e.getPlayer().getUniqueId() + ".color";
        if(plugin.getPlayerData().getPlayerData().getString(lookupstring) != null) {
            ChatColor color = ChatColor.valueOf(plugin.getPlayerData().getPlayerData().getString(lookupstring));
            if(e.getPlayer().hasPermission(plugin.getConfig().getString("colors." + plugin.getPlayerData().getPlayerData().getString(lookupstring) + ".permission"))){
                plugin.setChatColor(e.getPlayer(), color);
            }
            if(e.getPlayer().hasPermission(plugin.getConfig().getString("colors." + plugin.getPlayerData().getPlayerData().getString(lookupstring) + ".permission") + ".tablist")){
                plugin.setTabColor(e.getPlayer(), color);
            }
        }

    }
}
