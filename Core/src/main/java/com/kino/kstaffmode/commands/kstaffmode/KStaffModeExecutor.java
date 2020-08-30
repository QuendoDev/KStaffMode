package com.kino.kstaffmode.commands.kstaffmode;

import com.kino.kore.utils.command.ArgumentExecutor;
import com.kino.kstaffmode.KStaffMode;
import com.kino.kstaffmode.commands.kstaffmode.arguments.InfoArgument;
import com.kino.kstaffmode.commands.kstaffmode.arguments.ReloadArgument;
import com.kino.kstaffmode.managers.files.FilesManager;
import com.kino.kstaffmode.managers.staffmode.StaffModeManager;

public class KStaffModeExecutor extends ArgumentExecutor {

    public KStaffModeExecutor(KStaffMode plugin, FilesManager filesManager, StaffModeManager staffModeManager) {
        super("kstaffmode");
        addArgument(new ReloadArgument(filesManager, staffModeManager));
        addArgument(new InfoArgument(plugin));
    }
}
