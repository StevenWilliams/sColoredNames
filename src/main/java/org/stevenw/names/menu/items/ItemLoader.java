package org.stevenw.names.menu.items;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class ItemLoader {

    public static ItemStack loadItem(JavaPlugin plugin, String itempath, String item) {

        ItemStack loadedStack;

        Integer itemquantity = plugin.getConfig().getInt(itempath + "." + item + "." + "quantity", 1);

        //check if datavalues set
        short datavalueshort;
        if (plugin.getConfig().getString(itempath + "." + item + "." + "datavalue") != null) {
            String datavalue = plugin.getConfig().getString(itempath + "." + item + "." + "datavalue");
            datavalueshort = Short.parseShort(datavalue);
        } else {
            datavalueshort = 0;
        }

        loadedStack = new ItemStack(Material.matchMaterial(item), itemquantity, (short) datavalueshort);

        ItemMeta loadedStackMeta = loadedStack.getItemMeta();

        //lore
        loadedStackMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString(itempath + "." + item + ".displayName")));
        if (plugin.getConfig().getStringList(itempath + "." + item + ".lore") != null) {
            List<String> loreItemConfig = plugin.getConfig().getStringList(itempath + "." + item + ".lore");
            List<String> lore = new ArrayList();
            for (String loreItemConfigLine : loreItemConfig) {
                lore.add(formatMessage(loreItemConfigLine));
            }

            loadedStackMeta.setLore(lore);
        }


        if (plugin.getConfig().getConfigurationSection(itempath + "." + item + ".enchantments") != null) {
            Set<String> enchantItemConfig = plugin.getConfig().getConfigurationSection(itempath + "." + item + ".enchantments").getKeys(false);
            for (String enchantment : enchantItemConfig) {
                loadedStackMeta.addEnchant(Enchantment.getByName(enchantment), plugin.getConfig().getInt(itempath + "." + item + ".enchantments." + enchantment), true);
            }
        }

        loadedStack.setItemMeta(loadedStackMeta);
        return loadedStack;
    }

    private static List<String> formatMessage(List<String> msglist) {
        for (int i = 0; i < msglist.size(); i++) {
            msglist.set(i, formatMessage(msglist.get(i)));
        }
        return msglist;
    }

    private static String formatMessage(String msg) {
        return ChatColor.translateAlternateColorCodes('&' , msg);
    }
}