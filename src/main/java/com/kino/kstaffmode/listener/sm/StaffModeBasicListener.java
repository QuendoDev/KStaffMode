package com.kino.kstaffmode.listener.sm;

import com.kino.kstaffmode.KStaffMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

@SuppressWarnings("deprecation")
public class StaffModeBasicListener implements Listener {

    private KStaffMode plugin;
    public StaffModeBasicListener (KStaffMode plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e){
        if(e.getEntity() !=null) {
            if(e.getEntity() instanceof Player) {
                if (plugin.getStaffModeManager().getInStaffMode().contains(e.getDamager().getUniqueId())) {
                    e.setCancelled(true);
                }
                if (plugin.getStaffModeManager().getInStaffMode().contains(e.getEntity().getUniqueId())) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (plugin.getStaffModeManager().getInStaffMode().contains(e.getPlayer().getUniqueId())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak (BlockBreakEvent e) {
        if (plugin.getStaffModeManager().getInStaffMode().contains(e.getPlayer().getUniqueId())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemPickUp(PlayerPickupItemEvent e) {
        if (plugin.getStaffModeManager().getInStaffMode().contains(e.getPlayer().getUniqueId())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        if (plugin.getStaffModeManager().getInStaffMode().contains(e.getPlayer().getUniqueId())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (plugin.getStaffModeManager().getInStaffMode().contains(p.getUniqueId())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getWhoClicked() instanceof Player){
            Player p = (Player) e.getWhoClicked();
            if (plugin.getStaffModeManager().getInStaffMode().contains(p.getUniqueId())) {
                if(e.getClickedInventory().equals(p.getInventory())){
                    e.setCancelled(true);
                }
            }
        }
    }
}
