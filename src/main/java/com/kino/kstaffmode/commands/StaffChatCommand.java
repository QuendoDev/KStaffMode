package com.kino.kstaffmode.commands;

import com.kino.kore.utils.messages.MessageUtils;
import com.kino.kstaffmode.KStaffMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffChatCommand implements CommandExecutor {

    private KStaffMode plugin;

    public StaffChatCommand(KStaffMode plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (p.hasPermission("kstaffmode.staffchat.talk")) {
                plugin.getStaffModeManager().toogleStaffChat(p);
                return true;
            } else {
                MessageUtils.sendMessage(p, plugin.getMessages().getString("noPerms"));
                return false;
            }

        } else {
            MessageUtils.sendMessage(sender, "&cThis command is only for players!");
            return false;
        }
    }
}
