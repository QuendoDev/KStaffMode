package com.kino.kstaffmode.commands;

import com.kino.kore.utils.messages.MessageUtils;
import com.kino.kstaffmode.KStaffMode;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SCommand implements CommandExecutor {

    private KStaffMode plugin;
    public SCommand(KStaffMode plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        FileConfiguration messages = plugin.getMessages();
        if (sender instanceof Player) {

            Player p = (Player) sender;
            if(args.length != 1){
                MessageUtils.sendMessage(p, "&cCorrect usage: /s <player>");
                return false;
            }

            if(p.hasPermission("kstaffmode.teleport")) {
                Player t = Bukkit.getPlayer(args[0]);
                if (t != null && t.isOnline()) {
                    t.teleport(p);
                    MessageUtils.sendMessage(p,  messages.getString("teleported").replace("<player>", t.getName()));
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("teleported").replace("<player>", t.getName())));
                    return true;
                } else {
                    MessageUtils.sendMessage(sender, plugin.getMessages().getString("playerNotOnline"));
                    return false;
                }
            }else{
                MessageUtils.sendMessage(p, plugin.getMessages().getString("noPerms"));
                return false;
            }

        }else{
            MessageUtils.sendMessage(sender, "&cThis command is only for players!");
            return false;
        }
    }
}
