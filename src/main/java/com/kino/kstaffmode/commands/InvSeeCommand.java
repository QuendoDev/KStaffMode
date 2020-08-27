package com.kino.kstaffmode.commands;

import com.kino.kore.utils.messages.MessageUtils;
import com.kino.kstaffmode.KStaffMode;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InvSeeCommand implements CommandExecutor {

    private KStaffMode plugin;

    public InvSeeCommand(KStaffMode plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if(args.length == 1) {

                if (p.hasPermission("kstaffmode.inspect")) {
                    if(Bukkit.getPlayer(args[0]) !=null) {
                        Player t = Bukkit.getPlayer(args[0]);
                        if (!t.hasPermission("kstaffmode.bypass.inspect")) {
                            plugin.getMenuManager().getInspectMenu().open(p, t);
                            return true;
                        }
                    }
                    return false;
                } else {
                    MessageUtils.sendMessage(p, plugin.getMessages().getString("noPerms"));
                    return false;
                }
            }else {
                MessageUtils.sendMessage(p, "&cCorrect usage: /invsee <player>");
                return false;
            }
        } else {
            MessageUtils.sendMessage(sender, "&cThis command is only for players!");
            return false;
        }
    }
}
