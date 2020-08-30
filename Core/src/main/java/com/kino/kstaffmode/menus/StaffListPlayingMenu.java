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

public class StaffListPlayingMenu {

    private List<UUID> playing;
    public Inventory stafflistplaying;
    private FileConfiguration config;
    private StaffModeManager staffModeManager;
    private MenuManager menuManager;

    public StaffListPlayingMenu (FileConfiguration config, StaffModeManager staffModeManager, MenuManager menuManager) {
        this.config = config;
        this.menuManager = menuManager;
        this.staffModeManager = staffModeManager;
        this.playing = new ArrayList<>();
        this.stafflistplaying = Bukkit.createInventory(null, 54, ChatColor.translateAlternateColorCodes('&', config.getString("stafflist.staffPlaying.title")));
    }

    public void open (Player p, int page) {
        if(p.hasPermission("kstaffmode.items.stafflist.playing")) {
            for(Player pl : Bukkit.getOnlinePlayers()){
                if(pl.hasPermission("kstaffmode.staff")) {
                    if (!staffModeManager.getInStaffMode().contains(pl.getUniqueId())) {
                        if(!playing.contains(pl.getUniqueId())) {
                            playing.add(pl.getUniqueId());
                        }
                    }
                }
            }
            if (config.getBoolean("stafflist.staffPlaying.decoration.enabled")) {
                ItemStack decoration = ItemBuilder.createItem(config.getInt("stafflist.staffPlaying.decoration.id"),
                        config.getInt("stafflist.staffPlaying.decoration.amount"),
                        (short) config.getInt("stafflist.staffPlaying.decoration.data"),
                        config.getString("stafflist.staffPlaying.decoration.name"),
                        config.getStringList("stafflist.staffPlaying.decoration.lore"));

                for(int i = 45;i<54;i++) {
                    this.stafflistplaying.setItem(i, decoration);
                }
            }
            int pages = totalPages();

            int slot = 0;

            for (int i = 45 * (page - 1 ); i < playing.size(); i++) {
                ItemStack head = ItemBuilder.createSkull(config.getInt("stafflist.staffPlaying.heads.amount"),
                        config.getString("stafflist.staffPlaying.heads.name").replace("<player>", Bukkit.getPlayer(playing.get(i)).getName()),
                        JavaUtils.replaceAll(config.getStringList("stafflist.staffPlaying.heads.lore"), "<player>", Bukkit.getPlayer(playing.get(i)).getName()),
                        Bukkit.getPlayer(playing.get(i)).getName()
                );
                stafflistplaying.setItem(slot, head);

                slot++;

                if(slot > 44) {
                    break;
                }
            }

            if(pages > page) {
                ItemStack next = ItemBuilder.createItem(config.getInt("stafflist.staffPlaying.nextPage.id"),
                        config.getInt("stafflist.staffPlaying.nextPage.amount"),
                        (short) config.getInt("stafflist.staffPlaying.nextPage.data"),
                        config.getString("stafflist.staffPlaying.nextPage.name"),
                        config.getStringList("stafflist.staffPlaying.nextPage.lore"));
                stafflistplaying.setItem(53, next);
            }

            if (page > 1) {
                ItemStack prev = ItemBuilder.createItem(config.getInt("stafflist.staffPlaying.previousPage.id"),
                        config.getInt("stafflist.staffPlaying.previousPage.amount"),
                        (short) config.getInt("stafflist.staffPlaying.previousPage.data"),
                        config.getString("stafflist.staffPlaying.previousPage.name"),
                        config.getStringList("stafflist.staffPlaying.previousPage.lore"));
                stafflistplaying.setItem(45, prev);
            }

            ItemStack pageItem = ItemBuilder.createItem(config.getInt("stafflist.staffPlaying.pageItem.id"),
                    page,
                    (short) config.getInt("stafflist.staffPlaying.pageItem.data"),
                    config.getString("stafflist.staffPlaying.pageItem.name").replace("<page>", page + ""),
                    config.getStringList("stafflist.staffPlaying.pageItem.lore"));
            stafflistplaying.setItem(49, pageItem);

            p.openInventory(stafflistplaying);

            menuManager.addPlayerInventory(new PlayerInventory(p, page));
        }
    }

    public int totalPages() {
        return this.playing.size() % 45 == 0 ? (playing.size()/45) : (playing.size()/45) + 1;
    }
}
