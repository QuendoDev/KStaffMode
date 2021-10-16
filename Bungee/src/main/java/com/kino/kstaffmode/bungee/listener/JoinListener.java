package com.kino.kstaffmode.bungee.listener;

import com.kino.kstaffmode.bungee.managers.StaffChatManager;
import lombok.AllArgsConstructor;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

@AllArgsConstructor
public class JoinListener implements Listener {

    private StaffChatManager staffChatManager;

    @EventHandler
    public void onJoin (PostLoginEvent e) {


        staffChatManager.addStaffChat(e.getPlayer());



    }
}
