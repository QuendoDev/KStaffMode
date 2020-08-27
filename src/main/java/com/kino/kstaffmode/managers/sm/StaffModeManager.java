package com.kino.kstaffmode.managers.sm;

import com.kino.kore.utils.items.ItemBuilder;
import com.kino.kore.utils.messages.MessageUtils;
import com.kino.kstaffmode.KStaffMode;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

@SuppressWarnings("deprecation")
public class StaffModeManager {

    private List<UUID> inStaffMode;
    private List<UUID> vanished;
    private List<UUID> inStaffChat;
    private List<UUID> fly;
    private List<UUID> frozen;
    private Map<UUID, ItemStack[]> armorItems;
    private Map<UUID, ItemStack[]> inventoryItems;
    private Map<ItemStack, Integer> staffModeItems;
    private ItemStack vanishedItem;
    private ItemStack notVanishedItem;
    private KStaffMode plugin;

    public StaffModeManager(KStaffMode plugin){
        this.plugin = plugin;
        this.inStaffMode = new ArrayList<>();
        this.vanished = new ArrayList<>();
        this.inStaffChat = new ArrayList<>();
        this.fly = new ArrayList<>();
        this.frozen = new ArrayList<>();
        this.armorItems = new HashMap<>();
        this.inventoryItems = new HashMap<>();
        this.staffModeItems = new HashMap<>();
        this.registerItems();
    }

    public void clearInventory(Player p){
        p.getInventory().clear();
        p.getInventory().setHelmet(new ItemStack(Material.AIR));
        p.getInventory().setChestplate(new ItemStack(Material.AIR));
        p.getInventory().setLeggings(new ItemStack(Material.AIR));
        p.getInventory().setBoots(new ItemStack(Material.AIR));
    }

    public void giveStaffItems(Player p){
        this.saveItems(p);
        this.clearInventory(p);
        for(ItemStack item : this.staffModeItems.keySet()){
            if(item.getType() == Material.getMaterial(plugin.getConfig().getInt("staffItems.vanish.vanished.id"))){
                if(!isVanished(p)){
                    item = notVanishedItem;
                }
            }
            p.getInventory().setItem(this.staffModeItems.get(item), item);
        }
    }

    public void givePlayerItems(Player p){
        this.clearInventory(p);
        p.getInventory().setContents(this.inventoryItems.get(p.getUniqueId()));
        p.getInventory().setArmorContents(this.armorItems.get(p.getUniqueId()));
        this.inventoryItems.remove(p.getUniqueId());
        this.armorItems.remove(p.getUniqueId());
    }

    public void toogleFreeze (Player staff, Player p) {
        if(frozen.contains(p.getUniqueId())) {
            frozen.remove(p.getUniqueId());
            MessageUtils.sendMessage(p, plugin.getMessages().getString("unfrozen").replace("<player>", staff.getName()));
            MessageUtils.sendMessage(staff, plugin.getMessages().getString("unfreeze").replace("<player>", p.getName()));
        } else {
            if (!p.hasPermission("kstaffmode.bypass.freeze")) {
                frozen.add(p.getUniqueId());
                MessageUtils.sendMessage(p, plugin.getMessages().getString("frozen").replace("<player>", staff.getName()));
                MessageUtils.sendMessage(staff, plugin.getMessages().getString("freeze").replace("<player>", p.getName()));
            } else {
                MessageUtils.sendMessage(staff, plugin.getMessages().getString("noPerms"));
            }
        }
    }

    public void toogleStaffMode(Player p){
        if(p.hasPermission("kstaffmode.staffmode")){
            if(!this.inStaffMode.contains(p.getUniqueId())){
                this.inStaffMode.add(p.getUniqueId());
                this.vanished.add(p.getUniqueId());
                for(Player player : Bukkit.getOnlinePlayers()){
                    if(player.hasPermission("kstaffmode.bypassvanish")){
                        player.showPlayer(p);
                    }else{
                        player.hidePlayer(p);
                    }
                }
                this.fly.add(p.getUniqueId());
                p.setAllowFlight(true);
                p.setFlying(true);
                this.giveStaffItems(p);
                MessageUtils.sendMessage(p, plugin.getMessages().getString("enabledStaffMode"));
            }else{
                this.inStaffMode.remove(p.getUniqueId());
                this.vanished.remove(p.getUniqueId());
                for(Player player : Bukkit.getOnlinePlayers()){
                    player.showPlayer(p);
                }
                this.fly.remove(p.getUniqueId());
                p.setAllowFlight(false);
                p.setFlying(false);
                this.givePlayerItems(p);
                MessageUtils.sendMessage(p, plugin.getMessages().getString("disabledStaffMode"));
            }
        }
    }

    public void toogleStaffChat(Player p){
        if(p.hasPermission("kstaffmode.staffchat")){
            if(!inStaffChat.contains(p.getUniqueId())){
                inStaffChat.add(p.getUniqueId());
                MessageUtils.sendMessage(p, plugin.getMessages().getString("enabledStaffChat"));
            }else{
                inStaffChat.remove(p.getUniqueId());
                MessageUtils.sendMessage(p, plugin.getMessages().getString("disabledStaffChat"));
            }
        }
    }

    public void toogleFly(Player p){
        if(p.hasPermission("kstaffmode.fly")){
            if(!fly.contains(p.getUniqueId())){
                fly.add(p.getUniqueId());
                p.setAllowFlight(true);
                p.setFlying(true);
                MessageUtils.sendMessage(p, plugin.getMessages().getString("enabledFly"));
            }else{
                fly.remove(p.getUniqueId());
                p.setAllowFlight(false);
                p.setFlying(false);
                MessageUtils.sendMessage(p, plugin.getMessages().getString("disabledFly"));
            }
        }
    }

    public void toogleVanish(Player p){
        if(p.hasPermission("kstaffmode.vanish")){
            if(!vanished.contains(p.getUniqueId())){
                vanished.add(p.getUniqueId());
                if(inStaffMode.contains(p.getUniqueId())) {
                    p.getInventory().setItem(plugin.getConfig().getInt("staffItems.vanish.slot"), vanishedItem);
                }
                for(Player player : Bukkit.getOnlinePlayers()){
                    if(player.hasPermission("kstaffmode.bypassvanish")){
                        player.showPlayer(p);
                    }else{
                        player.hidePlayer(p);
                    }
                }
                MessageUtils.sendMessage(p, plugin.getMessages().getString("enabledVanish"));
            }else{
                vanished.remove(p.getUniqueId());
                if(inStaffMode.contains(p.getUniqueId())) {
                    p.getInventory().setItem(plugin.getConfig().getInt("staffItems.vanish.slot"), notVanishedItem);
                }
                for(Player player : Bukkit.getOnlinePlayers()){
                    player.showPlayer(p);
                }
                MessageUtils.sendMessage(p, plugin.getMessages().getString("disabledVanish"));
            }
        }
    }

    public void teleportToRandomplayer(Player p, boolean multiworld) {
        Random r = new Random();

        ArrayList<Player> players = new ArrayList<>(plugin.getServer().getOnlinePlayers());
        players.remove(p);

        if(players.size() > 0) {
            int index = r.nextInt(players.size());
            Player player = players.get(index);
            if(multiworld) {
                p.teleport(player.getLocation());
                MessageUtils.sendMessage(p, plugin.getMessages().getString("teleportedTo").replace("<player>", player.getName()));
            } else {
                if(player.getWorld().equals(p.getWorld())) {
                    p.teleport(player.getLocation());
                    MessageUtils.sendMessage(p, plugin.getMessages().getString("teleportedTo").replace("<player>", player.getName()));
                }
            }
        } else {
            MessageUtils.sendMessage(p, plugin.getMessages().getString("errorRandomTp"));
        }
    }

    public void saveItems(Player p){
        this.armorItems.put(p.getUniqueId(), p.getInventory().getArmorContents());
        this.inventoryItems.put(p.getUniqueId(), p.getInventory().getContents());
    }
    

    public boolean isInStaffMode(Player p){
        return inStaffMode.contains(p.getUniqueId());
    }

    public boolean isVanished(Player p){
        return vanished.contains(p.getUniqueId());
    }

    public boolean isInStaffChat(Player p){
        return inStaffChat.contains(p.getUniqueId());
    }

    public boolean canFly(Player p){
        return fly.contains(p.getUniqueId());
    }

    public List<UUID> getFrozen() {
        return frozen;
    }

    public boolean isFrozen(Player p){
        return frozen.contains(p.getUniqueId());
    }

    public List<UUID> getInStaffMode() {
        return inStaffMode;
    }

    public List<UUID> getFly() {
        return fly;
    }

    public List<UUID> getVanished() {
        return vanished;
    }

    public List<UUID> getInStaffChat() {
        return inStaffChat;
    }

    public Map<ItemStack, Integer> getStaffModeItems() {
        return staffModeItems;
    }

    public Map<UUID, ItemStack[]> getArmorItems() {
        return armorItems;
    }

    public Map<UUID, ItemStack[]> getInventoryItems() {
        return inventoryItems;
    }

    public void registerItems(){
        staffModeItems.clear();
        if(plugin.getConfig().getBoolean("staffItems.navigator.enabled")) {
            ItemStack item = ItemBuilder.createItem(
                    plugin.getConfig().getInt("staffItems.navigator.id"),
                    plugin.getConfig().getInt("staffItems.navigator.amount"),
                    (short) plugin.getConfig().getInt("staffItems.navigator.data"),
                    plugin.getConfig().getString("staffItems.navigator.name"),
                    plugin.getConfig().getStringList("staffItems.navigator.lore")
            );
            staffModeItems.put(item, plugin.getConfig().getInt("staffItems.navigator.slot"));
        }

        if(plugin.getConfig().getBoolean("staffItems.staffList.enabled")) {
            ItemStack item = ItemBuilder.createItem(
                    plugin.getConfig().getInt("staffItems.staffList.id"),
                    plugin.getConfig().getInt("staffItems.staffList.amount"),
                    (short) plugin.getConfig().getInt("staffItems.staffList.data"),
                    plugin.getConfig().getString("staffItems.staffList.name"),
                    plugin.getConfig().getStringList("staffItems.staffList.lore")
            );
            staffModeItems.put(item, plugin.getConfig().getInt("staffItems.staffList.slot"));
        }

        if(plugin.getConfig().getBoolean("staffItems.worldEdit.enabled")) {
            ItemStack item = ItemBuilder.createItem(
                    plugin.getConfig().getInt("staffItems.worldEdit.id"),
                    plugin.getConfig().getInt("staffItems.worldEdit.amount"),
                    (short) plugin.getConfig().getInt("staffItems.worldEdit.data"),
                    plugin.getConfig().getString("staffItems.worldEdit.name"),
                    plugin.getConfig().getStringList("staffItems.worldEdit.lore")
            );
            staffModeItems.put(item, plugin.getConfig().getInt("staffItems.worldEdit.slot"));
        }

        if(plugin.getConfig().getBoolean("staffItems.inspect.enabled")) {
            ItemStack item = ItemBuilder.createItem(
                    plugin.getConfig().getInt("staffItems.inspect.id"),
                    plugin.getConfig().getInt("staffItems.inspect.amount"),
                    (short) plugin.getConfig().getInt("staffItems.inspect.data"),
                    plugin.getConfig().getString("staffItems.inspect.name"),
                    plugin.getConfig().getStringList("staffItems.inspect.lore")
            );
            staffModeItems.put(item, plugin.getConfig().getInt("staffItems.inspect.slot"));
        }

        if(plugin.getConfig().getBoolean("staffItems.freeze.enabled")) {
            ItemStack item = ItemBuilder.createItem(
                    plugin.getConfig().getInt("staffItems.freeze.id"),
                    plugin.getConfig().getInt("staffItems.freeze.amount"),
                    (short) plugin.getConfig().getInt("staffItems.freeze.data"),
                    plugin.getConfig().getString("staffItems.freeze.name"),
                    plugin.getConfig().getStringList("staffItems.freeze.lore")
            );
            staffModeItems.put(item, plugin.getConfig().getInt("staffItems.freeze.slot"));
        }

        if(plugin.getConfig().getBoolean("staffItems.fly.enabled")) {
            ItemStack item = ItemBuilder.createItem(
                    plugin.getConfig().getInt("staffItems.fly.id"),
                    plugin.getConfig().getInt("staffItems.fly.amount"),
                    (short) plugin.getConfig().getInt("staffItems.fly.data"),
                    plugin.getConfig().getString("staffItems.fly.name"),
                    plugin.getConfig().getStringList("staffItems.fly.lore")
            );
            staffModeItems.put(item, plugin.getConfig().getInt("staffItems.fly.slot"));
        }

        if(plugin.getConfig().getBoolean("staffItems.randomtp.enabled")) {
            ItemStack item = ItemBuilder.createItem(
                    plugin.getConfig().getInt("staffItems.randomtp.id"),
                    plugin.getConfig().getInt("staffItems.randomtp.amount"),
                    (short) plugin.getConfig().getInt("staffItems.randomtp.data"),
                    plugin.getConfig().getString("staffItems.randomtp.name"),
                    plugin.getConfig().getStringList("staffItems.randomtp.lore")
            );
            staffModeItems.put(item, plugin.getConfig().getInt("staffItems.randomtp.slot"));
        }

        this.notVanishedItem = ItemBuilder.createItem(
                plugin.getConfig().getInt("staffItems.vanish.visible.id"),
                plugin.getConfig().getInt("staffItems.vanish.visible.amount"),
                (short) plugin.getConfig().getInt("staffItems.vanish.visible.data"),
                plugin.getConfig().getString("staffItems.vanish.visible.name"),
                plugin.getConfig().getStringList("staffItems.vanish.visible.lore")
        );

        this.vanishedItem = ItemBuilder.createItem(
                plugin.getConfig().getInt("staffItems.vanish.vanished.id"),
                plugin.getConfig().getInt("staffItems.vanish.vanished.amount"),
                (short) plugin.getConfig().getInt("staffItems.vanish.vanished.data"),
                plugin.getConfig().getString("staffItems.vanish.vanished.name"),
                plugin.getConfig().getStringList("staffItems.vanish.vanished.lore")
        );

        if(plugin.getConfig().getBoolean("staffItems.vanish.enabled")) {
            staffModeItems.put(vanishedItem, plugin.getConfig().getInt("staffItems.vanish.slot"));
        }
    }
}
