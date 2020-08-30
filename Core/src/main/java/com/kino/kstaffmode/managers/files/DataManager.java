package com.kino.kstaffmode.managers.files;

import com.kino.kore.utils.messages.LoggerUtils;
import com.kino.kstaffmode.KStaffMode;
import com.kino.kstaffmode.managers.staffmode.StaffModeManager;
import lombok.AllArgsConstructor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class DataManager {

    private List<String> staffChatInConfig;
    private List<String> flyInConfig;
    private List<String> staffModeInConfig;
    private List<String> vanishInConfig;
    private Map<String, ItemStack[]> armorItemsInConfig;
    private Map<String, ItemStack[]> inventoryItemsInConfig;

    private FileConfiguration data;
    private FileConfiguration config;
    private FilesManager filesManager;
    private StaffModeManager staffModeManager;
    
    public DataManager (FilesManager filesManager, FileConfiguration config, StaffModeManager staffModeManager) {
        this.filesManager = filesManager;
        this.config = config;
        this.data = filesManager.getData();
        this.staffModeManager = staffModeManager;
    }


    public void startManager(){
        armorItemsInConfig = new HashMap<>();
        if(data.getConfigurationSection("armorItems") !=null){
            for(String s : data.getConfigurationSection("armorItems").getKeys(false)){
                if(config.get("armorItems." + s) !=null) {
                    List<ItemStack> list = (List<ItemStack>) config.get("armorItems." + s);
                    armorItemsInConfig.put(s, (ItemStack[]) list.toArray());
                }
            }
            data.set("armorItems", null);
        }

        inventoryItemsInConfig = new HashMap<>();
        if(data.getConfigurationSection("inventoryItems") !=null){
            for(String s : data.getConfigurationSection("inventoryItems").getKeys(false)){
                if(config.get("inventoryItems." + s) !=null) {
                    List<ItemStack> list = (List<ItemStack>) config.get("inventoryItems." + s);
                    inventoryItemsInConfig.put(s, (ItemStack[]) list.toArray());
                }
            }
            data.set("inventoryItems", null);
        }

        staffChatInConfig = new ArrayList<>();
        if(data.getStringList("staffchat") !=null){
            staffChatInConfig.addAll(data.getStringList("staffchat"));
            data.set("staffchat", null);
        }

        flyInConfig = new ArrayList<>();
        if(data.getStringList("fly") !=null){
            flyInConfig.addAll(data.getStringList("fly"));
            data.set("fly", null);
        }

        staffModeInConfig = new ArrayList<>();
        if(data.getStringList("staffmode") !=null){
            staffModeInConfig.addAll(data.getStringList("staffmode"));
            data.set("staffmode", null);
        }

        vanishInConfig = new ArrayList<>();
        if(data.getStringList("vanish") !=null){
            vanishInConfig.addAll(data.getStringList("vanish"));
            data.set("vanish", null);
        }
        filesManager.saveData();
    }

    public void saveData(){
        LoggerUtils.sendConsoleMessage("&aSaving data...");
        saveStaffChat();
        saveFly();
        saveStaffMode();
        saveArmorItems();
        saveInventoryItems();
        saveVanish();
        filesManager.saveData();
        LoggerUtils.sendConsoleMessage("&aData saved!");
    }


    public void saveFly(){
        for(UUID uuid : staffModeManager.getFly()){
            if(!flyInConfig.contains(uuid.toString())) {
                flyInConfig.add(uuid.toString());
            }
        }
        data.set("fly", flyInConfig);
    }

    public void saveStaffChat(){
        for(UUID uuid : staffModeManager.getInStaffChat()){
            if(!staffChatInConfig.contains(uuid.toString())) {
                staffChatInConfig.add(uuid.toString());
            }
        }
        data.set("staffchat", staffChatInConfig);
    }

    public void saveStaffMode(){
        for(UUID uuid : staffModeManager.getInStaffMode()){
            if(!staffModeInConfig.contains(uuid.toString())) {
                staffModeInConfig.add(uuid.toString());
            }
        }
        data.set("staffmode", staffModeInConfig);
    }

    public void saveArmorItems(){
        for(UUID uuid : staffModeManager.getArmorItems().keySet()){
            if(!armorItemsInConfig.containsKey(uuid.toString())){
                armorItemsInConfig.put(uuid.toString(), staffModeManager.getArmorItems().get(uuid));
            }
        }
        data.set("armorItems", armorItemsInConfig);
    }

    public void saveInventoryItems(){
        for(UUID uuid : staffModeManager.getInventoryItems().keySet()){
            if(!inventoryItemsInConfig.containsKey(uuid.toString())){
                inventoryItemsInConfig.put(uuid.toString(), staffModeManager.getInventoryItems().get(uuid));
            }
        }
        data.set("inventoryItems", inventoryItemsInConfig);
    }

    public void saveVanish(){
        for(UUID uuid : staffModeManager.getVanished()){
            if(!staffModeInConfig.contains(uuid.toString()) && !staffModeManager.getInStaffMode().contains(uuid)) {
                if (!vanishInConfig.contains(uuid.toString())) {
                    vanishInConfig.add(uuid.toString());
                }
            }
        }
        data.set("vanish", vanishInConfig);
    }

    public List<String> getStaffChatInConfig() {
        return staffChatInConfig;
    }

    public List<String> getFlyInConfig() {
        return flyInConfig;
    }

    public List<String> getStaffModeInConfig() {
        return staffModeInConfig;
    }

    public List<String> getVanishInConfig() {
        return vanishInConfig;
    }

    public Map<String, ItemStack[]> getArmorItemsInConfig() {
        return armorItemsInConfig;
    }

    public Map<String, ItemStack[]> getInventoryItemsInConfig() {
        return inventoryItemsInConfig;
    }
}
