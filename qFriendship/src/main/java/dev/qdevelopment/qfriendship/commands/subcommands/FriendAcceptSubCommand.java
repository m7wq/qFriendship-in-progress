package dev.qdevelopment.qfriendship.commands.subcommands;

import dev.qdevelopment.qfriendship.Plugin;
import dev.qdevelopment.qfriendship.commands.impl.SubCommand;
import dev.qdevelopment.qfriendship.entity.Friend;
import dev.qdevelopment.qfriendship.entity.Invite;
import dev.qdevelopment.qfriendship.entity.PlayerBase;
import dev.qdevelopment.qfriendship.utils.LuckPerms;
import dev.qdevelopment.qfriendship.utils.TextHelper;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Optional;

public class FriendAcceptSubCommand extends SubCommand {
    @Override
    public String getArgument() {
        return "accept";
    }

    @Override
    public void command(ProxiedPlayer player, String[] args) {

        String target = args[1];



        Optional<Invite> invite = Plugin.getInstance().getInvites().stream().filter(invitess -> invitess.getReceiver().getName().equalsIgnoreCase(player.getName()) && invitess.getSender().getName().equalsIgnoreCase(target)).findFirst();

        if (invite.isEmpty())
        {
            TextHelper.sendMessage(player,"&cThe invite that this player invited is un-found or expired.");
            return;
        }

        PlayerBase targetBase =  new PlayerBase(invite.get().getSender()),
                playerBase = new PlayerBase(player);

        targetBase.addFriend(new Friend(player.getUniqueId(),player.getName()));
        playerBase.addFriend(new Friend(invite.get().getSender().getUniqueId(),invite.get().getSender().getName()));

        if (invite.get().getSender().isConnected())
            TextHelper.sendMessage(invite.get().getSender(),"&e"+ LuckPerms.formated(player.getName()) + " &aaccepted your invite you are new friends now!");

    }
}
