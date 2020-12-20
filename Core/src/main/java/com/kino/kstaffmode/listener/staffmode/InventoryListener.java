package com.kino.kstaffmode.listener.staffmode;

import com.kino.kore.utils.items.KMaterial;
import com.kino.kstaffmode.KStaffMode;
import com.kino.kstaffmode.factory.UtilsFactory;
import com.kino.kstaffmode.managers.menus.MenuManager;
import com.kino.kstaffmode.managers.menus.PlayerInventory;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

@SuppressWarnings("deprecation")
@AllArgsConstructor
public class InventoryListener implements Listener {

    private FileConfiguration config;
    private MenuManager menuManager;

    @EventHandler
    public void onClick (InventoryClickEvent e) {
        if(e.getClickedInventory() !=null) {
            String invName = UtilsFactory.getInventoryName(e);

            //////////////////MAIN MENU////////////////////////
            if (ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', config.getString("stafflist.main.title"))).equals(ChatColor.stripColor(invName))) {

                if (checks(e)) {
                    e.setCancelled(true);
                } else {
                    Player p = (Player) e.getWhoClicked();
                    e.setCancelled(true);
                    if (e.getClickedInventory().equals(p.getOpenInventory().getTopInventory())) {

                        if (e.getCurrentItem().getType().equals(KMaterial.getMaterial(config.getString("stafflist.main.inStaffMode.id")))) {
                            if (p.hasPermission("kstaffmode.menus.main.open.instaffmode")) {
                                menuManager.getStaffListSmMenu().open(p, 1);
                            }
                        }

                        if (e.getCurrentItem().getType().equals(KMaterial.getMaterial(config.getString("stafflist.main.withoutStaffMode.id")))) {
                            if (p.hasPermission("kstaffmode.menus.main.open.playing")) {
                                menuManager.getStaffListPlayingMenu().open(p, 1);
                            }
                        }
                    }
                }

            }

            //////////////////STAFF MENU////////////////////////
            if (ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', config.getString("stafflist.inStaffModeMenu.title"))).equals(ChatColor.stripColor(invName))) {

                if (checks(e)) {
                    e.setCancelled(true);
                } else {
                    Player p = (Player) e.getWhoClicked();
                    e.setCancelled(true);
                    if (e.getClickedInventory().equals(p.getOpenInventory().getTopInventory())) {
                        PlayerInventory playerInventory = menuManager.getPlayerInventory(p.getName());

                        if (playerInventory != null) {
                            int page = playerInventory.getPage();
                            int slot = e.getSlot();

                            if (slot == 53 && e.getCurrentItem().getType().equals(KMaterial.getMaterial(config.getString("stafflist.inStaffModeMenu.nextPage.id")))) {
                                int newPage = page + 1;

                                menuManager.getStaffListSmMenu().open(p, newPage);
                            }

                            if (slot == 45 && e.getCurrentItem().getType().equals(KMaterial.getMaterial(config.getString("stafflist.inStaffModeMenu.previousPage.id")))) {
                                int newPage = page - 1;

                                menuManager.getStaffListSmMenu().open(p, newPage);
                            }

                            if (e.getCurrentItem().getType().equals(Material.getMaterial(397))) {
                                if (p.hasPermission("kstaffmode.menus.instaffmode.teleport")) {
                                    ItemStack i = e.getCurrentItem();
                                    SkullMeta m = (SkullMeta) i.getItemMeta();
                                    p.teleport(Bukkit.getPlayer(m.getOwner()));
                                }
                            }
                        }
                    }
                }

            }

            //////////////////STAFFPLAYING MENU////////////////////////
            if (ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', config.getString("stafflist.staffPlaying.title"))).equals(ChatColor.stripColor(invName))) {

                if (checks(e)) {
                    e.setCancelled(true);
                } else {
                    Player p = (Player) e.getWhoClicked();
                    e.setCancelled(true);
                    if (e.getClickedInventory().equals(p.getOpenInventory().getTopInventory())) {
                        PlayerInventory playerInventory = menuManager.getPlayerInventory(p.getName());

                        if (playerInventory != null) {
                            int page = playerInventory.getPage();
                            int slot = e.getSlot();

                            if (slot == 53 && e.getCurrentItem().getType().equals(KMaterial.getMaterial(config.getString("stafflist.staffPlaying.nextPage.id")))) {
                                int newPage = page + 1;

                                menuManager.getStaffListPlayingMenu().open(p, newPage);
                            }

                            if (slot == 45 && e.getCurrentItem().getType().equals(KMaterial.getMaterial(config.getString("stafflist.staffPlaying.previousPage.id")))) {
                                int newPage = page - 1;

                                menuManager.getStaffListPlayingMenu().open(p, newPage);
                            }

                            if (e.getCurrentItem().getType().equals(Material.getMaterial(397))) {
                                if (p.hasPermission("kstaffmode.menus.playing.teleport")) {
                                    ItemStack i = e.getCurrentItem();
                                    SkullMeta m = (SkullMeta) i.getItemMeta();
                                    p.teleport(Bukkit.getPlayer(m.getOwner()));
                                }
                            }
                        }
                    }
                }

            }

            //////////////////INVSEE MENU////////////////////////
            if (ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', config.getString("inspect.title"))).equals(ChatColor.stripColor(invName))) {
                e.setCancelled(true);
            }
        }
    }

    private boolean checks (InventoryClickEvent e) {
        return e.getCurrentItem() == null || e.getSlotType() == null || e.getCurrentItem().getType().equals(Material.AIR);
    }

    @EventHandler
    public void onClose (InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        menuManager.removePlayerInventory(p.getName());
    }


}
