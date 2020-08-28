package com.kino.kstaffmode.menus;

import com.kino.kore.utils.items.ItemBuilder;
import com.kino.kstaffmode.KStaffMode;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("deprecation")
public class StaffListMainMenu {

    private KStaffMode plugin;
    public Inventory stafflistmain;

    public StaffListMainMenu (KStaffMode plugin) {
        this.plugin = plugin;
        this.stafflistmain = Bukkit.createInventory(null, plugin.getConfig().getInt("stafflist.main.size"), ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("stafflist.main.title")));
    }

    public void open (Player p) {
        if(p.hasPermission("kstaffmode.useitems") && p.hasPermission("kstaffmode.items.stafflist.main"))  {

            if (plugin.getConfig().getBoolean("stafflist.main.decoration.enabled")) {
                ItemStack decoration = ItemBuilder.createItem(plugin.getConfig().getInt("stafflist.main.decoration.id"),
                        plugin.getConfig().getInt("stafflist.main.decoration.amount"),
                        (short) plugin.getConfig().getInt("stafflist.main.decoration.data"),
                        plugin.getConfig().getString("stafflist.main.decoration.name"),
                        plugin.getConfig().getStringList("stafflist.main.decoration.lore"));
                for (int i = 0; i < (plugin.getConfig().getInt("stafflist.main.size")) ; i++) {
                    stafflistmain.setItem(i, decoration);
                }

            }

            if(plugin.getConfig().getBoolean("stafflist.main.inStaffMode.enabled")) {
                ItemStack staffMode = ItemBuilder.createItem(plugin.getConfig().getInt("stafflist.main.inStaffMode.id"),
                        plugin.getConfig().getInt("stafflist.main.inStaffMode.amount"),
                        (short) plugin.getConfig().getInt("stafflist.main.inStaffMode.data"),
                        plugin.getConfig().getString("stafflist.main.inStaffMode.name"),
                        plugin.getConfig().getStringList("stafflist.main.inStaffMode.lore"));
                this.stafflistmain.setItem(plugin.getConfig().getInt("stafflist.main.inStaffMode.slot"), staffMode);
            }

            if(plugin.getConfig().getBoolean("stafflist.main.withoutStaffMode.enabled")) {
                ItemStack staffPlaying = ItemBuilder.createItem(plugin.getConfig().getInt("stafflist.main.withoutStaffMode.id"),
                        plugin.getConfig().getInt("stafflist.main.withoutStaffMode.amount"),
                        (short) plugin.getConfig().getInt("stafflist.main.withoutStaffMode.data"),
                        plugin.getConfig().getString("stafflist.main.inStaffMode.name"),
                        plugin.getConfig().getStringList("stafflist.main.withoutStaffMode.lore"));
                this.stafflistmain.setItem(plugin.getConfig().getInt("stafflist.main.withoutStaffMode.slot"), staffPlaying);
            }

            p.openInventory(this.stafflistmain);
        }
    }


}
