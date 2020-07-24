package com.kino.kstaffmode.menus;

import com.kino.kore.utils.items.ItemBuilder;
import com.kino.kstaffmode.KStaffMode;
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
        this.stafflistsm = Bukkit.createInventory(null, plugin.getConfig().getInt("stafflist.inStaffModMenu.size"), ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("stafflist.inStaffModMenu.title")));
    }

    public void openInventario (Player p, int page) {
        if(p.hasPermission("kstaffmode.items.stafflist.staffmode")) {
            for(Player pl : Bukkit.getOnlinePlayers()){
                if(pl.hasPermission("kstaffmode.staff")) {
                    if (plugin.getStaffModeManager().getInStaffMode().contains(pl.getUniqueId())) {
                        staff.add(pl.getUniqueId());
                    }
                }
            }
            if (plugin.getConfig().getBoolean("stafflist.inStaffModMenu.decoration.enabled")) {
                ItemStack decoration = ItemBuilder.createItem(plugin.getConfig().getInt("stafflist.inStaffModMenu.decoration.id"),
                        plugin.getConfig().getInt("stafflist.inStaffModMenu.decoration.amount"),
                        (short) plugin.getConfig().getInt("stafflist.inStaffModMenu.decoration.data"),
                        plugin.getConfig().getString("stafflist.inStaffModMenu.decoration.name"),
                        plugin.getConfig().getStringList("stafflist.inStaffModMenu.decoration.lore"));

                for(int i = 45;i<53;i++) {
                    this.stafflistsm.setItem(i, decoration);
                }

                int pages = totalsPages();

                int slot = 0;

                for (int i = 45 * (page - 1 ); i < staff.size(); i++) {
                    //TODO: SEE AJNEB
                }
            }
        }
    }

    public int totalsPages() {
        if(this.staff.size() % 45 == 0) {
            int pagina = (staff.size()/45);
            return pagina;
        } else {
            int pagina = (staff.size()/45) + 1;
            return pagina;
        }
    }
}
