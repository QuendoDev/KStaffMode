package com.kino.kstaffmode.factory;

import com.kino.kstaffmode.api.utils.AbstractUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;

public class UtilsFactory {

    private static final String SERVER_VERSION = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1);

    private static final Class<?> UTILSCLASS;

    static {
        try {
            UTILSCLASS = Class.forName("com.kino.kstaffmode.v" + SERVER_VERSION + ".Utilsv" + SERVER_VERSION);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("You server version is not compatible with KStaffMode!");
        }
    }

    public static ItemStack getItemInHand (PlayerEvent e) {
        try {
            AbstractUtils utils = (AbstractUtils) UTILSCLASS.getConstructor().newInstance();
            return utils.getItemInHand(e);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
    }
}
