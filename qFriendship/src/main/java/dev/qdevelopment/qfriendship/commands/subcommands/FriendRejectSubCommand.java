package dev.qdevelopment.qfriendship.commands.subcommands;

import dev.qdevelopment.qfriendship.Plugin;
import dev.qdevelopment.qfriendship.commands.impl.SubCommand;
import dev.qdevelopment.qfriendship.entity.Invite;
import dev.qdevelopment.qfriendship.utils.TextHelper;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Optional;

public class FriendRejectSubCommand extends SubCommand {
    @Override
    public String getArgument() {
        return "reject";
    }

    @Override
    public void command(ProxiedPlayer player, String[] args) {
        String target = args[1];



        Optional<Invite> invite = Plugin.getInstance().getInvites().stream().filter(invitess -> invitess.getReceiver().getName().equalsIgnoreCase(player.getName()) && invitess.getSender().getName().equalsIgnoreCase(target)).findAny();

        if (invite.isEmpty())
        {
            TextHelper.sendMessage(player,"&cThe invite that this player invited is un-found or expired.");
            return;
        }

        if (invite.get().getSender().isConnected())
            TextHelper.sendMessage(invite.get().getSender(),"&e"+player.getName()+" &crejected your invite.");

        Plugin.getInstance().getInvites().remove(invite.get());

        TextHelper.sendMessage(player,"&aInvite rejected successfully");


    }
}
