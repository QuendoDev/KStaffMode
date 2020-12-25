package com.kino.kstaffmode.bungee.listener;

import com.kino.kstaffmode.bungee.files.YMLFile;
import com.kino.kstaffmode.bungee.managers.StaffChatManager;
import lombok.AllArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

@AllArgsConstructor
public class ChatListener implements Listener {

    private StaffChatManager staffChatManager;

    private YMLFile config;

    @EventHandler (priority = EventPriority.LOWEST)
    public void onChat (ChatEvent e) {

        ProxiedPlayer proxiedPlayer = (ProxiedPlayer) e.getSender();

        if (proxiedPlayer !=null) {
            String prefix = ChatColor.translateAlternateColorCodes('&', config.get().getString("staffchat.prefix"));
            String separator = ChatColor.translateAlternateColorCodes('&', config.get().getString("staffchat.separator"));
            String msg = e.getMessage();

            if (!e.isCommand()) {
                if (staffChatManager.isInStaffChat(proxiedPlayer)) {
                    for (ProxiedPlayer staff : ProxyServer.getInstance().getPlayers()) {
                        if (staff.hasPermission("kstaffmode.staffchat.read")) {
                            staff.sendMessage(new TextComponent(prefix + proxiedPlayer.getDisplayName() + separator + msg));
                        }
                    }
                    e.setMessage(null);
                    e.setCancelled(true);
                }
            }
        }
    }
}
