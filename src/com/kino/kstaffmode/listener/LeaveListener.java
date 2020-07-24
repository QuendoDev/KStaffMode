package com.kino.kstaffmode.listener;

import com.kino.kstaffmode.KStaffMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveListener implements Listener {

    private KStaffMode plugin;
    public LeaveListener(KStaffMode plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        if(e.getPlayer().hasPermission("kstaffmode.staffmode")){
            plugin.getPlayerDataManager().savePlayerData(e.getPlayer());
        }
    }

    @EventHandler
    public void onKick(PlayerKickEvent e){
        if(e.getPlayer().hasPermission("kstaffmode.staffmode")){
            plugin.getPlayerDataManager().savePlayerData(e.getPlayer());
        }
    }
}
