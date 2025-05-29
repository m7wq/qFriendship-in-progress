package dev.qdevelopment.qfriendship.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;


public class TextHelper {

    private static String prefix = ChatColor.translateAlternateColorCodes('&', "&eFriends &8›");

    public static void sendMessage(ProxiedPlayer player, String message){
        player.sendMessage(prefix + " " + ChatColor.translateAlternateColorCodes('&',message));
    }

    public static void sendPrivateMessage(ProxiedPlayer player, String message){
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',message));
    }
}
