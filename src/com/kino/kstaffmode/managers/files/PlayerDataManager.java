package com.kino.kstaffmode.managers.files;

import com.kino.kstaffmode.KStaffMode;
import org.bukkit.entity.Player;

public class PlayerDataManager {

    private KStaffMode plugin;
    private DataManager dataManager;

    public PlayerDataManager(KStaffMode plugin){
        this.plugin = plugin;
        this.dataManager = plugin.getDataManager();
    }

    public void readData(Player p){
        if(dataManager.getFlyInConfig().contains(p.getUniqueId().toString())){
            if(!plugin.getStaffModeManager().getFly().contains(p.getUniqueId())) {
                plugin.getStaffModeManager().toogleFly(p);
                dataManager.getFlyInConfig().remove(p.getUniqueId().toString());
            }
        }

        if(dataManager.getStaffChatInConfig().contains(p.getUniqueId().toString())){
            if(!plugin.getStaffModeManager().getInStaffChat().contains(p.getUniqueId())) {
                plugin.getStaffModeManager().toogleStaffChat(p);
                dataManager.getStaffChatInConfig().remove(p.getUniqueId().toString());
            }
        }

        if(dataManager.getStaffModeInConfig().contains(p.getUniqueId().toString())){
            if(!plugin.getStaffModeManager().getInStaffMode().contains(p.getUniqueId())) {
                plugin.getStaffModeManager().toogleStaffMode(p);
                dataManager.getStaffModeInConfig().remove(p.getUniqueId().toString());
            }
        }

        if(dataManager.getArmorItemsInConfig().containsKey(p.getUniqueId().toString())){
            if(!plugin.getStaffModeManager().getArmorItems().containsKey(p.getUniqueId())) {
                plugin.getStaffModeManager().getArmorItems().put(p.getUniqueId(),
                        dataManager.getArmorItemsInConfig().get(p.getUniqueId().toString()));
                dataManager.getArmorItemsInConfig().remove(p.getUniqueId().toString());
            }
        }

        if(dataManager.getInventoryItemsInConfig().containsKey(p.getUniqueId().toString())){
            if(!plugin.getStaffModeManager().getInventoryItems().containsKey(p.getUniqueId())) {
                plugin.getStaffModeManager().getInventoryItems().put(p.getUniqueId(),
                        dataManager.getInventoryItemsInConfig().get(p.getUniqueId().toString()));
                dataManager.getInventoryItemsInConfig().remove(p.getUniqueId().toString());
            }
        }

        if(dataManager.getVanishInConfig().contains(p.getUniqueId().toString())){
            if(!plugin.getStaffModeManager().getInStaffMode().contains(p.getUniqueId())){
                if(!plugin.getStaffModeManager().getVanished().contains(p.getUniqueId())) {
                    plugin.getStaffModeManager().toogleVanish(p);
                    dataManager.getVanishInConfig().remove(p.getUniqueId().toString());
                }
            }
        }

    }

    public void savePlayerData(Player p){
        saveFly(p);
        saveStaffChat(p);
        saveStaffMode(p);
        saveArmorItems(p);
        saveInventoryItems(p);
        saveVanish(p);
    }

    public void saveFly(Player p){
        if(plugin.getStaffModeManager().getFly().contains(p.getUniqueId())){
            if(!dataManager.getFlyInConfig().contains(p.getUniqueId().toString())){
                dataManager.getFlyInConfig().add(p.getUniqueId().toString());
            }
        }
    }

    public void saveStaffChat(Player p){
        if(plugin.getStaffModeManager().getInStaffChat().contains(p.getUniqueId())){
            if(!dataManager.getStaffChatInConfig().contains(p.getUniqueId().toString())){
                dataManager.getStaffChatInConfig().add(p.getUniqueId().toString());
            }
        }
    }

    public void saveStaffMode(Player p){
        if(plugin.getStaffModeManager().getInStaffMode().contains(p.getUniqueId())){
            if(!dataManager.getStaffModeInConfig().contains(p.getUniqueId().toString())){
                dataManager.getStaffModeInConfig().add(p.getUniqueId().toString());
            }
        }
    }

    public void saveVanish(Player p){
        if(plugin.getStaffModeManager().getVanished().contains(p.getUniqueId())){
            if(!plugin.getStaffModeManager().getInStaffMode().contains(p.getUniqueId())){
                if(!dataManager.getVanishInConfig().contains(p.getUniqueId().toString())){
                    dataManager.getVanishInConfig().add(p.getUniqueId().toString());
                }
            }
        }
    }

    public void saveArmorItems(Player p){
        if(plugin.getStaffModeManager().getArmorItems().containsKey(p.getUniqueId())){
            if(!dataManager.getArmorItemsInConfig().containsKey(p.getUniqueId().toString())){
                dataManager.getArmorItemsInConfig().put(p.getUniqueId().toString(), plugin.getStaffModeManager().getArmorItems().get(p.getUniqueId()));
            }
        }
    }

    public void saveInventoryItems(Player p){
        if(plugin.getStaffModeManager().getInventoryItems().containsKey(p.getUniqueId())){
            if(!dataManager.getInventoryItemsInConfig().containsKey(p.getUniqueId().toString())){
                dataManager.getInventoryItemsInConfig().put(p.getUniqueId().toString(), plugin.getStaffModeManager().getInventoryItems().get(p.getUniqueId()));
            }
        }
    }
}
