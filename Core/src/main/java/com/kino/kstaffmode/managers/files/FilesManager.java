package com.kino.kstaffmode.managers.files;

import com.kino.kore.utils.files.BasicFilesManager;
import com.kino.kore.utils.files.YMLFile;
import com.kino.kstaffmode.KStaffMode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

@Getter
public class FilesManager {

    private BasicFilesManager basicFilesManager;
    private YMLFile data;
    private YMLFile scoreboard;
    private KStaffMode plugin;


    public FilesManager (KStaffMode plugin) {
        this.plugin = plugin;
        this.basicFilesManager = new BasicFilesManager(plugin);
        this.data = new YMLFile(plugin, "data");
        this.scoreboard = new YMLFile(plugin, "scoreboard");
    }

    public void start () {
        basicFilesManager.registerConfig();
        basicFilesManager.registerMessages();
    }


    public void saveData () {
        data.save();
    }

    public FileConfiguration getMessages() {
        return basicFilesManager.getMessages();
    }

    public void reloadConfig () {
        plugin.reloadConfig();
    }

    public void saveScoreboard() {
        scoreboard.save();
    }
}
