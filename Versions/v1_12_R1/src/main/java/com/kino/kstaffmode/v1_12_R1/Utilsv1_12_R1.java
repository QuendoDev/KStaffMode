package com.kino.kstaffmode.v1_12_R1;

import com.kino.kstaffmode.api.utils.AbstractUtils;
import net.minecraft.server.v1_12_R1.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("deprecation")
public class Utilsv1_12_R1 extends AbstractUtils {

    @Override
    public ItemStack getItemInHand(Player p) {
        return p.getInventory().getItemInMainHand();
    }

    @Override
    public double getTPS() {
        return MinecraftServer.getServer().recentTps[0];
    }
}
