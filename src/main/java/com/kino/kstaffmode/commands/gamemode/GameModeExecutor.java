package com.kino.kstaffmode.commands.gamemode;

import com.kino.kore.utils.BukkitUtils;
import com.kino.kore.utils.command.ArgumentExecutor;
import com.kino.kstaffmode.KStaffMode;
import com.kino.kstaffmode.commands.gamemode.arguments.AdventureArgument;
import com.kino.kstaffmode.commands.gamemode.arguments.CreativeArgument;
import com.kino.kstaffmode.commands.gamemode.arguments.SpectatorArgument;
import com.kino.kstaffmode.commands.gamemode.arguments.SurvivalArgument;

public class GameModeExecutor extends ArgumentExecutor {

    public GameModeExecutor(KStaffMode plugin) {
        super("gm");
        addArgument(new SurvivalArgument(plugin));
        addArgument(new CreativeArgument(plugin));
        addArgument(new AdventureArgument(plugin));
        if(BukkitUtils.isMore1_7()){
            addArgument(new SpectatorArgument(plugin));
        }
    }
}
