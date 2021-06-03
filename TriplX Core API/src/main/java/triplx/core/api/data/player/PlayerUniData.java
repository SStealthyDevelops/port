package triplx.core.api.data.player;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;
import triplx.core.api.data.MongoManager;

import java.util.UUID;

@SuppressWarnings("unused")
public class PlayerUniData {


    private static final MongoCollection<Document> collection = MongoManager.getDatabase().getCollection("player-data-uni");


    public static void createPlayer(final UUID uuid) {
        if (playerExists(uuid)) {
            return;
        }
        System.out.println("TEST");
        Document doc = new Document("uuid", uuid.toString())
        .append("xp", "0").append("level", "0").append("karma", "0")
        .append("first-login", System.currentTimeMillis())
        .append("last-login", System.currentTimeMillis());
        collection.insertOne(doc);

    }

    @Deprecated
    public static boolean playerExists(String name) {
        Document doc = collection.find(new Document("name", name)).first();
        return doc != null;
    }

    public static boolean playerExists(UUID uuid) {
        System.out.println(uuid);
        Document doc = collection.find(new Document("uuid", uuid.toString())).first();
        return doc != null;
    }

    // levels 1-50 10,000 XP EACH
    // levels 75 +17,500 XP EACH

    public static void addXP(UUID uuid, int xp) {
        Document doc = collection.find(new Document("uuid", uuid.toString())).first();



        int currentXP = getXP(uuid);
        int currentLevel = getLevel(uuid);
        int newXP;
        int newLevel;

        if (currentLevel < 50) {

            if ((currentXP + xp) > 10000) {
                newLevel = currentLevel;
                newXP = currentXP + xp;
                while ((newXP) > 10000) {
                    newLevel++;
                    newXP -= 10000;
                }
            } else {
                newXP = currentXP + xp;
                newLevel = currentLevel;
            }


        } else {

            if ((currentXP + xp) > 17500) {
                newLevel = currentLevel;
                newXP = currentXP + xp;
                while ((newXP) > 17500) {
                    newLevel++;
                    newXP -= 17500;
                }
            } else {
                newXP = currentXP + xp;
                newLevel = currentLevel;
            }


        }
        updateXp(doc, newXP, newLevel);


    }

    private static void updateXp(Document doc, int newXP, int newLevel) {
        Bson updatedValue = new Document("xp", newXP);
        Bson updateOperation = new Document("$set", updatedValue);

        Bson updatedValue2 = new Document("level", newLevel);
        Bson updatedOperation2 = new Document("$set", updatedValue2);
        collection.updateOne(doc, updateOperation);
        collection.updateOne(doc, updatedOperation2);
    }

    private static void updateField(Document doc, String key, Object value) { // we know all updatable values are ints already
        Bson updatedValue = new Document(key, value);
        Bson updateOperation = new Document("$set", updatedValue);

        collection.updateOne(doc, updateOperation);
    }

    private static Document getDoc(final UUID uuid) {
        return collection.find(new Document("uuid", uuid.toString())).first();
    }

    public static void addKarma(UUID uuid, int karma) {
        int newKarma = getKarma(uuid) + karma;
        Document doc = collection.find(new Document("uuid", uuid.toString())).first();
        updateField(doc, "karma", newKarma);
    }

    public static int getLevel(UUID uuid) {
        Document doc = collection.find(new Document("uuid", uuid.toString())).first();
        return Integer.parseInt(doc.getString("level"));
    }

    public static int getXP(UUID uuid) {
        Document doc = collection.find(new Document("uuid", uuid.toString())).first();
        return Integer.parseInt(doc.getString("xp"));
    }

    public static int getKarma(UUID uuid) {
        Document doc = collection.find(new Document("uuid", uuid.toString())).first();
        return Integer.parseInt(doc.getString("karma"));
    }

    public static Long getLastLogin(UUID uuid) {
        return Long.valueOf(getDoc(uuid).getString("last-login"));
    }


    public static Long getFirstLogin(UUID uuid) {
        return Long.valueOf(getDoc(uuid).getString("first-login"));
    }

    public static void setLastLogin(UUID uuid) {
        updateField(getDoc(uuid), "last-login", String.valueOf(System.currentTimeMillis()));
    }

}
