package com.kino.kstaffmode.commands.gamemode.arguments;

import com.kino.kore.utils.command.CommandArgument;
import com.kino.kore.utils.messages.MessageUtils;
import com.kino.kstaffmode.KStaffMode;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SuppressWarnings("deprecation")
public class SpectatorArgument extends CommandArgument {

    private KStaffMode plugin;
    public SpectatorArgument(KStaffMode plugin) {
        super("spectator", "kstaffmode.commands.gamemode.spectator",
                "Use this argument to change your gamemode to spectator.",
                new String[]{"3, sp, spec, espectador"});
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(args.length == 2){
            if(Bukkit.getPlayer(args[1]) !=null){
                Player pl = Bukkit.getPlayer(args[1]);
                if(sender instanceof Player){
                    Player p = (Player) sender;
                    if(!pl.getName().equals(p.getName())){
                        pl.setGameMode(GameMode.valueOf("SPECTATOR"));
                        MessageUtils.sendMessage(p, plugin.getMessages().getString("gm-spectator-other").replace("<player>", pl.getName()));
                        MessageUtils.sendMessage(pl, plugin.getMessages().getString("gm-spectator-other1").replace("<player>", p.getName()));
                        for(Player player : Bukkit.getServer().getOnlinePlayers()){
                            if(player.hasPermission(getPermission())){
                                MessageUtils.sendMessage(player, plugin.getMessages().getString("gm-spectator-other-all").replace(
                                        "<sender>", p.getName()
                                ).replace("<player>", pl.getName()));
                            }
                        }
                        return true;
                    }else {
                        MessageUtils.sendMessage(sender, "&cCorrect usage: " + getUsage(label));
                        return false;
                    }
                }else{
                    pl.setGameMode(GameMode.valueOf("SPECTATOR"));
                    MessageUtils.sendMessage(sender, plugin.getMessages().getString("gm-spectator-other").replace("<player>", pl.getName()));
                    MessageUtils.sendMessage(pl, plugin.getMessages().getString("gm-spectator-other1").replace("<player>", sender.getName()));
                    for(Player player : Bukkit.getServer().getOnlinePlayers()){
                        if(player.hasPermission(getPermission())){
                            MessageUtils.sendMessage(player, plugin.getMessages().getString("gm-spectator-other-all").replace(
                                    "<sender>", sender.getName()
                            ).replace("<player>", pl.getName()));
                        }
                    }
                    return true;
                }
            }else{
                MessageUtils.sendMessage(sender, plugin.getMessages().getString("playerNotOnline"));
                return false;
            }
        }
        else if (args.length == 1) {
            if(sender instanceof Player) {
                Player p = (Player) sender;
                p.setGameMode(GameMode.valueOf("SPECTATOR"));
                MessageUtils.sendMessage(p, plugin.getMessages().getString("gm-spectator"));
                for(Player player : Bukkit.getServer().getOnlinePlayers()){
                    if(player.hasPermission(getPermission())){
                        MessageUtils.sendMessage(player, plugin.getMessages().getString("gm-spectator-all").replace(
                                "<player>", p.getName()
                        ));
                    }
                }
                return true;
            }else{
                MessageUtils.sendMessage(sender, "&cThis sub-command is only for players!");
                return false;
            }
        }else{
            MessageUtils.sendMessage(sender, "&cCorrect usage: " + getUsage(label));
            return false;
        }

    }

    @Override
    public String getUsage(String label) {
        return '/' + label + ' ' + getName();
    }
}
