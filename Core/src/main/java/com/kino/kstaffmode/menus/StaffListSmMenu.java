package com.kino.kstaffmode.menus;

import com.kino.kore.utils.JavaUtils;
import com.kino.kore.utils.items.ItemBuilder;
import com.kino.kstaffmode.KStaffMode;
import com.kino.kstaffmode.managers.menus.MenuManager;
import com.kino.kstaffmode.managers.menus.PlayerInventory;
import com.kino.kstaffmode.managers.staffmode.StaffModeManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StaffListSmMenu {

    
    private List<UUID> staff;
    public Inventory stafflistsm;
    private FileConfiguration config;
    private StaffModeManager staffModeManager;
    private MenuManager menuManager;
    
    public StaffListSmMenu (FileConfiguration config, StaffModeManager staffModeManager, MenuManager menuManager) {
        this.config = config;
        this.menuManager = menuManager;
        this.staffModeManager = staffModeManager;
        this.staff = new ArrayList<>();
        this.stafflistsm = Bukkit.createInventory(null, 54, ChatColor.translateAlternateColorCodes('&', config.getString("stafflist.inStaffModeMenu.title")));
    }

    public void open (Player p, int page) {
        if(p.hasPermission("kstaffmode.items.stafflist.staffmode")) {
            for(Player pl : Bukkit.getOnlinePlayers()){
                if(pl.hasPermission("kstaffmode.staff")) {
                    if (staffModeManager.getInStaffMode().contains(pl.getUniqueId())) {
                        if(!staff.contains(pl.getUniqueId())) {
                            staff.add(pl.getUniqueId());
                        }
                    }
                }
            }

            if (config.getBoolean("stafflist.inStaffModeMenu.decoration.enabled")) {
                ItemStack decoration = ItemBuilder.createItem(config.getInt("stafflist.inStaffModeMenu.decoration.id"),
                        config.getInt("stafflist.inStaffModeMenu.decoration.amount"),
                        (short) config.getInt("stafflist.inStaffModeMenu.decoration.data"),
                        config.getString("stafflist.inStaffModeMenu.decoration.name"),
                        config.getStringList("stafflist.inStaffModeMenu.decoration.lore"));

                for(int i = 45;i<54;i++) {
                    this.stafflistsm.setItem(i, decoration);
                }
            }
            int pages = totalPages();

            int slot = 0;

            for (int i = 45 * (page - 1 ); i < staff.size(); i++) {
                ItemStack head = ItemBuilder.createSkull(config.getInt("stafflist.inStaffModeMenu.heads.amount"),
                        config.getString("stafflist.inStaffModeMenu.heads.name").replace("<player>", Bukkit.getPlayer(staff.get(i)).getName()),
                        JavaUtils.replaceAll(config.getStringList("stafflist.inStaffModeMenu.heads.lore"), "<player>", Bukkit.getPlayer(staff.get(i)).getName()),
                        Bukkit.getPlayer(staff.get(i)).getName()
                        );
                stafflistsm.setItem(slot, head);

                slot++;

                if(slot > 44) {
                    break;
                }
            }

            if(pages > page) {
                ItemStack next = ItemBuilder.createItem(config.getInt("stafflist.inStaffModeMenu.nextPage.id"),
                        config.getInt("stafflist.inStaffModeMenu.nextPage.amount"),
                        (short) config.getInt("stafflist.inStaffModeMenu.nextPage.data"),
                        config.getString("stafflist.inStaffModeMenu.nextPage.name"),
                        config.getStringList("stafflist.inStaffModeMenu.nextPage.lore"));
                stafflistsm.setItem(53, next);
            }

            if (page > 1) {
                ItemStack prev = ItemBuilder.createItem(config.getInt("stafflist.inStaffModeMenu.previousPage.id"),
                        config.getInt("stafflist.inStaffModeMenu.previousPage.amount"),
                        (short) config.getInt("stafflist.inStaffModeMenu.previousPage.data"),
                        config.getString("stafflist.inStaffModeMenu.previousPage.name"),
                        config.getStringList("stafflist.inStaffModeMenu.previousPage.lore"));
                stafflistsm.setItem(45, prev);
            }

            ItemStack pageItem = ItemBuilder.createItem(config.getInt("stafflist.inStaffModeMenu.pageItem.id"),
                    page,
                    (short) config.getInt("stafflist.inStaffModeMenu.pageItem.data"),
                    config.getString("stafflist.inStaffModeMenu.pageItem.name").replace("<page>", page + ""),
                    config.getStringList("stafflist.inStaffModeMenu.pageItem.lore"));
            stafflistsm.setItem(49, pageItem);

            p.openInventory(stafflistsm);

            menuManager.addPlayerInventory(new PlayerInventory(p, page));
        }
    }

    public int totalPages() {
        return this.staff.size() % 45 == 0 ? (staff.size()/45) : (staff.size()/45) + 1;
    }
}
