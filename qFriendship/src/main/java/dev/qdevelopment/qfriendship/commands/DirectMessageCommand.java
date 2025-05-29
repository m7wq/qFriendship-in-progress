package dev.qdevelopment.qfriendship.commands;

import dev.qdevelopment.qfriendship.Plugin;
import dev.qdevelopment.qfriendship.utils.LuckPerms;
import dev.qdevelopment.qfriendship.utils.TextHelper;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class DirectMessageCommand extends Command {
    public DirectMessageCommand(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {


        if (!(sender instanceof ProxiedPlayer player))return;

        if (args[0].equalsIgnoreCase(sender.getName())){

            TextHelper.sendMessage(player,"&cYou cant use this command at yourself");
            return;
        }

        String target = args[0];

        ProxiedPlayer targetPlr = ProxyServer.getInstance().getPlayer(target);

        if (targetPlr == null){
            TextHelper.sendMessage(player,"&cThis player is offline");
            return;
        }


        StringBuilder messageBuilder = new StringBuilder();

        for (int i = 1; i < args.length; i++){
            messageBuilder.append(args[i]).append(" ");
        }

        String message = messageBuilder.toString();

        Plugin.getInstance().getLatestCache().put(targetPlr.getUniqueId().toString(),player.getUniqueId().toString());

        TextHelper.sendPrivateMessage(targetPlr, "&e&lPRIVATE &8┃ " + LuckPerms.formated(player.getName()) + " &8» &7" + message);
        TextHelper.sendPrivateMessage(player, "&e&lPRIVATE &8┃ " + LuckPerms.formated(player.getName()) + " &8» &7" + message);
    }
}
