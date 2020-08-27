package com.kino.kstaffmode.managers.menus;

import org.bukkit.entity.Player;

public class PlayerInventory {

    private Player player;
    private int page;

    public PlayerInventory (Player p, int page) {
        this.player = p;
        this.page = page;
    }

    public Player getPlayer() {
        return player;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
