package com.kino.kstaffmode.commands.kstaffmode.arguments;

import com.kino.kore.utils.PluginUtils;
import com.kino.kore.utils.command.CommandArgument;
import com.kino.kore.utils.messages.MessageUtils;
import com.kino.kstaffmode.KStaffMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class InfoArgument extends CommandArgument {

    private KStaffMode plugin;
    public InfoArgument(KStaffMode plugin) {
        super("info", "kstaffmode.commands.info",
                "Use this argument to get the info of this plugin.",
                new String[]{"inf, version, author"});
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(args.length == 1){
            MessageUtils.sendMessage(sender, "&e&m--------&e[KStaffMode]&e&m--------");
            MessageUtils.sendMessage(sender, "&aName: &f" + PluginUtils.getName(plugin));
            MessageUtils.sendMessage(sender, "&aVersion: &f" + PluginUtils.getVersion(plugin));
            MessageUtils.sendMessage(sender, "&aAuthor: &f" + Arrays.toString(PluginUtils.getAuthors(plugin).toArray()));
            MessageUtils.sendMessage(sender, "&e&m----------------------------");
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
