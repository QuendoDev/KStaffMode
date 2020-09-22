package com.kino.kstaffmode.menus;

import com.kino.kore.utils.items.KMaterial;
import com.kino.kore.utils.items.builder.ItemBuilder;
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
                ItemStack decoration = buildItem("decoration");
                for (int i = 0; i < (config.getInt("stafflist.main.size")) ; i++) {
                    stafflistmain.setItem(i, decoration);
                }

            }

            if(config.getBoolean("stafflist.main.inStaffMode.enabled")) {
                ItemStack staffMode = buildItem("inStaffMode");
                this.stafflistmain.setItem(config.getInt("stafflist.main.inStaffMode.slot"), staffMode);
            }

            if(config.getBoolean("stafflist.main.withoutStaffMode.enabled")) {
                ItemStack staffPlaying = buildItem("withoutStaffMode");
                this.stafflistmain.setItem(config.getInt("stafflist.main.withoutStaffMode.slot"), staffPlaying);
            }

            p.openInventory(this.stafflistmain);
        }
    }

    private ItemStack buildItem (String key) {
        return config.getString("stafflist.main." + key + ".skull.type").equalsIgnoreCase("OWNER") ?
                ItemBuilder.newSkullBuilder(KMaterial.PLAYER_HEAD.parseMaterial(true), config.getInt("stafflist.main." + key + ".amount"), (byte) 3)
                        .owner(config.getString("stafflist.main." + key + ".skull.owner"))
                        .name(config.getString("stafflist.main." + key + ".name"))
                        .lore(config.getStringList("stafflist.main." + key + ".lore")).build()
                : config.getString("stafflist.main." + key + ".skull.type").equalsIgnoreCase("URL") ?

                ItemBuilder.newSkullBuilder(KMaterial.PLAYER_HEAD.parseMaterial(true), config.getInt("stafflist.main." + key + ".amount"), (byte) 3)
                        .url(config.getString("stafflist.main." + key + ".skull.url"))
                        .name(config.getString("stafflist.main." + key + ".name"))
                        .lore(config.getStringList("stafflist.main." + key + ".lore")).build()

                : ItemBuilder.newBuilder(config.getString("stafflist.main." + key + ".id"), config.getInt("stafflist.main." + key + ".amount"))
                .name(config.getString("stafflist.main." + key + ".name"))
                .lore(config.getStringList("stafflist.main." + key + ".lore")).build();
    }


}
