package com.kino.kstaffmode.listener;

import com.kino.kore.utils.messages.MessageUtils;
import com.kino.kstaffmode.managers.staffmode.StaffModeManager;
import lombok.AllArgsConstructor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

@AllArgsConstructor
public class MoveListener implements Listener {

    private final StaffModeManager manager;
    private final FileConfiguration messages;


    @EventHandler
    public void move (PlayerMoveEvent e) {
        if(manager.isFrozen(e.getPlayer())) {
            MessageUtils.sendMessage(e.getPlayer(), messages.getString("moveWhileFrozen"));
            e.getPlayer().teleport(e.getFrom());
        }
    }

}
