package com.kino.kstaffmode.commands;

import com.kino.kore.utils.messages.MessageUtils;
import com.kino.kstaffmode.KStaffMode;
import com.kino.kstaffmode.managers.staffmode.StaffModeManager;
import lombok.AllArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class StaffChatCommand implements CommandExecutor {

    private StaffModeManager staffModeManager;
    private FileConfiguration messages;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (p.hasPermission("kstaffmode.staffchat.talk")) {
                staffModeManager.toogleStaffChat(p);
                return true;
            } else {
                MessageUtils.sendMessage(p, messages.getString("noPerms"));
                return false;
            }

        } else {
            MessageUtils.sendMessage(sender, "&cThis command is only for players!");
            return false;
        }
    }
}
