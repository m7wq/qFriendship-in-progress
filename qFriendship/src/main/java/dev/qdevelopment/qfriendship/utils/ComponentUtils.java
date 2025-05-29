package dev.qdevelopment.qfriendship.utils;

import dev.qdevelopment.qfriendship.Plugin;
import dev.qdevelopment.qfriendship.entity.Friend;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ComponentUtils {

    public static Component friendComponent(Friend friend){


        Component mainComponent = Component.text("●");

        ProxiedPlayer player = ProxyServer.getInstance().getPlayer(friend.getUuid());

        if (player.isConnected() && player != null){
            mainComponent.color(NamedTextColor.GREEN);
        }else {
            mainComponent.color(NamedTextColor.RED);
        }

        mainComponent.appendSpace();

        mainComponent.append(Component.text(LuckPerms.formated(friend.getName())));

        mainComponent.appendSpace();

        mainComponent.append(Component.text("» " , NamedTextColor.DARK_GRAY));

        if (player.isConnected() && player != null){
            mainComponent.append(Component.text(player.getServer().getInfo().getName(), NamedTextColor.YELLOW));
        }else {
            mainComponent.append(Component.text("Offline",NamedTextColor.RED));
        }

        mainComponent.hoverEvent(HoverEvent.showText(Component.text("Click to message")));
        mainComponent.clickEvent(ClickEvent.runCommand("/msg "+friend.getName()+" "));

        return mainComponent;


    }

    public static Component previousPage(ProxiedPlayer player){
        int previousPageint = Plugin.getInstance().getCurrentPage().get(player)-1;

        if (previousPageint < 1)
            return Component.text(" ");

        Component component = Component.text("[<─]").color(NamedTextColor.AQUA);
        component.hoverEvent(HoverEvent.showText(Component.text("Previous page", NamedTextColor.WHITE)));
        component.clickEvent(ClickEvent.runCommand("/list "+previousPageint));
        return component;
    }




    public static Component nextPage(ProxiedPlayer player){
        int nextPageint = Plugin.getInstance().getCurrentPage().get(player)-1;

        int friendsAmount = Plugin.getInstance().getMainCache().get(player.getUniqueId().toString()).size();

        if (nextPageint == ((friendsAmount + 8 - 1) / 8)+1 || nextPageint == -1)
            return Component.text(" ");



        Component component = Component.text("[─>]").color(NamedTextColor.AQUA);
        component.hoverEvent(HoverEvent.showText(Component.text("Next page", NamedTextColor.WHITE)));
        component.clickEvent(ClickEvent.runCommand("/list "+nextPageint));
        return component;
    }
}
