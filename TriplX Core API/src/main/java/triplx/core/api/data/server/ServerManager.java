package triplx.core.api.data.server;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bukkit.Bukkit;
import triplx.core.api.TriplXAPI;
import triplx.core.api.chat.Color;
import triplx.core.api.ranking.Rank;
import triplx.core.api.ranking.RankingManager;
import triplx.core.api.utils.UsernameLookup;

@SuppressWarnings("unused")
public class ServerManager {

    @Getter
    @Setter
    private static ServerManager instance;

    private static MongoDatabase database;

    public void init() {
        MongoClientURI uri = new MongoClientURI("mongodb://sstealthy:sstealthy@cluster0-shard-00-00-oyr6l.mongodb.net:27017,cluster0-shard-00-01-oyr6l.mongodb.net:27017,cluster0-shard-00-02-oyr6l.mongodb.net:27017/admin?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin&retryWrites=true&w=majority");
        MongoClient mongoClient = new MongoClient(uri);
        database = mongoClient.getDatabase("server-manager");
    }

    @Getter
    private MongoCollection<Document> collection;

    @Getter
    private String name;

    @Getter
    private String bungeeName;

    @Getter
    private String collectionName;

    /**
     *
     * @param collection The collection of the server type (minigame- skydomination, etcccc)
     * @param name The name of the server (SKYDOM_1) etc
     * @param bungeeName The name of the server as defined in the bungee config.yml
     */
    public void registerServer(String collection, String name, String bungeeName) {
        this.collection = database.getCollection(collection);
        if (this.collection.find(new Document("name", name)).first() == null) {
            Document doc = new Document("name", name).append("available", true).append("public", true).append("bungee-name", bungeeName);
            this.collection.insertOne(doc);
        }
        this.name = name;
        this.bungeeName = bungeeName;
        this.collectionName = collection;
    }

    public void unregisterServer() {
        System.out.println("Unregistering server");
        update(this.name, "available", false);
    }

    private void update(String name, String key, Object newVal) {
        try {
            Document doc = collection.find(new Document("name", name)).first();

            if (doc != null) {
                Bson updatedValue = new Document(key, newVal);
                Bson updateOp = new Document("$set", updatedValue);

                collection.updateOne(doc, updateOp);
            }
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(Color.cc("&cCould not update field " + key + " to " + newVal.toString() + " in server " + name));
            e.printStackTrace();
        }
    }

    /**
     * Set the availability of the server to false (players cannot join)
     */
    public void setUnavailable() {
        update(this.name, "available", false);
    }

    /**
     * Set the availability of the server to true (players can join)
     */
    public void setAvailable() {
        update(this.name, "available", true);
    }

    /**
     * Set the availability of the server
     * @param bool The new availability
     */
    public void setAvailable(boolean bool) {
        update(this.name, "available", bool);
    }

    public void setPublic(boolean PUBLIC) {
        update(this.name ,"public", PUBLIC);
    }

    // bungee name
    /**
     * @param player The player username (exact) you want to send to the server
     * @param serverName The server - use the TriplX name.. not the BungeeCord name
     */
    public void sendPlayer(String player, String serverName) {
        try {
            Document doc = collection.find(new Document("name", serverName)).first();
            String bungeeName = doc.getString("bungee-name");
            boolean pub = doc.getBoolean("public");

            if (!pub) {
                Rank rank = RankingManager.getRank(UsernameLookup.getUUID(player));
                if (!rank.isStaff()) return;
            }

            boolean bool = TriplXAPI.getInstance().sendPlayer(player, bungeeName);
            String message = bool ?
                    Color.cc("&aSuccessfully sent player " + player + " to server " + serverName + " // " + bungeeName): //worked
                    Color.cc("&cCould not send player " + player + " to server " + serverName + " // " + bungeeName); // failed
            Bukkit.getConsoleSender().sendMessage(message);
        } catch (Exception e) {
            Color.cc("&cCould not send player " + player + " to server " + serverName + " // " + bungeeName); // failed
            e.printStackTrace();
        }
    }


    /**
     *
     * @param collection The collection name for the server type (i.e main-hubs)
     * @return Returns the bungee name of the server
     */
    public String getAvailableServer(String collection) {
        try {
            MongoCollection<Document> serverColl = database.getCollection(collection);
            if (serverColl == null) return "UNKNOWN";

            Document document = serverColl.find(new Document("available", true)).first(); // find the first available server
            if (document == null) return "UNKNOWN";
            return document.getString("bungee-name");
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(Color.cc("&cCould not find available server in collection " + collection));
            e.printStackTrace();
        }
        return "UNKNOWN";
    }

    public String getAvailableServer(ServerType type) {
        return getAvailableServer(type.getCollection());
    }

}
