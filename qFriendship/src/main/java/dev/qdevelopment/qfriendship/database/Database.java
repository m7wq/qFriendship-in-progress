package dev.qdevelopment.qfriendship.database;

import com.zaxxer.hikari.HikariDataSource;
import dev.qdevelopment.qfriendship.Plugin;
import dev.qdevelopment.qfriendship.entity.Friend;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;


import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Database {

    Connection connection;

    public Database(HikariDataSource hikariDataSource)throws SQLException{
        connection = hikariDataSource.getConnection();
    }

    public Database() throws SQLException {}

    public void init(){

        try{
            PreparedStatement friendsTable = connection.prepareStatement("CREATE TABLE IF NOT EXISTS qfriendship_friends(" +
                    "main VARCHAR(36) PRIMARY KEY," +
                    "friend VARCHAR(36)," +
                    "friend_name VARCHAR(36)" +
                    ");");

            friendsTable.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }


    }

    public HashMap<String,List<Friend>> deserialize(){
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT main FROM qfriendship_friends");

            ResultSet resultSet = statement.executeQuery();
            HashMap<String,List<Friend>> map = new HashMap<>();
            while (resultSet.next()){
                String mainUUID = resultSet.getString("main");
                map.put(mainUUID, getFriends(mainUUID));
            }
            return map;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return new HashMap<>();
    }


    private List<Friend> getFriends(String uuid){

        try {
            PreparedStatement getFriends = connection.prepareStatement("SELECT * FROM qfriendship_friends WHERE main = ?");
            getFriends.setString(1,uuid);

            ResultSet resultSet = getFriends.executeQuery();
            List<Friend> friends = new ArrayList<>();
            while (resultSet.next()){
                UUID frienduuid = UUID.fromString(resultSet.getString("friend"));
                String friendName = resultSet.getString("friend_name");
                friends.add(new Friend(frienduuid,friendName));
            }

            return friends;


        }catch (SQLException e){}
        return new ArrayList<>();
    }

    public void serialize(HashMap<String,List<Friend>> map){



        for (String main : map.keySet()){

             List<Friend> friends = getFriends(main);

            for (Friend plr : friends){


                List<Friend> cacheList = map.get(main);

                if (!cacheList.contains(plr))
                    removeFriend(main,plr.getUuid().toString());

                for (Friend fr : cacheList){

                    if (!friends.contains(fr))
                        addFriend(main,fr.getUuid().toString(),fr.getName());

                }


            }


        }




    }

    private void removeFriend(String main, String friend){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM qfriendship_friends WHERE main = ?, friend = ?");
            preparedStatement.setString(1,main);
            preparedStatement.setString(2,friend);


            preparedStatement.executeUpdate();
        }catch (SQLException e){

        }
    }
    private void addFriend(String main,String friend,String friend_name){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO qfriendship_friends (main,friend,friend_name) VALUES (?,?,?)");
            preparedStatement.setString(1,main);
            preparedStatement.setString(2,friend);
            preparedStatement.setString(3,friend_name);

            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }




}
