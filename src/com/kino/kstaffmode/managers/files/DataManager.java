package com.kino.kstaffmode.managers.files;

import com.kino.kore.utils.messages.LoggerUtils;
import com.kino.kstaffmode.KStaffMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

@SuppressWarnings("unchecked")
public class DataManager {

    private List<String> staffChatInConfig;
    private List<String> flyInConfig;
    private List<String> staffModeInConfig;
    private List<String> vanishInConfig;
    private Map<String, ItemStack[]> armorItemsInConfig;
    private Map<String, ItemStack[]> inventoryItemsInConfig;
    private KStaffMode plugin;

    public DataManager(KStaffMode plugin){
        this.plugin = plugin;
    }

    public void startManager(){
        armorItemsInConfig = new HashMap<>();
        if(plugin.getData().getConfigurationSection("armorItems") !=null){
            for(String s : plugin.getData().getConfigurationSection("armorItems").getKeys(false)){
                if(plugin.getConfig().get("armorItems." + s) !=null) {
                    List<ItemStack> list = (List<ItemStack>) plugin.getConfig().get("armorItems." + s);
                    armorItemsInConfig.put(s, (ItemStack[]) list.toArray());
                }
            }
            plugin.getData().set("armorItems", null);
        }

        inventoryItemsInConfig = new HashMap<>();
        if(plugin.getData().getConfigurationSection("inventoryItems") !=null){
            for(String s : plugin.getData().getConfigurationSection("inventoryItems").getKeys(false)){
                if(plugin.getConfig().get("inventoryItems." + s) !=null) {
                    List<ItemStack> list = (List<ItemStack>) plugin.getConfig().get("inventoryItems." + s);
                    inventoryItemsInConfig.put(s, (ItemStack[]) list.toArray());
                }
            }
            plugin.getData().set("inventoryItems", null);
        }

        staffChatInConfig = new ArrayList<>();
        if(plugin.getData().getStringList("staffchat") !=null){
            staffChatInConfig.addAll(plugin.getData().getStringList("staffchat"));
            plugin.getData().set("staffchat", null);
        }

        flyInConfig = new ArrayList<>();
        if(plugin.getData().getStringList("fly") !=null){
            flyInConfig.addAll(plugin.getData().getStringList("fly"));
            plugin.getData().set("fly", null);
        }

        staffModeInConfig = new ArrayList<>();
        if(plugin.getData().getStringList("staffmode") !=null){
            staffModeInConfig.addAll(plugin.getData().getStringList("staffmode"));
            plugin.getData().set("staffmode", null);
        }

        vanishInConfig = new ArrayList<>();
        if(plugin.getData().getStringList("vanish") !=null){
            vanishInConfig.addAll(plugin.getData().getStringList("vanish"));
            plugin.getData().set("vanish", null);
        }
        plugin.saveData();
    }

    public void saveData(){
        LoggerUtils.sendConsoleMessage("&aSaving data...");
        saveStaffChat();
        saveFly();
        saveStaffMode();
        saveArmorItems();
        saveInventoryItems();
        saveVanish();
        plugin.saveData();
        LoggerUtils.sendConsoleMessage("&aData saved!");
    }


    public void saveFly(){
        for(UUID uuid : plugin.getStaffModeManager().getFly()){
            if(!flyInConfig.contains(uuid.toString())) {
                flyInConfig.add(uuid.toString());
            }
        }
        plugin.getData().set("fly", flyInConfig);
    }

    public void saveStaffChat(){
        for(UUID uuid : plugin.getStaffModeManager().getInStaffChat()){
            if(!staffChatInConfig.contains(uuid.toString())) {
                staffChatInConfig.add(uuid.toString());
            }
        }
        plugin.getData().set("staffchat", staffChatInConfig);
    }

    public void saveStaffMode(){
        for(UUID uuid : plugin.getStaffModeManager().getInStaffMode()){
            if(!staffModeInConfig.contains(uuid.toString())) {
                staffModeInConfig.add(uuid.toString());
            }
        }
        plugin.getData().set("staffmode", staffModeInConfig);
    }

    public void saveArmorItems(){
        for(UUID uuid : plugin.getStaffModeManager().getArmorItems().keySet()){
            if(!armorItemsInConfig.containsKey(uuid.toString())){
                armorItemsInConfig.put(uuid.toString(), plugin.getStaffModeManager().getArmorItems().get(uuid));
            }
        }
        plugin.getData().set("armorItems", armorItemsInConfig);
    }

    public void saveInventoryItems(){
        for(UUID uuid : plugin.getStaffModeManager().getInventoryItems().keySet()){
            if(!inventoryItemsInConfig.containsKey(uuid.toString())){
                inventoryItemsInConfig.put(uuid.toString(), plugin.getStaffModeManager().getInventoryItems().get(uuid));
            }
        }
        plugin.getData().set("inventoryItems", inventoryItemsInConfig);
    }

    public void saveVanish(){
        for(UUID uuid : plugin.getStaffModeManager().getVanished()){
            if(!staffModeInConfig.contains(uuid.toString()) && !plugin.getStaffModeManager().getInStaffMode().contains(uuid)) {
                if (!vanishInConfig.contains(uuid.toString())) {
                    vanishInConfig.add(uuid.toString());
                }
            }
        }
        plugin.getData().set("vanish", vanishInConfig);
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
