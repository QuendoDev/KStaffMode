package com.kino.kstaffmode.v1_8_R3;

import com.kino.kstaffmode.api.utils.AbstractUtils;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class Utilsv1_8_R3 extends AbstractUtils {


    @Override
    public ItemStack getItemInHand(PlayerEvent e) {
        return e.getPlayer().getItemInHand();
    }


    @Override
    public double getTPS() {
        return MinecraftServer.getServer().recentTps[0];
    }
}
