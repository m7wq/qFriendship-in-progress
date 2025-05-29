package dev.qdevelopment.qfriendship.commands.subcommands;

import dev.qdevelopment.qfriendship.Plugin;
import dev.qdevelopment.qfriendship.commands.impl.SubCommand;
import dev.qdevelopment.qfriendship.entity.Friend;
import dev.qdevelopment.qfriendship.entity.PlayerBase;
import dev.qdevelopment.qfriendship.utils.TextHelper;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;


public class FriendAddSubCommand extends SubCommand {
    @Override
    public String getArgument() {
        return "add";
    }

    @Override
    public void command(ProxiedPlayer player, String[] args) {
        String target = args[1];

        ProxiedPlayer targetPlr = ProxyServer.getInstance().getPlayer(target);

        if (targetPlr != null){
            PlayerBase base = Plugin.getInstance().getFrManager().getPlayerBase(player);

            if (base.getFriends().contains(new Friend(targetPlr.getUniqueId(), targetPlr.getName()))){
                TextHelper.sendMessage(player, "&cYou are already friend with this player.");
                return;
            }



            Plugin.getInstance().getFrManager().sendInvite(player,targetPlr);


        }else {
            TextHelper.sendMessage(player,"&cThis player is offline.");
        }
    }
}
