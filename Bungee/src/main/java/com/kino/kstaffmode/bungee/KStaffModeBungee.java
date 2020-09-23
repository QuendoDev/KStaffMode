package com.kino.kstaffmode.bungee;

import com.kino.kstaffmode.bungee.files.YMLFile;
import com.kino.kstaffmode.bungee.listener.ChatListener;
import com.kino.kstaffmode.bungee.managers.StaffChatManager;
import net.md_5.bungee.api.plugin.Plugin;

public class KStaffModeBungee extends Plugin {

    private YMLFile config;

    private StaffChatManager staffChatManager;

    @Override
    public void onEnable() {
        config = new YMLFile(this, "config", "config").load("config", true);
        staffChatManager = new StaffChatManager(config);
        getProxy().getPluginManager().registerListener(this, new ChatListener(staffChatManager, config));
        getLogger().info("KStaffMode loaded on bungee!");
    }

    @Override
    public void onDisable() {
        staffChatManager.saveData();
        getLogger().info("KStaffMode disabled on bungee!");
    }
}
