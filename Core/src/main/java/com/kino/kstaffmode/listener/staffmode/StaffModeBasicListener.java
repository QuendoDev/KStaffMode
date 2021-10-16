package com.kino.kstaffmode.listener.staffmode;

import com.kino.kstaffmode.managers.staffmode.StaffModeManager;
import lombok.AllArgsConstructor;
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

@AllArgsConstructor
public class StaffModeBasicListener implements Listener {

    private StaffModeManager staffModeManager;

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e){
        if(e.getEntity() !=null) {
            if(e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
                if (staffModeManager.getInStaffMode().contains(e.getDamager().getUniqueId()) || staffModeManager.isFrozen((Player) e.getDamager())) {
                    e.setCancelled(true);
                }
                if (staffModeManager.getInStaffMode().contains(e.getEntity().getUniqueId()) || staffModeManager.isFrozen((Player) e.getEntity())) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (staffModeManager.getInStaffMode().contains(e.getPlayer().getUniqueId()) || staffModeManager.isFrozen(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak (BlockBreakEvent e) {
        if (staffModeManager.getInStaffMode().contains(e.getPlayer().getUniqueId()) || staffModeManager.isFrozen(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemPickUp(PlayerPickupItemEvent e) {
        if (staffModeManager.getInStaffMode().contains(e.getPlayer().getUniqueId()) || staffModeManager.isFrozen(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        if (staffModeManager.getInStaffMode().contains(e.getPlayer().getUniqueId())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (staffModeManager.getInStaffMode().contains(p.getUniqueId()) || staffModeManager.isFrozen(p)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getWhoClicked() instanceof Player){
            Player p = (Player) e.getWhoClicked();
            if (staffModeManager.getInStaffMode().contains(p.getUniqueId())) {
                if(e.getClickedInventory() !=null) {
                    if (e.getClickedInventory().equals(p.getInventory())) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}
