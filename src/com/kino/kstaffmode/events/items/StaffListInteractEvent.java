package com.kino.kstaffmode.events.items;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class StaffListInteractEvent extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    private Player player;

    public StaffListInteractEvent (Player player) {
        this.player = player;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    public Player getPlayer() {
        return player;
    }
}
