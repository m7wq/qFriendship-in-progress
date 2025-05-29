package dev.qdevelopment.qfriendship;


import dev.qdevelopment.qfriendship.commands.DirectMessageCommand;
import dev.qdevelopment.qfriendship.commands.FriendsCommand;
import dev.qdevelopment.qfriendship.commands.ReplyCommand;
import dev.qdevelopment.qfriendship.commands.impl.SubCommand;
import dev.qdevelopment.qfriendship.commands.subcommands.*;
import dev.qdevelopment.qfriendship.database.Cache;
import dev.qdevelopment.qfriendship.database.Database;
import dev.qdevelopment.qfriendship.entity.Friend;
import dev.qdevelopment.qfriendship.entity.Invite;
import dev.qdevelopment.qfriendship.managers.Config;
import dev.qdevelopment.qfriendship.managers.FrManager;
import lombok.Getter;
import lombok.SneakyThrows;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;
import org.checkerframework.checker.units.qual.C;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class Plugin extends net.md_5.bungee.api.plugin.Plugin {


    // Cache
    private @Getter Cache mainCache = new Cache();
    //                      receiver,sender
    private @Getter HashMap<String, String> latestCache = new HashMap<>();
    //
    private @Getter List<Invite> invites = new ArrayList<>();
    //
    private @Getter HashMap<ProxiedPlayer, HashMap<Integer,List<Friend>>> friendsList = new HashMap<>();
    private @Getter HashMap<ProxiedPlayer, Integer> currentPage = new HashMap<>();


    private Config configManager;
    private @Getter FrManager frManager;
    private static @Getter Plugin instance;
    private Database database;
    private @Getter BungeeAudiences audiences;




    @Override
    public void onEnable() {
        instance = this;
        this.audiences = BungeeAudiences.create(this);
        this.configManager = new Config(this);
        this.frManager = new FrManager(this, getConfig());


        try {

            this.database = new Database(frManager.getDataSource());
            this.database.init();
            mainCache.putAll(database.deserialize());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        command();

    }

    public void command(){

        SubCommand[] subCommands = new SubCommand[]{
                new FriendAcceptSubCommand(),
                new FriendAddSubCommand(),
                new FriendRejectSubCommand(),
                new FriendRemoveSubCommand(),
                new FriendListSubCommand()
        };

        Command friendCommand = new FriendsCommand(subCommands);
        Command msg = new DirectMessageCommand("msg"),reply = new ReplyCommand("r");

        getProxy().getPluginManager().registerCommand(this,friendCommand);
        getProxy().getPluginManager().registerCommand(this,msg);
        getProxy().getPluginManager().registerCommand(this,reply);

    }



    @Override
    public void onDisable() {
        if (frManager != null) {
            frManager.close();
        }

        database.serialize(mainCache.map);
    }

    public Configuration getConfig() {
        return configManager.getConfiguration();
    }
}
