package com.kino.kstaffmode.bungee.managers;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.kino.kore.utils.messages.MessageUtils;
import com.kino.kstaffmode.bungee.files.YMLFile;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
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
        List<String> list = new ArrayList<>();
        for (UUID uuid : inStaffChat) {
            list.add(uuid.toString());
        }
        config.get().set("inStaffChat", list);
        config.save();
    }

    public void loadData () {
        if (config.get().contains("inStaffChat")) {
            for (String uuid :  config.get().getStringList("inStaffChat")) {
                inStaffChat.add(UUID.fromString(uuid));
            }
            config.get().set("inStaffChat", null);
            config.save();
        }
    }

    public void addStaffChat (ProxiedPlayer p) {
        if(p.hasPermission("kstaffmode.staffchat")){
            if(inStaffChat.contains(p.getUniqueId())){
                p.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', config.get().getString("enabledStaffChat"))));
            }
        }
    }

    public void toogleStaffChat(ProxiedPlayer p){
        if(p.hasPermission("kstaffmode.staffchat")){
            if(!inStaffChat.contains(p.getUniqueId())){
                inStaffChat.add(p.getUniqueId());
                p.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', config.get().getString("enabledStaffChat"))));
            }else{
                inStaffChat.remove(p.getUniqueId());
                p.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', config.get().getString("disabledStaffChat"))));
            }
            sendPluginMessageStaffChatScore(p);
        }

    }

    @SuppressWarnings("UnstableApiUsage")
    public void sendPluginMessageStaffChatScore(ProxiedPlayer player)
    {
        Collection<ProxiedPlayer> networkPlayers = ProxyServer.getInstance().getPlayers();
        if ( networkPlayers == null || networkPlayers.isEmpty() || !networkPlayers.contains(player))
        {
            return;
        }
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF( "staffchatScore");
        out.writeUTF(player.getName());
        out.writeBoolean(inStaffChat.contains(player.getUniqueId()));

        // we send the data to the server
        // using ServerInfo the packet is being queued if there are no players in the server
        // using only the server to send data the packet will be lost if no players are in it
        player.getServer().getInfo().sendData( "kino:kstaffmode", out.toByteArray() );
    }


}
