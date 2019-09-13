package org.stevenw.names.menu.items;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import ninja.amp.ampmenus.events.ItemClickEvent;
import ninja.amp.ampmenus.items.MenuItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.stevenw.names.sColoredNames;

public class ColorItem extends MenuItem {
    private sColoredNames plugin;
    private String colorName;
    private ChatColor color;
    private String permission;

    
    public ColorItem(sColoredNames plugin, ChatColor color, String colorName, String permission, String displayName, ItemStack icon, String[] lore) {
        super(displayName, icon, lore);
        this.plugin = plugin;
        this.permission = permission;
        this.color = color;
        this.colorName = colorName;
    }

    @Override
    @EventHandler
    public void onItemClick(ItemClickEvent event) {
        if(event.getPlayer().hasPermission(permission)) {
            if(canChangeColor(event.getPlayer()))
            {
                plugin.setChatColor(event.getPlayer(), color);
                if(event.getPlayer().hasPermission(permission + ".tablist"))
                {
                    plugin.setTabColor(event.getPlayer(), color);
                }
                plugin.getPlayerData().saveColor(event.getPlayer(), color);

                event.getPlayer().sendMessage("Your name is now " + colorName);
            } else {
                Long waitTime = (plugin.getPlayerData().getLastChanged(event.getPlayer()) + plugin.getCooldown() - System.currentTimeMillis());
                event.getPlayer().sendMessage(ChatColor.RED + "You can change your name color again in " + plugin.getDurationBreakdown(waitTime));
            }
        } else {
             List<String> noPermissionMsg = plugin.getNoPermissionMsg(color.name());
             for(String permLine : noPermissionMsg)
             {
                 event.getPlayer().sendMessage(ChatColor.RED + permLine);
             }
        }
        event.setWillClose(true);
        
    }

    @Override
    public ItemStack getFinalIcon(Player player) {
        ItemStack finalIcon = super.getFinalIcon(player);
        if (!player.hasPermission(permission)) {
            ItemMeta meta = finalIcon.getItemMeta();
            //plugin.getLogger().info("icon1" + color.toString());
            //plugin.getLogger().info("icon2" + color.name());

            List<String> lore = plugin.getNoPermissionMsg(color.name());
            meta.setLore(lore);
            finalIcon.setItemMeta(meta);
        }
        return finalIcon;
    }

    private boolean canChangeColor(Player player) {
        if (player.hasPermission("names.bypasscooldown")) {
            return true;
        }
        if (plugin.getPlayerData().getLastChanged(player) != 0) {
            Long lastchange = plugin.getPlayerData().getLastChanged(player);
            Long cooldown = plugin.getCooldown();
            return ((lastchange + cooldown) < System.currentTimeMillis());
        } else {
            return true;
        }
    }
}