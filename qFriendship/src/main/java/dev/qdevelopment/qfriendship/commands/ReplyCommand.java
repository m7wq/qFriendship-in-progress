package dev.qdevelopment.qfriendship.commands;

import dev.qdevelopment.qfriendship.Plugin;
import dev.qdevelopment.qfriendship.utils.LuckPerms;
import dev.qdevelopment.qfriendship.utils.TextHelper;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.UUID;

public class ReplyCommand extends Command {
    public ReplyCommand(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (!(sender instanceof ProxiedPlayer player))return;

        if (args[0].equalsIgnoreCase(sender.getName())){

            TextHelper.sendMessage(player,"&cYou cant use this command at yourself");
            return;
        }


        UUID targetUUID = UUID.fromString(Plugin.getInstance().getLatestCache().get(player.getUniqueId().toString()));
        ProxiedPlayer targetPlr = ProxyServer.getInstance().getPlayer(targetUUID);

        if (targetPlr == null){
            TextHelper.sendMessage(player,"&cYou got no messages until now to reply.");
            return;
        }




        String message = String.join(" ",args);

        Plugin.getInstance().getLatestCache().put(targetPlr.getUniqueId().toString(),player.getUniqueId().toString());

        TextHelper.sendPrivateMessage(targetPlr, "&e&lREPLY &8┃ " + LuckPerms.formated(player.getName()) + " &8» &7" + message);
        TextHelper.sendPrivateMessage(player, "&e&lREPLY &8┃ " + LuckPerms.formated(player.getName()) + " &8» &7" + message);
    }
}
