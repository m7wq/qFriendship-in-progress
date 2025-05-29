package dev.qdevelopment.qfriendship.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.connection.ProxiedPlayer;

@AllArgsConstructor
@Getter@Setter
public class Invite {


    ProxiedPlayer sender;
    ProxiedPlayer receiver;
}
