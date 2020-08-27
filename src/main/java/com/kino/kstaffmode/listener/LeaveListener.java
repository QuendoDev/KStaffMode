package com.kino.kstaffmode.listener;

import com.kino.kore.utils.messages.MessageUtils;
import com.kino.kstaffmode.KStaffMode;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
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
        if(plugin.getStaffModeManager().isFrozen(e.getPlayer())) {
            for(Player p : Bukkit.getServer().getOnlinePlayers()) {
                if(p.hasPermission("kstaffmode.freezedisconnect")) {
                    MessageUtils.sendMessage(p, plugin.getMessages().getString("frozenDisconnect").replace("<player>", e.getPlayer().getName()));
                }
            }
            plugin.getStaffModeManager().getFrozen().remove(e.getPlayer().getUniqueId());
            plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), plugin.getConfig().getString("frozenDisconnect").replace("<player>", e.getPlayer().getName()));
        }
        if(e.getPlayer().hasPermission("kstaffmode.staffmode")){
            plugin.getPlayerDataManager().savePlayerData(e.getPlayer());
        }
    }

    @EventHandler
    public void onKick(PlayerKickEvent e){
        if(e.getPlayer().hasPermission("kstaffmode.staffmode")){
            plugin.getPlayerDataManager().savePlayerData(e.getPlayer());
        }

        if(plugin.getStaffModeManager().isFrozen(e.getPlayer())) {
            for(Player p : Bukkit.getServer().getOnlinePlayers()) {
                if(p.hasPermission("kstaffmode.freezedisconnect")) {
                    MessageUtils.sendMessage(p, plugin.getMessages().getString("frozenDisconnect").replace("<player>", e.getPlayer().getName()));
                }
            }
            plugin.getStaffModeManager().getFrozen().remove(e.getPlayer().getUniqueId());
            plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), plugin.getConfig().getString("frozenDisconnect").replace("<player>", e.getPlayer().getName()));
        }
    }
}
