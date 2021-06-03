package triplx.core.api.ranking;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import triplx.core.api.chat.Color;
import triplx.core.api.data.MongoManager;
import triplx.core.api.handlers.users.UserHandler;

import java.util.Objects;
import java.util.UUID;

import static triplx.core.api.ranking.Rank.*;

public class RankingManager {


    private static MongoCollection collection = MongoManager.getDatabase().getCollection("ranking");

    public static Rank getRank(Player player) {
        return getRank(player.getUniqueId());
    }

    public static Rank getRank(UUID uuid) {
        if (UserHandler.getInstance().userStored(uuid)) {
            return UserHandler.getInstance().getUser(uuid).getRank();
        }
        Document doc = (Document) collection.find(new Document("uuid", uuid.toString())).first();
        if (doc == null) {
            Bukkit.getConsoleSender().sendMessage(Color.cc("&cReturned rank of '" + uuid.toString() + "' as DEFAULT because it was unfound."));
            return DEFAULT;
        }
        return Rank.getRank(doc.getInteger("rankid"));
    }

    public static String getRankHist(UUID uuid) {
        Document doc = (Document) collection.find(new Document("uuid", uuid.toString())).first();
        if (doc == null) {
            Bukkit.getConsoleSender().sendMessage(Color.cc("&cReturned rank history of '" + uuid.toString() + "' as EMPTY because it was unfound."));
            return "";
        }
        return doc.getString("rankhistory");
    }


    public static boolean playerExists(String name) {
        Document doc = (Document) collection.find(new Document("name", name)).first();
        return doc != null;
    }

    public static boolean playerExists(UUID uuid) {
        Document doc = (Document) collection.find(new Document("uuid", uuid.toString())).first();
        return doc != null;
    }

    public static Player getPlayer(UUID uuid) {
        Document doc = (Document) collection.find(new Document("uuid", uuid.toString())).first();
        String name = doc.getString("name");
        return Bukkit.getPlayer(name);
    }

    public static void setRank(UUID uuid, Rank rank) {
        Document doc = (Document) collection.find(new Document("uuid", uuid.toString())).first();

        if (doc != null) {
            Bson updatedValue = new Document("rankid", rank.getRankID());
            Bson updateOperation = new Document("$set", updatedValue);

            Bson updatedValue2 = new Document("rankhistory", getRankHist(uuid) + " " + rank.getRankID());
            System.out.println(getRankHist(uuid));
            Bson updatedOperation2 = new Document("$set", updatedValue2);
            collection.updateOne(doc, updateOperation);
            collection.updateOne(doc, updatedOperation2);

            System.out.println("Updated both");
        }

        if (getPlayer(uuid) == null) return;

        if (Objects.equals(rank.getName(), "developer")) {
            getPlayer(uuid).setPlayerListName(Color.cc("&7[&eDEV&7] &e") + getPlayer(uuid).getName());
        } else {
            if (Objects.equals(rank.getName(), "youtube")) {
                getPlayer(uuid).setPlayerListName(Color.cc("&7[&cY&fT&7] &f"));
            }

            getPlayer(uuid).setPlayerListName(Color.cc(rank.getPrefix() + getPlayer(uuid).getName()));
        }
    }

    public static void createPlayer(Player player) {
        if (playerExists(player.getUniqueId())) {
            return;
        }
        Document document = new Document("uuid", player.getUniqueId().toString());
        document.append("rankid", 1);
        document.append("rankhistory", "1");
        document.append("name", player.getName());

        // noinspection all
        collection.insertOne(document);
    }

    public static String getUUID(String name) {
        Document doc = (Document) collection.find(new Document("name", name)).first();
        if (doc.getString("uuid") == null) {
            Bukkit.getConsoleSender().sendMessage(Color.cc("&cCould not find UUID for player '" + name + "'."));
            return "";
        }
        return doc.getString("uuid");
    }

}
