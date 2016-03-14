package org.stevenw.names.commands;

import ninja.amp.ampmenus.menus.ItemMenu;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.stevenw.names.menu.items.ColorItem;
import org.stevenw.names.menu.items.ColorResetItem;
import org.stevenw.names.menu.items.ItemLoader;
import org.stevenw.names.sColoredNames;

import java.util.Set;

public class Color implements CommandExecutor{
    private sColoredNames plugin;

    public Color(sColoredNames plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String tag, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("Must be a player!");
            return false;
        }
        Player player = (Player) sender;
        Set<String> colors = plugin.getConfig().getConfigurationSection("colors").getKeys(false);

        ItemMenu mainMenu = new ItemMenu("Name Colours", ItemMenu.Size.fit(colors.size()), plugin);
        Integer slot = 0;

        for (String colorName : colors) {
            String itemname = null;
            ChatColor color = ChatColor.valueOf(colorName);
            for (String item : plugin.getConfig().getConfigurationSection("colors." + colorName + ".item").getKeys(false)) {
                itemname = item;
            }
            ItemStack icon = ItemLoader.loadItem(plugin, "colors." + colorName + ".item", itemname);
            String colorNameFormatted = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("colors." + colorName + ".name", colorName));
            ColorItem coloritem = new ColorItem(plugin, color, colorNameFormatted, plugin.getConfig().getString("colors." + colorName + ".permission"), icon.getItemMeta().getDisplayName(), icon, icon.getItemMeta().getLore().toArray(new String[0]));

            mainMenu.setItem(slot, coloritem);
            slot = slot + 1;
        }

        //reset item
        mainMenu.setItem(slot, new ColorResetItem(plugin));


        mainMenu.open(player);
        return true;
    }
}
