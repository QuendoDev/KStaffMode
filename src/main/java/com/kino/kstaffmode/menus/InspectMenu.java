package com.kino.kstaffmode.menus;

import com.kino.kore.utils.items.ItemBuilder;
import com.kino.kstaffmode.KStaffMode;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.List;

public class InspectMenu {

    private KStaffMode plugin;
    public Inventory inspect;

    public InspectMenu (KStaffMode plugin) {
        this.plugin = plugin;
        this.inspect = Bukkit.createInventory(null, 54, ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("inspect.title")));
    }

    public void open (Player p, Player interacted) {

        if(plugin.getConfig().getBoolean("inspect.contents")) {
            for(int i = 0; i < 36; i++) {
                inspect.setItem(i, interacted.getInventory().getContents()[i]);
            }
        }

        if(plugin.getConfig().getBoolean("inspect.armor")) {
            for(int i = 0; i < interacted.getInventory().getArmorContents().length; i++) {
                inspect.setItem(45+i, interacted.getInventory().getArmorContents()[i]);
            }
        }

        if(plugin.getConfig().getBoolean("inspect.decoration.enabled")) {
            ItemStack item = ItemBuilder.createItem(plugin.getConfig().getInt("inspect.decoration.id"),
                    plugin.getConfig().getInt("inspect.decoration.amount"),
                    (short) plugin.getConfig().getInt("inspect.decoration.data"),
                    plugin.getConfig().getString("inspect.decoration.name"),
                    plugin.getConfig().getStringList("inspect.decoration.lore"));
            for(int i = 36; i < 45; i++) {
                inspect.setItem(i, item);
            }
            for(int i = 49; i < 54 ; i++) {
                inspect.setItem(i, item);
            }
        }

        if(plugin.getConfig().getBoolean("inspect.info.enabled")) {
            List<String> lore = plugin.getConfig().getStringList("inspect.info.lore");
            lore.replaceAll(line -> line.replace(
                    "<food>", interacted.getFoodLevel() + ""
                    ).replace(
                    "<xp>", interacted.getExpToLevel() + ""
                    ).replace("<health>", Math.round(interacted.getHealth() * 100) / 100.0 + ""));
            inspect.setItem(50, ItemBuilder.createItem(plugin.getConfig().getInt("inspect.info.id"),
                    plugin.getConfig().getInt("inspect.info.amount"),
                    (short) plugin.getConfig().getInt("inspect.info.data"),
                    plugin.getConfig().getString("inspect.info.name"), lore
                    ));
        }

        if(plugin.getConfig().getBoolean("inspect.gamemode.enabled")) {
            List<String> lore = plugin.getConfig().getStringList("inspect.gamemode.lore");
            lore.replaceAll(line -> line.replace(
                    "<gm>", interacted.getGameMode() + ""));
            inspect.setItem(51, ItemBuilder.createItem(plugin.getConfig().getInt("inspect.gamemode.id"),
                    plugin.getConfig().getInt("inspect.gamemode.amount"),
                    (short) plugin.getConfig().getInt("inspect.gamemode.data"),
                    plugin.getConfig().getString("inspect.gamemode.name").replace("<gm>", interacted.getGameMode() + ""), lore
            ));
        }

        if(plugin.getConfig().getBoolean("inspect.fly.enabled")) {
            List<String> lore = plugin.getConfig().getStringList("inspect.fly.lore");
            lore.replaceAll(line -> line.replace(
                    "<fly>", interacted.isFlying() + ""));
            inspect.setItem(52, ItemBuilder.createItem(plugin.getConfig().getInt("inspect.fly.id"),
                    plugin.getConfig().getInt("inspect.fly.amount"),
                    (short) plugin.getConfig().getInt("inspect.fly.data"),
                    plugin.getConfig().getString("inspect.fly.name").replace("<fly>", interacted.isFlying() + ""), lore
            ));
        }

        if(plugin.getConfig().getBoolean("inspect.effects.enabled")) {
            List<String> lore = plugin.getConfig().getStringList("inspect.effects.lore");
            String format = plugin.getConfig().getString("inspect.effects.format");
            for(PotionEffect e : interacted.getActivePotionEffects()) {
                lore.add(format.replace("<name>", e.getType().getName()).replace("<level>", e.getAmplifier() + 1 + "").replace("<duration>", duration(e.getDuration())));
            }
            inspect.setItem(53, ItemBuilder.createItem(plugin.getConfig().getInt("inspect.effects.id"),
                    plugin.getConfig().getInt("inspect.effects.amount"),
                    (short) plugin.getConfig().getInt("inspect.effects.data"),
                    plugin.getConfig().getString("inspect.effects.name"), lore
            ));
        }
        p.openInventory(inspect);
    }

    private String duration (int i) {
        int seconds = i / 20;

        int minutes = seconds / 60;

        int secondsLeft = seconds % 60;

        return minutes + ":" + secondsLeft;
    }
}
