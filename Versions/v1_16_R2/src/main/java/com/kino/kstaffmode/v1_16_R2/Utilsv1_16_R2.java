package com.kino.kstaffmode.v1_16_R2;

import com.kino.kstaffmode.api.utils.AbstractUtils;
import net.minecraft.server.v1_16_R2.MinecraftServer;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("deprecation")
public class Utilsv1_16_R2 implements AbstractUtils {

    @Override
    public ItemStack getItemInHand(PlayerEvent e) {
        if(e instanceof PlayerInteractEntityEvent) {
            if (((PlayerInteractEntityEvent) e).getHand() == EquipmentSlot.HAND) {
                return e.getPlayer().getInventory().getItemInMainHand();
            }
        }
        if(e instanceof PlayerInteractEvent) {
            if(((PlayerInteractEvent) e).getHand() == EquipmentSlot.HAND) {
                return e.getPlayer().getInventory().getItemInMainHand();
            }
        }

        return new ItemStack(Material.AIR);
    }

    @Override
    public double getTPS() {
        return MinecraftServer.getServer().recentTps[0];
    }

    @Override
    public String getInventoryName(InventoryClickEvent inventoryClickEvent) {
        return inventoryClickEvent.getView().getTitle();
    }
}
