package com.kino.kstaffmode.menus;

import com.kino.kstaffmode.KStaffMode;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;

public class StaffListSmMenu {

    private KStaffMode plugin;
    public Inventory stafflistsm;

    public StaffListSmMenu (KStaffMode plugin) {
        this.plugin = plugin;
        this.stafflistsm = Bukkit.createInventory(null, plugin.getConfig().getInt("stafflist.inStaffModMenu.size"), ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("stafflist.inStaffModMenu.title")));
    }
}
