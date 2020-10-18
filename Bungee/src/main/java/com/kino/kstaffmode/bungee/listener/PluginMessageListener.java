package com.kino.kstaffmode.bungee.listener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.kino.kstaffmode.bungee.managers.StaffChatManager;
import lombok.AllArgsConstructor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

@AllArgsConstructor
public class PluginMessageListener implements Listener {


    private StaffChatManager staffChatManager;

    @SuppressWarnings("UnstableApiUsage")
    @EventHandler
    public void on(PluginMessageEvent event)
    {
        if (event.isCancelled()) {
            return;
        }
        
        if ( !event.getTag().equalsIgnoreCase( "kino:kstaffmode" ) )
        {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput( event.getData() );
        String subChannel = in.readUTF();
        if ( subChannel.equalsIgnoreCase( "checkIfInStaffchat" ) )
        {
            if ( event.getReceiver() instanceof ProxiedPlayer)
            {
                ProxiedPlayer receiver = (ProxiedPlayer) event.getReceiver();

                staffChatManager.sendPluginMessageStaffChatScore(receiver);
            }
        }
    }
}
