package com.kino.kstaffmode.menus;

import com.kino.kstaffmode.KStaffMode;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;

public class StaffListPlayingMenu {

    private KStaffMode plugin;
    public Inventory stafflistplaying;

    public StaffListPlayingMenu (KStaffMode plugin) {
        this.plugin = plugin;
        this.stafflistplaying = Bukkit.createInventory(null, plugin.getConfig().getInt("stafflist.staffPlaying.size"), ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("stafflist.staffPlaying.title")));
    }
}
