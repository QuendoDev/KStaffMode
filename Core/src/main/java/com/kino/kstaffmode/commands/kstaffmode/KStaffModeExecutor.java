package com.kino.kstaffmode.commands.kstaffmode;

import com.kino.kore.utils.command.ArgumentExecutor;
import com.kino.kstaffmode.KStaffMode;
import com.kino.kstaffmode.commands.kstaffmode.arguments.InfoArgument;
import com.kino.kstaffmode.commands.kstaffmode.arguments.ReloadArgument;

public class KStaffModeExecutor extends ArgumentExecutor {

    public KStaffModeExecutor(KStaffMode plugin) {
        super("com/kino/kstaffmode");
        addArgument(new ReloadArgument(plugin));
        addArgument(new InfoArgument(plugin));
    }
}
