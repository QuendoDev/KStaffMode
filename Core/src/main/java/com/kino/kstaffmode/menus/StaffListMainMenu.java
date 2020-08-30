package com.kino.kstaffmode.menus;

import com.kino.kore.utils.items.ItemBuilder;
import com.kino.kstaffmode.KStaffMode;
import com.kino.kstaffmode.managers.menus.MenuManager;
import com.kino.kstaffmode.managers.staffmode.StaffModeManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class StaffListMainMenu {

    
    public Inventory stafflistmain;
    private FileConfiguration config;

    public StaffListMainMenu (FileConfiguration config) {
        this.config = config;
        this.stafflistmain = Bukkit.createInventory(null, config.getInt("stafflist.main.size"), ChatColor.translateAlternateColorCodes('&', config.getString("stafflist.main.title")));
    }

    public void open (Player p) {
        if(p.hasPermission("kstaffmode.useitems") && p.hasPermission("kstaffmode.items.stafflist.main"))  {

            if (config.getBoolean("stafflist.main.decoration.enabled")) {
                ItemStack decoration = ItemBuilder.createItem(config.getInt("stafflist.main.decoration.id"),
                        config.getInt("stafflist.main.decoration.amount"),
                        (short) config.getInt("stafflist.main.decoration.data"),
                        config.getString("stafflist.main.decoration.name"),
                        config.getStringList("stafflist.main.decoration.lore"));
                for (int i = 0; i < (config.getInt("stafflist.main.size")) ; i++) {
                    stafflistmain.setItem(i, decoration);
                }

            }

            if(config.getBoolean("stafflist.main.inStaffMode.enabled")) {
                ItemStack staffMode = ItemBuilder.createItem(config.getInt("stafflist.main.inStaffMode.id"),
                        config.getInt("stafflist.main.inStaffMode.amount"),
                        (short) config.getInt("stafflist.main.inStaffMode.data"),
                        config.getString("stafflist.main.inStaffMode.name"),
                        config.getStringList("stafflist.main.inStaffMode.lore"));
                this.stafflistmain.setItem(config.getInt("stafflist.main.inStaffMode.slot"), staffMode);
            }

            if(config.getBoolean("stafflist.main.withoutStaffMode.enabled")) {
                ItemStack staffPlaying = ItemBuilder.createItem(config.getInt("stafflist.main.withoutStaffMode.id"),
                        config.getInt("stafflist.main.withoutStaffMode.amount"),
                        (short) config.getInt("stafflist.main.withoutStaffMode.data"),
                        config.getString("stafflist.main.inStaffMode.name"),
                        config.getStringList("stafflist.main.withoutStaffMode.lore"));
                this.stafflistmain.setItem(config.getInt("stafflist.main.withoutStaffMode.slot"), staffPlaying);
            }

            p.openInventory(this.stafflistmain);
        }
    }


}
