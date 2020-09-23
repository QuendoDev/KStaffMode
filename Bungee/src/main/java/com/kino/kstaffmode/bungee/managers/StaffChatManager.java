package com.kino.kstaffmode.bungee.managers;

import com.kino.kstaffmode.bungee.files.YMLFile;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("unchecked")
public class StaffChatManager {


    private List<UUID> inStaffChat;

    private YMLFile config;

    public StaffChatManager (YMLFile config) {
        this.config = config;
        inStaffChat = new ArrayList<>();
        loadData();
    }

    public List<UUID> getInStaffChat() {
        return inStaffChat;
    }

    public void addToStaffChat (ProxiedPlayer proxiedPlayer) {
        inStaffChat.add(proxiedPlayer.getUniqueId());
    }

    public void removeOfStaffChat (ProxiedPlayer proxiedPlayer) {
        inStaffChat.remove(proxiedPlayer.getUniqueId());
    }

    public boolean isInStaffChat (ProxiedPlayer player) {
        return inStaffChat.contains(player.getUniqueId());
    }

    public void saveData () {
        config.get().set("inStaffChat", inStaffChat);
    }

    public void loadData () {
        inStaffChat.addAll((List<UUID>) config.get().get("inStaffChat"));
    }


}
