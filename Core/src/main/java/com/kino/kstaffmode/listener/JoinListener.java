package com.kino.kstaffmode.listener;

import com.kino.kstaffmode.managers.files.PlayerDataManager;
import com.kino.kstaffmode.managers.staffmode.StaffModeManager;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

@AllArgsConstructor
public class JoinListener implements Listener {


    private StaffModeManager staffModeManager;
    private PlayerDataManager playerDataManager;

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        if(e.getPlayer().hasPermission("kstaffmode.data.read")){
            playerDataManager.readData(e.getPlayer());
        }
        if(!e.getPlayer().hasPermission("kstaffmode.bypass.vanish")) {
            for (UUID uuid : staffModeManager.getVanished()) {
                e.getPlayer().hidePlayer(Bukkit.getPlayer(uuid));
            }
        }
        staffModeManager.setOnline();
    }
}
