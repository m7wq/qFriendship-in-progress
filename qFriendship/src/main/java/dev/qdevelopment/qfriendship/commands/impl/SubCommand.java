package dev.qdevelopment.qfriendship.commands.impl;

import net.md_5.bungee.api.connection.ProxiedPlayer;


public abstract class SubCommand {


    public abstract String getArgument();

    public abstract void command(ProxiedPlayer player, String args[]);


}
