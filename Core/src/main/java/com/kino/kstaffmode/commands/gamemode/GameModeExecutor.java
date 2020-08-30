package com.kino.kstaffmode.commands.gamemode;

import com.kino.kore.utils.BukkitUtils;
import com.kino.kore.utils.command.ArgumentExecutor;
import com.kino.kstaffmode.commands.gamemode.arguments.AdventureArgument;
import com.kino.kstaffmode.commands.gamemode.arguments.SurvivalArgument;
import com.kino.kstaffmode.KStaffMode;
import com.kino.kstaffmode.commands.gamemode.arguments.CreativeArgument;
import com.kino.kstaffmode.commands.gamemode.arguments.SpectatorArgument;
import org.bukkit.configuration.file.FileConfiguration;

public class GameModeExecutor extends ArgumentExecutor {

    public GameModeExecutor(FileConfiguration messages) {
        super("gm");
        addArgument(new SurvivalArgument(messages));
        addArgument(new CreativeArgument(messages));
        addArgument(new AdventureArgument(messages));
        addArgument(new SpectatorArgument(messages));
    }
}
