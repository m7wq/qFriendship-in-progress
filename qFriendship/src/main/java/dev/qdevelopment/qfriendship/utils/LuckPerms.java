package dev.qdevelopment.qfriendship.utils;

import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;

import java.util.UUID;

public class LuckPerms {

    public static String formated(String name){
        User user = LuckPermsProvider.get().getUserManager().getUser(name);
        String prefix = user.getCachedData().getMetaData().getPrefix();
        return prefix+name;
    }

//    public static String formated(UUID uuid){
//        User user = LuckPermsProvider.get().getUserManager().getUser(uuid);
//        String prefix = user.getCachedData().getMetaData().getPrefix();
//        return prefix + user.getUsername();
//    }
}
