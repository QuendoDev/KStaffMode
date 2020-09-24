package com.kino.kstaffmode.managers.files;

import com.kino.kstaffmode.KStaffMode;
import com.kino.kstaffmode.managers.staffmode.StaffModeManager;
import lombok.AllArgsConstructor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class PlayerDataManager {

    
    private DataManager dataManager;
    private StaffModeManager staffModeManager;

    private FileConfiguration config;

    

    public void readData(Player p){
        if(dataManager.getFlyInConfig().contains(p.getUniqueId().toString())){
            if(!staffModeManager.getFly().contains(p.getUniqueId())) {
                staffModeManager.toogleFly(p);
                dataManager.getFlyInConfig().remove(p.getUniqueId().toString());
            }
        }

        if (!config.getBoolean("bungee") && config.getBoolean("staffChatEnabled")) {
            if (dataManager.getStaffChatInConfig().contains(p.getUniqueId().toString())) {
                if (!staffModeManager.getInStaffChat().contains(p.getUniqueId())) {
                    staffModeManager.toogleStaffChat(p);
                    dataManager.getStaffChatInConfig().remove(p.getUniqueId().toString());
                }
            }
        }

        if(dataManager.getStaffModeInConfig().contains(p.getUniqueId().toString())){
            if(!staffModeManager.getInStaffMode().contains(p.getUniqueId())) {
                staffModeManager.toogleStaffMode(p);
                dataManager.getStaffModeInConfig().remove(p.getUniqueId().toString());
            }
        }

        if(dataManager.getArmorItemsInConfig().containsKey(p.getUniqueId().toString())){
            if(!staffModeManager.getArmorItems().containsKey(p.getUniqueId())) {
                staffModeManager.getArmorItems().put(p.getUniqueId(),
                        dataManager.getArmorItemsInConfig().get(p.getUniqueId().toString()));
                dataManager.getArmorItemsInConfig().remove(p.getUniqueId().toString());
            }
        }

        if(dataManager.getInventoryItemsInConfig().containsKey(p.getUniqueId().toString())){
            if(!staffModeManager.getInventoryItems().containsKey(p.getUniqueId())) {
                staffModeManager.getInventoryItems().put(p.getUniqueId(),
                        dataManager.getInventoryItemsInConfig().get(p.getUniqueId().toString()));
                dataManager.getInventoryItemsInConfig().remove(p.getUniqueId().toString());
            }
        }

        if(dataManager.getVanishInConfig().contains(p.getUniqueId().toString())){
            if(!staffModeManager.getInStaffMode().contains(p.getUniqueId())){
                if(!staffModeManager.getVanished().contains(p.getUniqueId())) {
                    staffModeManager.toogleVanish(p);
                    dataManager.getVanishInConfig().remove(p.getUniqueId().toString());
                }
            }
        }

    }

    public void savePlayerData(Player p){
        saveFly(p);
        if (!config.getBoolean("bungee") && config.getBoolean("staffChatEnabled")) {
            saveStaffChat(p);
        }
        saveStaffMode(p);
        saveArmorItems(p);
        saveInventoryItems(p);
        saveVanish(p);
    }

    public void saveFly(Player p){
        if(staffModeManager.getFly().contains(p.getUniqueId())){
            if(!dataManager.getFlyInConfig().contains(p.getUniqueId().toString())){
                dataManager.getFlyInConfig().add(p.getUniqueId().toString());
            }
        }
    }

    public void saveStaffChat(Player p){
        if(staffModeManager.getInStaffChat().contains(p.getUniqueId())){
            if(!dataManager.getStaffChatInConfig().contains(p.getUniqueId().toString())){
                dataManager.getStaffChatInConfig().add(p.getUniqueId().toString());
            }
        }
    }

    public void saveStaffMode(Player p){
        if(staffModeManager.getInStaffMode().contains(p.getUniqueId())){
            if(!dataManager.getStaffModeInConfig().contains(p.getUniqueId().toString())){
                dataManager.getStaffModeInConfig().add(p.getUniqueId().toString());
            }
        }
    }

    public void saveVanish(Player p){
        if(staffModeManager.getVanished().contains(p.getUniqueId())){
            if(!staffModeManager.getInStaffMode().contains(p.getUniqueId())){
                if(!dataManager.getVanishInConfig().contains(p.getUniqueId().toString())){
                    dataManager.getVanishInConfig().add(p.getUniqueId().toString());
                }
            }
        }
    }

    public void saveArmorItems(Player p){
        if(staffModeManager.getArmorItems().containsKey(p.getUniqueId())){
            if(!dataManager.getArmorItemsInConfig().containsKey(p.getUniqueId().toString())){
                dataManager.getArmorItemsInConfig().put(p.getUniqueId().toString(), staffModeManager.getArmorItems().get(p.getUniqueId()));
            }
        }
    }

    public void saveInventoryItems(Player p){
        if(staffModeManager.getInventoryItems().containsKey(p.getUniqueId())){
            if(!dataManager.getInventoryItemsInConfig().containsKey(p.getUniqueId().toString())){
                dataManager.getInventoryItemsInConfig().put(p.getUniqueId().toString(), staffModeManager.getInventoryItems().get(p.getUniqueId()));
            }
        }
    }
}
