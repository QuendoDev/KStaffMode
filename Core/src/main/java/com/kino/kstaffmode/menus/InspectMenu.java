package com.kino.kstaffmode.menus;

import com.kino.kore.utils.items.KMaterial;
import com.kino.kore.utils.items.builder.ItemBuilder;
import com.kino.kstaffmode.KStaffMode;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.List;

public class InspectMenu {

    
    public Inventory inspect;
    private FileConfiguration config;

    public InspectMenu (FileConfiguration config) {
        this.config = config;
        this.inspect = Bukkit.createInventory(null, 54, ChatColor.translateAlternateColorCodes('&', config.getString("inspect.title")));
    }

    public void open (Player p, Player interacted) {

        if(config.getBoolean("inspect.contents")) {
            for(int i = 0; i < 36; i++) {
                inspect.setItem(i, interacted.getInventory().getContents()[i]);
            }
        }

        if(config.getBoolean("inspect.armor")) {
            for(int i = 0; i < interacted.getInventory().getArmorContents().length; i++) {
                inspect.setItem(45+i, interacted.getInventory().getArmorContents()[i]);
            }
        }

        if(config.getBoolean("inspect.decoration.enabled")) {
            ItemStack item = buildItem("decoration", config.getStringList("inspect.decoration.lore"));
            for(int i = 36; i < 45; i++) {
                inspect.setItem(i, item);
            }
            for(int i = 49; i < 54 ; i++) {
                inspect.setItem(i, item);
            }
        }

        if(config.getBoolean("inspect.info.enabled")) {
            List<String> lore = config.getStringList("inspect.info.lore");
            lore.replaceAll(line -> line.replace(
                    "<food>", interacted.getFoodLevel() + ""
                    ).replace(
                    "<xp>", interacted.getExpToLevel() + ""
                    ).replace("<health>", Math.round(interacted.getHealth() * 100) / 100.0 + ""));
            inspect.setItem(50, buildItem("info", lore));
        }

        if(config.getBoolean("inspect.gamemode.enabled")) {
            List<String> lore = config.getStringList("inspect.gamemode.lore");
            lore.replaceAll(line -> line.replace(
                    "<gm>", interacted.getGameMode() + ""));
            inspect.setItem(51, buildItem("gamemode", lore, config.getString("inspect.gamemode.name").replace("<gm>", interacted.getGameMode() + "")));
        }

        if(config.getBoolean("inspect.fly.enabled")) {
            List<String> lore = config.getStringList("inspect.fly.lore");
            lore.replaceAll(line -> line.replace(
                    "<fly>", interacted.isFlying() + ""));
            inspect.setItem(52, buildItem("fly", lore, config.getString("inspect.fly.name").replace("<fly>", interacted.isFlying() + "")));
        }

        if(config.getBoolean("inspect.effects.enabled")) {
            List<String> lore = config.getStringList("inspect.effects.lore");
            String format = config.getString("inspect.effects.format");
            for(PotionEffect e : interacted.getActivePotionEffects()) {
                lore.add(format.replace("<name>", e.getType().getName()).replace("<level>", e.getAmplifier() + 1 + "").replace("<duration>", duration(e.getDuration())));
            }
            inspect.setItem(53, buildItem("effects", lore));
        }
        p.openInventory(inspect);
    }

    private String duration (int i) {
        int seconds = i / 20;

        int minutes = seconds / 60;

        int secondsLeft = seconds % 60;

        return minutes + ":" + secondsLeft;
    }

    private ItemStack buildItem (String key, List<String> lore) {
        return buildItem(key, lore, config.getString("inspect." + key + ".name"));
    }

    private ItemStack buildItem (String key, List<String> lore, String name) {
        return config.getString("inspect." + key + ".skull.type").equalsIgnoreCase("OWNER") ?
                ItemBuilder.newSkullBuilder(KMaterial.PLAYER_HEAD.name(), config.getInt("inspect." + key + ".amount"))
                        .owner(config.getString("inspect." + key + ".skull.owner"))
                        .name(name)
                        .lore(lore).build()
                : config.getString("inspect." + key + ".skull.type").equalsIgnoreCase("URL") ?

                ItemBuilder.newSkullBuilder(KMaterial.PLAYER_HEAD.name(), config.getInt("inspect." + key + ".amount"))
                        .url(config.getString("inspect." + key + ".skull.owner"))
                        .name(name)
                        .lore(lore).build()

                : ItemBuilder.newBuilder(config.getString("inspect." + key + ".id"), config.getInt("inspect." + key + ".amount"))
                .name(name)
                .lore(lore).build();
    }
}
