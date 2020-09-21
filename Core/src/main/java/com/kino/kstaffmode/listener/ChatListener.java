package com.kino.kstaffmode.listener;

import com.kino.kore.utils.BungeeUtils;
import com.kino.kstaffmode.KStaffMode;
import com.kino.kstaffmode.managers.staffmode.StaffModeManager;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@AllArgsConstructor
public class ChatListener implements Listener {


    private FileConfiguration config;
    private FileConfiguration messages;
    private StaffModeManager staffModeManager;

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){

        Player p = e.getPlayer();

        if(p !=null){
            if(config.getBoolean ("staffChatEnabled")) {

                String prefix = ChatColor.translateAlternateColorCodes('&', messages.getString("staffchat.prefix"));
                String separator = ChatColor.translateAlternateColorCodes('&', messages.getString("staffchat.separator"));
                String msg = e.getMessage();

                if (staffModeManager.getInStaffChat().contains(p.getUniqueId())) {
                    for (Player staff : Bukkit.getServer().getOnlinePlayers()) {
                        if (staff.hasPermission("kstaffmode.staffchat.read")) {
                            staff.sendMessage(prefix + p.getDisplayName() + separator + msg);
                        }
                    }
                    e.setMessage(null);
                    e.setCancelled(true);
                }
            }
        }
    }


}
