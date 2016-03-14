package org.stevenw.names.menu.items;

import ninja.amp.ampmenus.events.ItemClickEvent;
import ninja.amp.ampmenus.items.MenuItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.stevenw.names.sColoredNames;

public class ColorResetItem extends MenuItem {
    private sColoredNames plugin;
    public ColorResetItem(sColoredNames plugin) {
        super("Reset", new ItemStack(Material.GLASS), "Reset to your default color");
        this.plugin = plugin;
    }

    @Override
    @EventHandler
    public void onItemClick(ItemClickEvent event) {
        //event.getPlayer().setDisplayName(event.getPlayer().getName());
        plugin.setChatColor(event.getPlayer(), null);
        plugin.setTabColor(event.getPlayer(), null);
        event.getPlayer().sendMessage(ChatColor.YELLOW + "Your name color has been reset!");
        event.setWillClose(true);
    }
}
