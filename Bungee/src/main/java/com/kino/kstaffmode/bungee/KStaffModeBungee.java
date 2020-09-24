package com.kino.kstaffmode.bungee;

import com.kino.kstaffmode.bungee.commands.StaffChatCommand;
import com.kino.kstaffmode.bungee.files.YMLFile;
import com.kino.kstaffmode.bungee.listener.ChatListener;
import com.kino.kstaffmode.bungee.listener.PluginMessageListener;
import com.kino.kstaffmode.bungee.managers.StaffChatManager;
import me.fixeddev.ebcm.bungee.BungeeCommandManager;
import me.fixeddev.ebcm.parametric.ParametricCommandBuilder;
import me.fixeddev.ebcm.parametric.ReflectionParametricCommandBuilder;
import net.md_5.bungee.api.plugin.Plugin;

public class KStaffModeBungee extends Plugin {

    private YMLFile config;

    private StaffChatManager staffChatManager;

    private final ParametricCommandBuilder builder = new ReflectionParametricCommandBuilder();

    private final BungeeCommandManager commandManager = new BungeeCommandManager(this);

    @Override
    public void onEnable() {
        getProxy().registerChannel("kino:kstaffmode");
        config = new YMLFile(this, "config", "config").load("config", true);
        staffChatManager = new StaffChatManager(config);
        commandManager.registerCommands(builder.fromClass(new StaffChatCommand()));
        getProxy().getPluginManager().registerListener(this, new ChatListener(staffChatManager, config));
        getProxy().getPluginManager().registerListener(this, new PluginMessageListener(staffChatManager));
        getLogger().info("KStaffMode loaded on bungee!");
    }

    @Override
    public void onDisable() {
        staffChatManager.saveData();
        getLogger().info("KStaffMode disabled on bungee!");
    }
}
