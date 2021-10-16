package com.kino.kstaffmode.listener.bungee;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.kino.kstaffmode.managers.staffmode.StaffModeManager;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

@AllArgsConstructor
public class PluginMessagesListener implements PluginMessageListener
{

    private StaffModeManager staffModeManager;

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void onPluginMessageReceived(String s, Player player, byte[] bytes) {
        if ( !s.equalsIgnoreCase( "kino:kstaffmode" ) )
        {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput( bytes );
        String subChannel = in.readUTF();
        if ( subChannel.equalsIgnoreCase( "staffchatScore" ) )
        {
            boolean inStaffChat = in.readBoolean();

            staffModeManager.setStaffChatScoreBungee(inStaffChat);
        }
    }
}
