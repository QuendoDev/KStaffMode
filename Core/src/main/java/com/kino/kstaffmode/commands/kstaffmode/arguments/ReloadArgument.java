package com.kino.kstaffmode.commands.kstaffmode.arguments;

import com.kino.kore.utils.command.CommandArgument;
import com.kino.kore.utils.files.BasicFilesManager;
import com.kino.kore.utils.messages.MessageUtils;
import com.kino.kstaffmode.KStaffMode;
import com.kino.kstaffmode.managers.files.FilesManager;
import com.kino.kstaffmode.managers.staffmode.StaffModeManager;
import lombok.AllArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;


public class ReloadArgument extends CommandArgument {

    private FilesManager filesManager;
    private StaffModeManager staffModeManager;

    public ReloadArgument(FilesManager filesManager, StaffModeManager staffModeManager){
        super("reload", "kstaffmode.commands.reload",
                "Reload all configurations of the plugin.", "rl, rel, restart");
        this.filesManager = filesManager;
        this.staffModeManager = staffModeManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(args.length == 1){
            filesManager.reloadConfig();
            filesManager.getBasicFilesManager().reloadMessages();
            staffModeManager.registerItems();
            MessageUtils.sendMessage(sender, filesManager.getBasicFilesManager().getMessages().getString("reloadMessage"));
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
