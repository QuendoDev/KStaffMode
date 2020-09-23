package com.kino.kstaffmode.commands;

import com.kino.kore.utils.messages.MessageUtils;
import com.kino.kstaffmode.KStaffMode;
import com.kino.kstaffmode.managers.staffmode.StaffModeManager;
import lombok.AllArgsConstructor;
import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Injected;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class StaffChatCommand implements CommandClass {

    private StaffModeManager staffModeManager;
    private FileConfiguration messages;
    private FileConfiguration config;

    @ACommand(names = {"staffchat", "privatechat", "sc", "adminchat", "modchat"}, desc = "Enable / disable staffchat.", permission = "kstaffmode.staffchat.talk")
    public boolean executeCommand (@Injected (true) CommandSender sender) {
        if (!(sender instanceof Player)) {
            MessageUtils.sendMessage(sender, "&cThis command is only for players!");
            return true;
        }

        Player p = (Player) sender;

        if(config.getBoolean ("staffChatEnabled")) {
            if (p.hasPermission("kstaffmode.staffchat.talk")) {
                staffModeManager.toogleStaffChat(p);
            } else {
                MessageUtils.sendMessage(p, messages.getString("noPerms"));
            }
            return true;
        } else {
            MessageUtils.sendMessage(sender, messages.getString("staffChatDisabledByDefault"));
            return true;
        }
    }
}
