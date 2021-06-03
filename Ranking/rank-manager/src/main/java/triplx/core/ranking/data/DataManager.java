package triplx.core.ranking.data;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import triplx.core.api.utils.UsernameLookup;
import triplx.core.ranking.rank.Rank;
import triplx.core.ranking.utils.Color;

import java.util.Objects;
import java.util.UUID;

import static triplx.core.ranking.rank.Rank.*;

public class DataManager {

    private static MongoCollection<Document> collection = MongoManager.getCollection();

    public static Rank getRank(Player player) {
        return getRank(player.getUniqueId());
    }

    public static Rank getRank(UUID uuid) {
        Document doc = (Document) MongoManager.getCollection().find(new Document("uuid", uuid.toString())).first();
        if (doc == null) {
            Bukkit.getConsoleSender().sendMessage(Color.cc("&cReturned rank of '" + uuid.toString() + "' as DEFAULT because it was unfound."));
            return DEFAULT;
        }
        return Rank.getRank(doc.getInteger("rankid"));
    }

    public static String getRankHist(UUID uuid) {
        Document doc = (Document) MongoManager.getCollection().find(new Document("uuid", uuid.toString())).first();
        if (doc == null) {
            Bukkit.getConsoleSender().sendMessage(Color.cc("&cReturned rank history of '" + uuid.toString() + "' as EMPTY because it was unfound."));
            return "";
        }
        System.out.println(doc.getString("rankhistory"));
        return doc.getString("rankhistory");
    }


    public static boolean playerExists(UUID uuid) {
            Document doc = (Document) MongoManager.getCollection().find(new Document("uuid", uuid.toString())).first();
        return doc != null;
    }

    public static Player getPlayer(UUID uuid) {
        Document doc = (Document) MongoManager.getCollection().find(new Document("uuid", uuid.toString())).first();
        String name = doc.getString("name");
        return Bukkit.getPlayer(name);
    }

    public static void setRank(UUID uuid, Rank rank) {
        update(uuid, "rankid", rank.getRankID());
        update(uuid, "rankhistory", getRankHist(uuid) + " " + rank.getRankID().toString());


        if (getPlayer(uuid) == null) return;

        if (Objects.equals(rank.getName(), "developer")) {
            getPlayer(uuid).setPlayerListName(Color.cc("&7[&eDEV&7] &e") + getPlayer(uuid).getName());
        } else {
            if (Objects.equals(rank.getName(), "youtube")) {
                getPlayer(uuid).setPlayerListName(Color.cc("&7[&cY&fT&7] &f" + getPlayer(uuid).getName()));
            } else {
                getPlayer(uuid).setPlayerListName(Color.cc(rank.getPrefix() + getPlayer(uuid).getName()));
            }
        }
    }

    private static void update(UUID uuid, String key, Object newVal) {
        Document doc = collection.find(new Document("uuid", uuid.toString())).first();

        if (doc != null) {
            Bson updatedValue = new Document(key, newVal);
            Bson updateOp = new Document("$set", updatedValue);

            collection.updateOne(doc, updateOp);
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
        MongoManager.getCollection().insertOne(document);
    }

    public static String getUUID(String name) {
//        System.out.println(UsernameLookup.getUUID(name).toString() + " " + name +  ");
        return UsernameLookup.getUUID(name).toString();
    }

}
