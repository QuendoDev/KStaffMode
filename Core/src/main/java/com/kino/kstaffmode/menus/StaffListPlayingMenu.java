package com.kino.kstaffmode.menus;

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
                ItemStack decoration = buildItem("decoration", config.getString("stafflist.staffPlaying.decoration.name"), config.getInt("stafflist.staffPlaying.decoration.amount"));

                for(int i = 45;i<54;i++) {
                    this.stafflistplaying.setItem(i, decoration);
                }
            }
            int pages = totalPages();

            int slot = 0;

            for (int i = 45 * (page - 1 ); i < playing.size(); i++) {
                int finalI = i;
                List<String> lore = config.getStringList("stafflist.staffPlaying.heads.lore");
                lore.replaceAll(
                        line -> line.replace("<player>", Bukkit.getPlayer(playing.get(finalI)).getName()));
                ItemStack head = ItemBuilder.newSkullBuilder(KMaterial.PLAYER_HEAD.parseMaterial(true), config.getInt("stafflist.staffPlaying.heads.amount"), (byte) 3)
                        .owner(Bukkit.getPlayer(playing.get(i)).getName())
                        .name(config.getString("stafflist.staffPlaying.heads.name").replace("<player>", Bukkit.getPlayer(playing.get(i)).getName()))
                        .lore(lore).build();
                stafflistplaying.setItem(slot, head);

                slot++;

                if(slot > 44) {
                    break;
                }
            }

            if(pages > page) {
                ItemStack next = buildItem("nextPage", config.getString("stafflist.staffPlaying.nextPage.name"), config.getInt("stafflist.staffPlaying.nextPage.amount"));
                stafflistplaying.setItem(53, next);
            }

            if (page > 1) {
                ItemStack prev = buildItem("previousPage", config.getString("stafflist.staffPlaying.previousPage.name"), config.getInt("stafflist.staffPlaying.previousPage.amount"));
                stafflistplaying.setItem(45, prev);
            }

            ItemStack pageItem = buildItem("pageItem", config.getString("stafflist.staffPlaying.pageItem.name").replace("<page>", page + ""), page);
            stafflistplaying.setItem(49, pageItem);

            p.openInventory(stafflistplaying);

            menuManager.addPlayerInventory(new PlayerInventory(p, page));
        }
    }

    public int totalPages() {
        return this.playing.size() % 45 == 0 ? (playing.size()/45) : (playing.size()/45) + 1;
    }


    private ItemStack buildItem (String key, String name, int amount) {
        return config.getString("stafflist.staffPlaying." + key + ".skull.type").equalsIgnoreCase("OWNER") ?
                ItemBuilder.newSkullBuilder(KMaterial.PLAYER_HEAD.parseMaterial(true), amount, (byte) 3)
                        .owner(config.getString("stafflist.staffPlaying." + key + ".skull.owner"))
                        .name(name)
                        .lore(config.getStringList("stafflist.staffPlaying" + key + ".lore")).build()
                : config.getString("stafflist.staffPlaying." + key + ".skull.type").equalsIgnoreCase("URL") ?

                ItemBuilder.newSkullBuilder(KMaterial.PLAYER_HEAD.parseMaterial(true), amount, (byte) 3)
                        .url(config.getString("stafflist.staffPlaying." + key + ".skull.url"))
                        .name(name)
                        .lore(config.getStringList("stafflist.staffPlaying." + key + ".lore")).build()

                : ItemBuilder.newBuilder(config.getString("stafflist.staffPlaying." + key + ".id"), amount)
                .name(name)
                .lore(config.getStringList("stafflist.staffPlaying." + key + ".lore")).build();
    }
}
