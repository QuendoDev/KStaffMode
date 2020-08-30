package com.kino.kstaffmode.managers.menus;

import com.kino.kstaffmode.KStaffMode;
import com.kino.kstaffmode.managers.staffmode.StaffModeManager;
import com.kino.kstaffmode.menus.InspectMenu;
import com.kino.kstaffmode.menus.StaffListMainMenu;
import com.kino.kstaffmode.menus.StaffListPlayingMenu;
import com.kino.kstaffmode.menus.StaffListSmMenu;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class MenuManager {



    private StaffListMainMenu staffListMainMenu;
    private StaffListPlayingMenu staffListPlayingMenu;
    private StaffListSmMenu staffListSmMenu;
    private InspectMenu inspectMenu;

    private List<PlayerInventory> playerInventories;

    public MenuManager (FileConfiguration config, StaffModeManager staffModeManager, MenuManager menuManager) {

        this.playerInventories = new ArrayList<>();
        this.staffListMainMenu = new StaffListMainMenu(config);
        this.staffListPlayingMenu = new StaffListPlayingMenu(config, staffModeManager, menuManager);
        this.staffListSmMenu = new StaffListSmMenu(config, staffModeManager, menuManager);
        this.inspectMenu = new InspectMenu(config);
    }

    public StaffListMainMenu getStaffListMainMenu() {
        return staffListMainMenu;
    }

    public StaffListPlayingMenu getStaffListPlayingMenu() {
        return staffListPlayingMenu;
    }

    public StaffListSmMenu getStaffListSmMenu() {
        return staffListSmMenu;
    }

    public PlayerInventory getPlayerInventory (String name) {
        for(PlayerInventory playerInventory : playerInventories) {
            if(playerInventory.getPlayer().getName().equals(name)) {
                return playerInventory;
            }
        }
        return null;
    }

    public void addPlayerInventory (PlayerInventory playerInventory) {
        playerInventories.add(playerInventory);
    }

    public void removePlayerInventory (String name) {
        for(int i = 0; i < playerInventories.size(); i++) {
            if(playerInventories.get(i).getPlayer().getName().equals(name)) {
                playerInventories.remove(i);
            }
        }
    }

    public List<PlayerInventory> getPlayerInventories() {
        return playerInventories;
    }

    public InspectMenu getInspectMenu() {
        return inspectMenu;
    }
}
