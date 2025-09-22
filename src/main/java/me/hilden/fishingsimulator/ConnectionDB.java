package me.hilden.fishingsimulator;
import GUI.holder.LocationMenuHolder;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoCredential;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.BSON;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ConnectionDB {

    private static MongoClient client;
    private static MongoDatabase database;
    private static MongoCollection collection;
    private static MongoCollection fishCollection;

    public static MongoClient getClient() {
        return client;
    }

    public static MongoDatabase getDatabase() {
        return database;
    }

    public static MongoCollection getCollection() {
        return collection;
    }

    public static MongoCollection getFishCollection() {
        return fishCollection;
    }

    public static void establishConnections() {
        try {
            client = new MongoClient("localhost", 27017);

            System.out.println("Successfully Connected to the database");
            System.out.println("Successfully Connected to the database");
            System.out.println("Successfully Connected to the database");
            System.out.println("Successfully Connected to the database");
            System.out.println("Successfully Connected to the database");

            database = client.getDatabase("test");
            collection = database.getCollection("testCollection");
            fishCollection = database.getCollection("fish");

            System.out.println(getPlayer("Penis").get("uuid"));
            System.out.println(getPlayer("Penis").get("uuid"));
            System.out.println(getPlayer("Penis").get("money"));
            setParameter("Penis", "money", 50.0);
            System.out.println(getPlayer("Penis").get("uuid"));
            System.out.println(getPlayer("Penis").get("uuid"));
            System.out.println(getPlayer("Penis").get("money"));
        } catch (Exception e) {
            System.out.println("Connection establishment failed");
            System.out.println(e);
        }
    }

    public static Document getPlayer(String nickname) {
        Document document = (Document) collection.find(Filters.eq("nickname", nickname)).first();
        if (document == null) {
            List<String> fish = new ArrayList<>();
            List<String> locations = new ArrayList<>();
            locations.add("Озеро");
            document = new Document("nickname", nickname)
                    .append("uuid", UUID.randomUUID())
                    .append("money", 20.0)
                    .append("hookUpgrade", 0)
                    .append("lineUpgrade", 0)
                    .append("rodUpgrade", 0)
                    .append("caughtFish", fish)
                    .append("locations", locations);
            collection.insertOne(document);
        }
        return document;
    }

    public static void setParameter(String nickname, String parameterName, int parameterValue) {
        Document document = getPlayer(nickname);
        Bson update = Updates.set(parameterName, parameterValue);
        UpdateResult result = collection.updateOne(Filters.eq("nickname", nickname), update);
    }

    public static void setParameter(String nickname, String parameterName, double parameterValue) {
        Document document = getPlayer(nickname);
        Bson update = Updates.set(parameterName, parameterValue);
        UpdateResult result = collection.updateOne(Filters.eq("nickname", nickname), update);

    }

    public static void increaseParameter(String nickname, String parameterName, int parameterValue) {
        Document document = getPlayer(nickname);
        Bson update = Updates.set(parameterName, (int) document.get(parameterName) + parameterValue);
        UpdateResult result = collection.updateOne(Filters.eq("nickname", nickname), update);
    }

    public static void increaseParameter(String nickname, String parameterName, double parameterValue) {
        Document document = getPlayer(nickname);
        Bson update = Updates.set(parameterName, (double) document.get(parameterName) + parameterValue);
        UpdateResult result = collection.updateOne(Filters.eq("nickname", nickname), update);

    }

    public static void decreaseParameter(String nickname, String parameterName, int parameterValue) {
        Document document = getPlayer(nickname);
        Bson update = Updates.set(parameterName, (int) document.get(parameterName) - parameterValue);
        UpdateResult result = collection.updateOne(Filters.eq("nickname", nickname), update);
    }

    public static void decreaseParameter(String nickname, String parameterName, double parameterValue) {
        Document document = getPlayer(nickname);
        Bson update = Updates.set(parameterName, (double) document.get(parameterName) - parameterValue);
        UpdateResult result = collection.updateOne(Filters.eq("nickname", nickname), update);

    }

    public static Document getFish(UUID uuid) {
        return (Document) fishCollection.find(Filters.eq("uuid", uuid)).first();
    }


}