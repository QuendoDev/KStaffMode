package com.kino.kstaffmode.v1_12_R1;

import com.kino.kstaffmode.api.utils.AbstractUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Utilsv1_12_R1 extends AbstractUtils {

    @Override
    public ItemStack getItemInHand(Player p) {
        return p.getInventory().getItemInMainHand();
    }
}
