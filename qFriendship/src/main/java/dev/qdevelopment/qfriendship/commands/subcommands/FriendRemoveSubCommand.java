package dev.qdevelopment.qfriendship.commands.subcommands;

import dev.qdevelopment.qfriendship.Plugin;
import dev.qdevelopment.qfriendship.commands.impl.SubCommand;
import dev.qdevelopment.qfriendship.entity.Friend;
import dev.qdevelopment.qfriendship.entity.PlayerBase;
import dev.qdevelopment.qfriendship.utils.TextHelper;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public class FriendRemoveSubCommand extends SubCommand {
    @Override
    public String getArgument() {
        return "remove";
    }

    @Override
    public void command(ProxiedPlayer player, String[] args) {
        String target = args[1];

        PlayerBase playerBase = Plugin.getInstance().getFrManager().getPlayerBase(player);

        if (!playerBase.getFriends().stream().anyMatch(friend -> friend.getName().equalsIgnoreCase(target)))
        {
            TextHelper.sendMessage(player,"&cThis is not a friend with you.");
            return;
        }

        playerBase.removeFriend(target);
        UUID targetUUID = playerBase.getFriends().stream().filter(friend -> friend.getName().equalsIgnoreCase(target)).findFirst().get().getUuid();
        Plugin.getInstance().getMainCache().map.get(targetUUID.toString()).remove(new Friend(player.getUniqueId(),player.getName()));
        TextHelper.sendMessage(player,"&cFriend has been removed successfully.");






    }
}
