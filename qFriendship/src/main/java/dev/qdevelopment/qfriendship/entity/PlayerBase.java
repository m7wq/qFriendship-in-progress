package dev.qdevelopment.qfriendship.entity;

import dev.qdevelopment.qfriendship.Plugin;
import lombok.AllArgsConstructor;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class PlayerBase {

    ProxiedPlayer player;

    public void addFriend(Friend target){
        Plugin.getInstance().getMainCache().get(player.getUniqueId().toString()).add(target);
    }

    public void removeFriend(String target){
        UUID uuid = Plugin.getInstance().getMainCache().get(player.getUniqueId().toString())
                .stream().filter(friend -> friend.getName().equalsIgnoreCase(target)).findFirst().get().getUuid();

        Plugin.getInstance().getMainCache().get(player.getUniqueId().toString()).remove(new Friend(uuid,target));
    }

    public List<Friend> getFriends(){
        return Plugin.getInstance().getMainCache().get(player.getUniqueId().toString());
    }

}
