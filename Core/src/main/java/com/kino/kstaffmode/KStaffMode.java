package com.kino.kstaffmode;

import com.kino.kore.utils.PluginUtils;
import com.kino.kore.utils.files.BasicFilesManager;
import com.kino.kore.utils.files.YMLFile;
import com.kino.kstaffmode.commands.*;
import com.kino.kstaffmode.commands.gamemode.GameModeExecutor;
import com.kino.kstaffmode.listener.ChatListener;
import com.kino.kstaffmode.listener.JoinListener;
import com.kino.kstaffmode.listener.LeaveListener;
import com.kino.kstaffmode.listener.MoveListener;
import com.kino.kstaffmode.listener.sm.InventoryListener;
import com.kino.kstaffmode.commands.kstaffmode.KStaffModeExecutor;
import com.kino.kstaffmode.listener.sm.ItemsInteractListener;
import com.kino.kstaffmode.listener.sm.StaffModeBasicListener;
import com.kino.kstaffmode.managers.files.DataManager;
import com.kino.kstaffmode.managers.files.PlayerDataManager;
import com.kino.kstaffmode.managers.menus.MenuManager;
import com.kino.kstaffmode.managers.sm.StaffModeManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class KStaffMode extends JavaPlugin {



    private StaffModeManager staffModeManager;
    private MenuManager menuManager;
    private DataManager dataManager;
    private PlayerDataManager playerDataManager;

    private BasicFilesManager basicFilesManager;
    private YMLFile dataFile;

    @Override
    public void onEnable() {
        this.registerBasicFiles();
        this.registerFiles();
        this.registerClasses();
        this.registerListeners();
        this.registerCommands();
        PluginUtils.sendEnableMessages(this);
    }

    @Override
    public void onDisable() {
        this.dataManager.saveData();
        PluginUtils.sendDisableMessages(this);
    }

    private void registerBasicFiles() {
        basicFilesManager = new BasicFilesManager(this);
        basicFilesManager.registerConfig();
        basicFilesManager.registerMessages();
    }

    private void registerFiles() {
        dataFile = new YMLFile(this, "data");
    }

    private void registerListeners(){
        Bukkit.getServer().getPluginManager().registerEvents(new LeaveListener(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new StaffModeBasicListener(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new ItemsInteractListener(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new InventoryListener(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new MoveListener(this), this);
    }

    private void registerCommands(){
        getCommand("kstaffmode").setExecutor(new KStaffModeExecutor(this));
        getCommand("gm").setExecutor(new GameModeExecutor(this));
        getCommand("staff").setExecutor(new StaffModeCommand(this));
        getCommand("vanish").setExecutor(new VanishCommand(this));
        getCommand("fly").setExecutor(new FlyCommand(this));
        getCommand("staffchat").setExecutor(new StaffChatCommand(this));
        getCommand("s").setExecutor(new SCommand(this));
        getCommand("invsee").setExecutor(new InvSeeCommand(this));
        getCommand("freeze").setExecutor(new FreezeCommand(this));
    }

    private void registerClasses(){
        this.staffModeManager = new StaffModeManager(this);
        this.menuManager = new MenuManager(this);
        this.dataManager = new DataManager(this);
        dataManager.startManager();
        this.playerDataManager = new PlayerDataManager(this);
    }

    public BasicFilesManager getBasicFilesManager() {
        return basicFilesManager;
    }

    public FileConfiguration getMessages(){
        return basicFilesManager.getMessages();
    }

    public StaffModeManager getStaffModeManager() {
        return staffModeManager;
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public YMLFile getDataFile() {
        return dataFile;
    }

    public FileConfiguration getData(){
        return dataFile;
    }

    public PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }

    public void saveMessages() {
        basicFilesManager.saveMessages();
    }

    public void saveData() {
        dataFile.save();
    }

    public MenuManager getMenuManager() {
        return menuManager;
    }
}
