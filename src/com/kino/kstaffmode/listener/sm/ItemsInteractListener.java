package com.kino.kstaffmode.listener.sm;

import com.kino.kore.utils.messages.MessageUtils;
import com.kino.kstaffmode.KStaffMode;
import com.kino.kstaffmode.events.items.*;
import com.kino.kstaffmode.events.util.ActionType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;

public class ItemsInteractListener implements Listener {

    private KStaffMode plugin;
    public ItemsInteractListener (KStaffMode plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onAction(PlayerInteractEvent e) {

        Player p = e.getPlayer();
        if(plugin.getStaffModeManager().getInStaffMode().contains(p.getUniqueId())) {
            if(p.hasPermission("kstaffmode.useitems")) {

                ////////////******NAVIGATOR******/////////////
                if (p.getItemInHand().getType() == Material.getMaterial(plugin.getConfig().getInt("staffItems.navigator.id")) && p.hasPermission("kstaffmode.items.navigator")) {
                    if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
                        plugin.getServer().getPluginManager().callEvent(new NavigatorInteractEvent(p, ActionType.LEFT_CLICK, p.getLocation()));
                    }

                    if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                        if (p.getTargetBlock((Set<Material>) null, 25) != null) {
                            Block block = p.getTargetBlock((Set<Material>) null, 25);
                            if (block != null) {
                                plugin.getServer().getPluginManager().callEvent(new NavigatorInteractEvent(p, ActionType.RIGHT_CLICK, block.getLocation()));
                            }
                        }
                    }
                    e.setCancelled(true);
                }

                ////////////******STAFFLIST******/////////////
                if (p.getItemInHand().getType() == Material.getMaterial(plugin.getConfig().getInt("staffItems.staffList.id"))
                        && p.hasPermission("kstaffmode.items.stafflist")) {
                    if(e.getAction() !=null ) {
                        plugin.getServer().getPluginManager().callEvent(new StaffListInteractEvent(p));
                    }
                    e.setCancelled(true);
                }

                ////////////******VANISH******/////////////
                if (p.getItemInHand().getType() == Material.getMaterial(plugin.getConfig().getInt("staffItems.vanish.visible.id"))
                        || p.getItemInHand().getType() == Material.getMaterial(plugin.getConfig().getInt("staffItems.vanish.vanished.id"))
                        && p.hasPermission("kstaffmode.items.vanish")) {
                    if(e.getAction() !=null ) {
                        plugin.getServer().getPluginManager().callEvent(new VanishInteractEvent(p));
                    }
                    e.setCancelled(true);
                }

                ////////////******FLY******/////////////
                if (p.getItemInHand().getType() == Material.getMaterial(plugin.getConfig().getInt("staffItems.fly.id")) && p.hasPermission("kstaffmode.items.fly")) {
                    if(e.getAction() !=null ) {
                        plugin.getServer().getPluginManager().callEvent(new FlyInteractEvent(p));
                    }
                    e.setCancelled(true);
                }

                ////////////******RANDOMTP******/////////////
                if(p.getItemInHand().getType() == Material.getMaterial(plugin.getConfig().getInt("staffItems.randomtp.id")) && p.hasPermission("kstaffmode.items.randomtp")) {
                    if(e.getAction() !=null ) {
                        plugin.getServer().getPluginManager().callEvent(new RandomTpInteractEvent(p));
                    }
                    e.setCancelled(true);
                }
            }
        }
    }


    @EventHandler
    public void navigatorInteract (NavigatorInteractEvent e) {
        if(e.getPlayer().hasPermission("kstaffmode.items.navigator")) {
            if (e.getActionType() == ActionType.LEFT_CLICK) {
                if (plugin.getConfig().getDouble("navigatorImpulse") > 0.0D && plugin.getConfig().getDouble("navigatorImpulse") <= 8.0D) {
                    Vector v = e.getLookingAt().getDirection().normalize().multiply(plugin.getConfig().getDouble("navigatorImpulse"));
                    e.getPlayer().setVelocity(v);
                }
            }

            if (e.getActionType() == ActionType.RIGHT_CLICK) {
                Location loc = e.getLookingAt();
                e.getPlayer().teleport(loc);
            }
        }
    }

    @EventHandler
    public void flyInteract (FlyInteractEvent e) {
        if(e.getPlayer().hasPermission("kstaffmode.items.fly")) {
            plugin.getStaffModeManager().toogleFly(e.getPlayer());
        }
    }

    @EventHandler
    public void randomTpInteract (RandomTpInteractEvent e) {
        if(e.getPlayer().hasPermission("kstaffmode.items.randomtp")) {
            plugin.getStaffModeManager().teleportToRandomplayer(e.getPlayer(), plugin.getConfig().getBoolean("randomTpMultiworld"));
        }
    }

    @EventHandler
    public void randomTpInteract (VanishInteractEvent e) {
        if(e.getPlayer().hasPermission("kstaffmode.items.vanish")) {
            plugin.getStaffModeManager().toogleVanish(e.getPlayer());
        }
    }

    @EventHandler
    public void randomTpInteract (StaffListInteractEvent e) {
        if(e.getPlayer().hasPermission("kstaffmode.items.stafflist")) {
            plugin.getMenuManager().getStaffListMainMenu().open(e.getPlayer());
        }
    }
}
