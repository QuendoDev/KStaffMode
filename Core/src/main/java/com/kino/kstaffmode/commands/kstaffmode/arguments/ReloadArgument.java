package com.kino.kstaffmode.commands.kstaffmode.arguments;

import com.kino.kore.utils.command.CommandArgument;
import com.kino.kore.utils.messages.MessageUtils;
import com.kino.kstaffmode.KStaffMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;


public class ReloadArgument extends CommandArgument {

    private KStaffMode plugin;
    public ReloadArgument(KStaffMode plugin){
        super("reload", "kstaffmode.commands.reload",
                "Reload all configurations of the plugin.", new String[]{"rl, rel, restart"});
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(args.length == 1){
            plugin.reloadConfig();
            plugin.getBasicFilesManager().reloadMessages();
            plugin.getStaffModeManager().registerItems();
            MessageUtils.sendMessage(sender, plugin.getMessages().getString("reloadMessage"));
            return true;
        }else{
            MessageUtils.sendMessage(sender, "&cCorrect usage: " + getUsage(label));
            return false;
        }

    }

    @Override
    public String getUsage(String label) {
        return '/' + label + ' ' + getName();
    }
}
