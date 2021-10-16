package com.kino.kstaffmode;

import com.kino.kore.utils.loaders.Loader;
import com.kino.kstaffmode.managers.staffmode.StaffModeManager;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;

@AllArgsConstructor
public class TaskLoader implements Loader {

    private KStaffMode plugin;
    private StaffModeManager staffModeManager;

    @Override
    public void load() {
        Bukkit.getScheduler().runTaskTimer(plugin, runnable(), 0, 20L);
    }

    private Runnable runnable (){
        return () -> staffModeManager.setTPS();
    }
}
