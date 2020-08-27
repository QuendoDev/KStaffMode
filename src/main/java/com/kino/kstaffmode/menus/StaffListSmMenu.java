package com.kino.kstaffmode.menus;

import com.kino.kore.utils.JavaUtils;
import com.kino.kore.utils.items.ItemBuilder;
import com.kino.kstaffmode.KStaffMode;
import com.kino.kstaffmode.managers.menus.PlayerInventory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StaffListSmMenu {

    private KStaffMode plugin;
    private List<UUID> staff;
    public Inventory stafflistsm;

    public StaffListSmMenu (KStaffMode plugin) {
        this.plugin = plugin;
        this.staff = new ArrayList<>();
        this.stafflistsm = Bukkit.createInventory(null, 54, ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("stafflist.inStaffModeMenu.title")));
    }

    public void open (Player p, int page) {
        if(p.hasPermission("kstaffmode.items.stafflist.staffmode")) {
            for(Player pl : Bukkit.getOnlinePlayers()){
                if(pl.hasPermission("kstaffmode.staff")) {
                    if (plugin.getStaffModeManager().getInStaffMode().contains(pl.getUniqueId())) {
                        if(!staff.contains(pl.getUniqueId())) {
                            staff.add(pl.getUniqueId());
                        }
                    }
                }
            }

            if (plugin.getConfig().getBoolean("stafflist.inStaffModeMenu.decoration.enabled")) {
                ItemStack decoration = ItemBuilder.createItem(plugin.getConfig().getInt("stafflist.inStaffModeMenu.decoration.id"),
                        plugin.getConfig().getInt("stafflist.inStaffModeMenu.decoration.amount"),
                        (short) plugin.getConfig().getInt("stafflist.inStaffModeMenu.decoration.data"),
                        plugin.getConfig().getString("stafflist.inStaffModeMenu.decoration.name"),
                        plugin.getConfig().getStringList("stafflist.inStaffModeMenu.decoration.lore"));

                for(int i = 45;i<54;i++) {
                    this.stafflistsm.setItem(i, decoration);
                }
            }
            int pages = totalPages();

            int slot = 0;

            for (int i = 45 * (page - 1 ); i < staff.size(); i++) {
                ItemStack head = ItemBuilder.createSkull(plugin.getConfig().getInt("stafflist.inStaffModeMenu.heads.amount"),
                        plugin.getConfig().getString("stafflist.inStaffModeMenu.heads.name").replace("<player>", Bukkit.getPlayer(staff.get(i)).getName()),
                        JavaUtils.replaceAll(plugin.getConfig().getStringList("stafflist.inStaffModeMenu.heads.lore"), "<player>", Bukkit.getPlayer(staff.get(i)).getName()),
                        Bukkit.getPlayer(staff.get(i)).getName()
                        );
                stafflistsm.setItem(slot, head);

                slot++;

                if(slot > 44) {
                    break;
                }
            }

            if(pages > page) {
                ItemStack next = ItemBuilder.createItem(plugin.getConfig().getInt("stafflist.inStaffModeMenu.nextPage.id"),
                        plugin.getConfig().getInt("stafflist.inStaffModeMenu.nextPage.amount"),
                        (short) plugin.getConfig().getInt("stafflist.inStaffModeMenu.nextPage.data"),
                        plugin.getConfig().getString("stafflist.inStaffModeMenu.nextPage.name"),
                        plugin.getConfig().getStringList("stafflist.inStaffModeMenu.nextPage.lore"));
                stafflistsm.setItem(53, next);
            }

            if (page > 1) {
                ItemStack prev = ItemBuilder.createItem(plugin.getConfig().getInt("stafflist.inStaffModeMenu.previousPage.id"),
                        plugin.getConfig().getInt("stafflist.inStaffModeMenu.previousPage.amount"),
                        (short) plugin.getConfig().getInt("stafflist.inStaffModeMenu.previousPage.data"),
                        plugin.getConfig().getString("stafflist.inStaffModeMenu.previousPage.name"),
                        plugin.getConfig().getStringList("stafflist.inStaffModeMenu.previousPage.lore"));
                stafflistsm.setItem(45, prev);
            }

            ItemStack pageItem = ItemBuilder.createItem(plugin.getConfig().getInt("stafflist.inStaffModeMenu.pageItem.id"),
                    page,
                    (short) plugin.getConfig().getInt("stafflist.inStaffModeMenu.pageItem.data"),
                    plugin.getConfig().getString("stafflist.inStaffModeMenu.pageItem.name").replace("<page>", page + ""),
                    plugin.getConfig().getStringList("stafflist.inStaffModeMenu.pageItem.lore"));
            stafflistsm.setItem(49, pageItem);

            p.openInventory(stafflistsm);

            plugin.getMenuManager().addPlayerInventory(new PlayerInventory(p, page));
        }
    }

    public int totalPages() {
        return this.staff.size() % 45 == 0 ? (staff.size()/45) : (staff.size()/45) + 1;
    }
}
