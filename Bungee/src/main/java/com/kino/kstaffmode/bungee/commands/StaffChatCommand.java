package com.kino.kstaffmode.bungee.commands;

import com.kino.kstaffmode.bungee.files.YMLFile;
import com.kino.kstaffmode.bungee.managers.StaffChatManager;
import lombok.AllArgsConstructor;
import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Injected;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

@AllArgsConstructor
@ACommand(names = {"staffchat", "privatechat", "sc", "adminchat", "modchat"}, desc = "Enable / disable staffchat.", permission = "kstaffmode.staffchat.talk")
public class StaffChatCommand implements CommandClass {

    private StaffChatManager staffChatManager;


    @ACommand(names = "")
    public boolean executeCommand (@Injected(true) CommandSender sender) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&cThis command is only for players!")));
            return true;
        }


        ProxiedPlayer p = (ProxiedPlayer) sender;
        staffChatManager.toogleStaffChat(p);
        return true;

    }
}
