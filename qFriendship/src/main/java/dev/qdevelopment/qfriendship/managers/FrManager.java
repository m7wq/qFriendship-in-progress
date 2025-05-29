package dev.qdevelopment.qfriendship.managers;



import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dev.qdevelopment.qfriendship.Plugin;

import dev.qdevelopment.qfriendship.entity.Invite;
import dev.qdevelopment.qfriendship.entity.PlayerBase;
import dev.qdevelopment.qfriendship.utils.LuckPerms;
import dev.qdevelopment.qfriendship.utils.TextHelper;
import lombok.Getter;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.kyori.adventure.text.Component;


import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.config.Configuration;

import java.awt.*;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


public class FrManager {

    private final Plugin plugin;
    private @Getter HikariDataSource dataSource;

    public FrManager(Plugin plugin, Configuration config) {
        this.plugin = plugin;
        setupDataSource(config);
    }

    private void setupDataSource(Configuration cf) {
        String host = cf.getString("database.host");
        int port = cf.getInt("database.port");
        String db = cf.getString("database.name");
        String user = cf.getString("database.username");
        String pass = cf.getString("database.password");

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + db + "?useSSL=false&autoReconnect=true");
        hikariConfig.setUsername(user);
        hikariConfig.setPassword(pass);

        hikariConfig.setMaximumPoolSize(10);
        hikariConfig.setMinimumIdle(2);
        hikariConfig.setIdleTimeout(30000);
        hikariConfig.setMaxLifetime(1800000);
        hikariConfig.setConnectionTimeout(30000);

        dataSource = new HikariDataSource(hikariConfig);
        plugin.getLogger().info("Friends Connection pool initialized.");
    }

    public void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            plugin.getLogger().info("Friends Connection pool closed.");
        }
    }


    public PlayerBase getPlayerBase(ProxiedPlayer player){
        return new PlayerBase(player);
    }

    public void sendInvite(ProxiedPlayer from,ProxiedPlayer to){


        Invite invite = new Invite(from,to);

        if (Plugin.getInstance().getInvites().stream().anyMatch(invitep -> invitep.getSender().getName().equalsIgnoreCase(from.toString()) && invitep.getReceiver().getName().equalsIgnoreCase(to.getName()))){
            TextHelper.sendMessage(from,"&cYou already invited a request to this player");
            return;
        }

        Plugin.getInstance().getInvites().add(invite);

        TextHelper.sendMessage(from,"&aInvite sent successfully, the invite will be expired in 30 seconds");


        BungeeAudiences audiences = Plugin.getInstance().getAudiences();



        Audience audience = audiences.player(to);

        String acceptCommand = "/f accept "+from.getName();
        String rejectCommand = "/f reject "+from.getName();




        Component component = Component.text(from.getName() + " ", NamedTextColor.YELLOW)
                .append(Component.text("sent you a friend request.", NamedTextColor.GRAY))
                .appendNewline()
                .append(
                        Component.text("[ACCEPT]", NamedTextColor.GREEN)
                                .hoverEvent(HoverEvent.showText(Component.text("Accept the friend request", NamedTextColor.GREEN)))
                                .clickEvent(ClickEvent.runCommand(acceptCommand))
                )
                .appendSpace()
                .append(
                        Component.text("[REJECT]", NamedTextColor.RED)
                                .hoverEvent(HoverEvent.showText(Component.text("Reject the friend request", NamedTextColor.RED)))
                                .clickEvent(ClickEvent.runCommand(rejectCommand))
                );

        audience.sendMessage(component);


        ProxyServer.getInstance().getScheduler().schedule(
                Plugin.getInstance(),
                new Runnable() {
            @Override
            public void run() {
                if (Plugin.getInstance().getInvites().contains(invite)) {
                    Plugin.getInstance().getInvites().remove(invite);
                    if (from.isConnected())
                        TextHelper.sendMessage(from, "&cYour invite to &e" + LuckPerms.formated(to.getName()) + " &chas expired!");
                }
            }
        },
                30L,
                TimeUnit.SECONDS);


    }



}
