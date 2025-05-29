package dev.qdevelopment.qfriendship.database;

import dev.qdevelopment.qfriendship.entity.Friend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Cache {

    public HashMap<String, List<Friend>> map = new HashMap<>();

    public void putAll(HashMap<String, List<Friend>> map){
        this.map.putAll(map);
    }


    public List<Friend> get(String string){return map.getOrDefault(string, new ArrayList<>());}
}
