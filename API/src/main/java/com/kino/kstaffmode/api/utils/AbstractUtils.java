package com.kino.kstaffmode.api.utils;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public interface AbstractUtils {

    ItemStack getItemInHand (PlayerEvent e);

    double getTPS();
}
