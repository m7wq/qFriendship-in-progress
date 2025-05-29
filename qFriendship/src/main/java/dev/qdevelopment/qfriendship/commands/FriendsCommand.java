package dev.qdevelopment.qfriendship.commands;

import dev.qdevelopment.qfriendship.commands.impl.SubCommand;
import dev.qdevelopment.qfriendship.utils.TextHelper;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FriendsCommand extends Command {

    List<SubCommand> subCommands;

    public FriendsCommand(SubCommand...subs){
        super("friends","","f","friend");
        subCommands = Arrays.asList(subs);
    }


    @Override
    public void execute(CommandSender sender, String[] args) {

        if(!(sender instanceof ProxiedPlayer player))return;



        if (args.length < 2 && !args[0].equalsIgnoreCase("list")){
            TextHelper.sendMessage(player, "&cUsage: /f [list|add|remove|accept|reject]");
            TextHelper.sendMessage(player, "&cUsage: /msg | /r");
            return;
        }


        if (args.length >= 2)
            if (args[1].equalsIgnoreCase(sender.getName())){

                TextHelper.sendMessage(player,"&cYou cant use this command at yourself");
                return;
            }


        for(SubCommand subCommand : subCommands){
            if (args[0].equalsIgnoreCase(subCommand.getArgument())){
                subCommand.command(player,args);
                return;
            }
        }


        TextHelper.sendMessage(player, "&cUsage: /f [list|add|remove|accept|reject]");
        TextHelper.sendMessage(player, "&cUsage: /msg | /r");


    }
}
