package com.kino.kstaffmode.menus;

import com.kino.kore.utils.JavaUtils;
import com.kino.kore.utils.items.KMaterial;
import com.kino.kore.utils.items.builder.ItemBuilder;
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
                ItemStack decoration = buildItem("decoration", config.getString("stafflist.inStaffModeMenu.decoration.name"), config.getInt("stafflist.inStaffModeMenu.decoration.amount"));

                for(int i = 45;i<54;i++) {
                    this.stafflistsm.setItem(i, decoration);
                }
            }
            int pages = totalPages();

            int slot = 0;

            for (int i = 45 * (page - 1 ); i < staff.size(); i++) {
                int finalI = i;
                List<String> lore = config.getStringList("stafflist.inStaffModeMenu.heads.lore");
                lore.replaceAll(
                        line -> line.replace("<player>", Bukkit.getPlayer(staff.get(finalI)).getName()));
                ItemStack head = ItemBuilder.newSkullBuilder(KMaterial.PLAYER_HEAD.name(), config.getInt("stafflist.inStaffModeMenu.heads.amount"))
                        .owner(Bukkit.getPlayer(staff.get(i)).getName())
                        .name(config.getString("stafflist.inStaffModeMenu.heads.name").replace("<player>", Bukkit.getPlayer(staff.get(i)).getName()))
                        .lore(lore).build();
                stafflistsm.setItem(slot, head);

                slot++;

                if(slot > 44) {
                    break;
                }
            }

            if(pages > page) {
                ItemStack next = buildItem("nextPage" ,config.getString("stafflist.inStaffModeMenu.nextPage.name"), config.getInt("stafflist.inStaffModeMenu.nextPage.amount"));
                stafflistsm.setItem(53, next);
            }

            if (page > 1) {
                ItemStack prev = buildItem("previousPage", config.getString("stafflist.inStaffModeMenu.previousPage.name"), config.getInt("stafflist.inStaffModeMenu.previousPage.amount"));
                stafflistsm.setItem(45, prev);
            }

            ItemStack pageItem = buildItem("pageItem", config.getString("stafflist.inStaffModeMenu.pageItem.name").replace("<page>", page + ""), page);
            stafflistsm.setItem(49, pageItem);

            p.openInventory(stafflistsm);

            menuManager.addPlayerInventory(new PlayerInventory(p, page));
        }
    }

    public int totalPages() {
        return this.staff.size() % 45 == 0 ? (staff.size()/45) : (staff.size()/45) + 1;
    }

    private ItemStack buildItem (String key, String name, int amount) {
        return config.getString("stafflist.inStaffModeMenu." + key + ".skull.type").equalsIgnoreCase("OWNER") ?
                ItemBuilder.newSkullBuilder(KMaterial.PLAYER_HEAD.name(), amount)
                        .owner(config.getString("stafflist.inStaffModeMenu." + key + ".skull.owner"))
                        .name(name)
                        .lore(config.getStringList("stafflist.inStaffModeMenu" + key + ".lore")).build()
                : config.getString("stafflist.inStaffModeMenu." + key + ".skull.type").equalsIgnoreCase("URL") ?

                ItemBuilder.newSkullBuilder(KMaterial.PLAYER_HEAD.name(), amount)
                        .url(config.getString("stafflist.inStaffModeMenu." + key + ".skull.owner"))
                        .name(name)
                        .lore(config.getStringList("stafflist.inStaffModeMenu." + key + ".lore")).build()

                : ItemBuilder.newBuilder(config.getString("stafflist.inStaffModeMenu." + key + ".id"), amount)
                .name(name)
                .lore(config.getStringList("stafflist.inStaffModeMenu." + key + ".lore")).build();
    }
}
