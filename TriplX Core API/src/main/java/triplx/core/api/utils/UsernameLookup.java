package triplx.core.api.utils;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import triplx.core.api.chat.Color;
import triplx.core.api.data.MongoManager;

import java.util.HashMap;
import java.util.UUID;

public class UsernameLookup {

    public static HashMap<String, UUID> uuids = new HashMap<>();
    public static HashMap<String, String> names = new HashMap<>();

    private static final MongoCollection<Document> collection = MongoManager.getDatabase().getCollection("usernames");

    public static UUID getUUID(final String name) {
        if (uuids.containsKey(name.toLowerCase())) {
            return uuids.get(name.toLowerCase());
        }

        Document doc = collection.find(new Document("lower-name", name.toLowerCase())).first();
        if (doc == null) {
            Bukkit.getConsoleSender().sendMessage(Color.cc("&cCould not find a user with the lower-case name of " + name.toLowerCase()));
            return null;
        }

        String uuid = doc.getString("uuid");

        //get from mongo collection
        return UUID.fromString(uuid);
    }

    public static String getFormattedName(final String lowerCaseName) {
        Document doc = collection.find(new Document("lower-name", lowerCaseName.toLowerCase())).first();
        if (doc == null) {
            Bukkit.getConsoleSender().sendMessage(Color.cc("&cCould not find a user with the lower-case name of " + lowerCaseName.toLowerCase()));
            return null;
        }

        return doc.getString("name");
    }

    public static String getFormattedName(final UUID uuid) {
        Document doc = collection.find(new Document("uuid", uuid.toString())).first();
        if (doc == null) {
            Bukkit.getConsoleSender().sendMessage(Color.cc("&cCould not find a user with the uuid of " + uuid.toString()));
            return null;
        }

        return doc.getString("name");
    }

    public static void createPlayer(Player player) {
        if (playerExists(player.getUniqueId())) {
            // check if the values need to be updated
            checkForNameUpdate(player);
            return;
        }

        /* schema:
        {
            "uuid": "",
            "name": "",
            "lower-name": ""
        }
        */

        Document doc = new Document("uuid", player.getUniqueId().toString());
        doc.append("name", player.getName());
        doc.append("lower-name", player.getName().toLowerCase());
        collection.insertOne(doc);
    }

    public static boolean playerExists(UUID uuid) {
        Document doc = collection.find(new Document("uuid", uuid.toString())).first();
        return doc != null;
    }

    public static void checkForNameUpdate(Player player) {
        UUID uuid = player.getUniqueId();
        Document doc = collection.find(new Document("uuid", uuid.toString())).first();

        if (doc == null) return;

        String name = player.getName();
        String nameL = name.toLowerCase();

        String storedName = doc.getString("name");
        if (!storedName.equals(name)) {
            // update
            Bson updatedValue = new Document("name", name);
            Bson updateOperation = new Document("$set", updatedValue);

            collection.updateOne(doc, updateOperation);
        }
        String storedNameL = doc.getString("lower-name");
        if (!storedNameL.equals(nameL)) {
            // update
            Bson updatedValue = new Document("lower-name", nameL);
            Bson updateOperation = new Document("$set", updatedValue);

            collection.updateOne(doc, updateOperation);
        }

    }

}
