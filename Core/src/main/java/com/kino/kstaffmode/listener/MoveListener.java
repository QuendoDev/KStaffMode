package com.kino.kstaffmode.listener;

import com.kino.kore.utils.messages.MessageUtils;
import com.kino.kstaffmode.KStaffMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveListener implements Listener {

    private KStaffMode plugin;
    public MoveListener (KStaffMode plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void move (PlayerMoveEvent e) {
        if(plugin.getStaffModeManager().isFrozen(e.getPlayer())) {
            MessageUtils.sendMessage(e.getPlayer(), plugin.getMessages().getString("moveWhileFrozen"));
            e.setCancelled(true);
        }
    }

}
