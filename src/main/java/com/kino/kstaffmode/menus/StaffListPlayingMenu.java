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

public class StaffListPlayingMenu {

    private KStaffMode plugin;
    private List<UUID> playing;
    public Inventory stafflistplaying;

    public StaffListPlayingMenu (KStaffMode plugin) {
        this.plugin = plugin;
        this.playing = new ArrayList<>();
        this.stafflistplaying = Bukkit.createInventory(null, 54, ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("stafflist.staffPlaying.title")));
    }

    public void open (Player p, int page) {
        if(p.hasPermission("kstaffmode.items.stafflist.playing")) {
            for(Player pl : Bukkit.getOnlinePlayers()){
                if(pl.hasPermission("kstaffmode.staff")) {
                    if (!plugin.getStaffModeManager().getInStaffMode().contains(pl.getUniqueId())) {
                        if(!playing.contains(pl.getUniqueId())) {
                            playing.add(pl.getUniqueId());
                        }
                    }
                }
            }
            if (plugin.getConfig().getBoolean("stafflist.staffPlaying.decoration.enabled")) {
                ItemStack decoration = ItemBuilder.createItem(plugin.getConfig().getInt("stafflist.staffPlaying.decoration.id"),
                        plugin.getConfig().getInt("stafflist.staffPlaying.decoration.amount"),
                        (short) plugin.getConfig().getInt("stafflist.staffPlaying.decoration.data"),
                        plugin.getConfig().getString("stafflist.staffPlaying.decoration.name"),
                        plugin.getConfig().getStringList("stafflist.staffPlaying.decoration.lore"));

                for(int i = 45;i<54;i++) {
                    this.stafflistplaying.setItem(i, decoration);
                }
            }
            int pages = totalPages();

            int slot = 0;

            for (int i = 45 * (page - 1 ); i < playing.size(); i++) {
                ItemStack head = ItemBuilder.createSkull(plugin.getConfig().getInt("stafflist.staffPlaying.heads.amount"),
                        plugin.getConfig().getString("stafflist.staffPlaying.heads.name").replace("<player>", Bukkit.getPlayer(playing.get(i)).getName()),
                        JavaUtils.replaceAll(plugin.getConfig().getStringList("stafflist.staffPlaying.heads.lore"), "<player>", Bukkit.getPlayer(playing.get(i)).getName()),
                        Bukkit.getPlayer(playing.get(i)).getName()
                );
                stafflistplaying.setItem(slot, head);

                slot++;

                if(slot > 44) {
                    break;
                }
            }

            if(pages > page) {
                ItemStack next = ItemBuilder.createItem(plugin.getConfig().getInt("stafflist.staffPlaying.nextPage.id"),
                        plugin.getConfig().getInt("stafflist.staffPlaying.nextPage.amount"),
                        (short) plugin.getConfig().getInt("stafflist.staffPlaying.nextPage.data"),
                        plugin.getConfig().getString("stafflist.staffPlaying.nextPage.name"),
                        plugin.getConfig().getStringList("stafflist.staffPlaying.nextPage.lore"));
                stafflistplaying.setItem(53, next);
            }

            if (page > 1) {
                ItemStack prev = ItemBuilder.createItem(plugin.getConfig().getInt("stafflist.staffPlaying.previousPage.id"),
                        plugin.getConfig().getInt("stafflist.staffPlaying.previousPage.amount"),
                        (short) plugin.getConfig().getInt("stafflist.staffPlaying.previousPage.data"),
                        plugin.getConfig().getString("stafflist.staffPlaying.previousPage.name"),
                        plugin.getConfig().getStringList("stafflist.staffPlaying.previousPage.lore"));
                stafflistplaying.setItem(45, prev);
            }

            ItemStack pageItem = ItemBuilder.createItem(plugin.getConfig().getInt("stafflist.staffPlaying.pageItem.id"),
                    page,
                    (short) plugin.getConfig().getInt("stafflist.staffPlaying.pageItem.data"),
                    plugin.getConfig().getString("stafflist.staffPlaying.pageItem.name").replace("<page>", page + ""),
                    plugin.getConfig().getStringList("stafflist.staffPlaying.pageItem.lore"));
            stafflistplaying.setItem(49, pageItem);

            p.openInventory(stafflistplaying);

            plugin.getMenuManager().addPlayerInventory(new PlayerInventory(p, page));
        }
    }

    public int totalPages() {
        return this.playing.size() % 45 == 0 ? (playing.size()/45) : (playing.size()/45) + 1;
    }
}
