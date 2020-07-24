package com.kino.kstaffmode.listener;

import com.kino.kstaffmode.KStaffMode;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {


    private KStaffMode plugin;
    public ChatListener (KStaffMode plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        FileConfiguration messages = plugin.getMessages();

        Player p = e.getPlayer();

        if(p !=null){
            String prefix = ChatColor.translateAlternateColorCodes('&', messages.getString("staffchat.prefix"));
            String separator = ChatColor.translateAlternateColorCodes('&', messages.getString("staffchat.separator"));
            String msg = e.getMessage();

            if(plugin.getStaffModeManager().getInStaffChat().contains(p.getUniqueId())){
                for(Player staff : Bukkit.getServer().getOnlinePlayers()) {
                    if(staff.hasPermission("kstaffmode.staffchat.read")){
                        staff.sendMessage(prefix + p.getDisplayName() + separator + msg);
                        e.setMessage(null);
                        e.setCancelled(true);
                    }
                }
            }
        }
    }


}
