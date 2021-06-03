package core.triplx.punishment.mongo;

import com.mongodb.client.MongoCollection;
import core.triplx.punishment.constructors.ban.Ban;
import core.triplx.punishment.constructors.ban.BanReason;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

import java.util.UUID;

public class BanData {

    @Getter
    @Setter
    private static BanData instance;

    private static final MongoCollection<Document> collection = MongoManager.getDatabase().getCollection("bans");

    public boolean isBanned(UUID uuid) {
        final long now = System.currentTimeMillis();
        Document document = collection.find(new Document("uuid", uuid.toString())).first();
        if (document == null) return false;
        if (document.getString("end-time").equals("-1")) return true;
        long end = Long.parseLong(document.getString("end-time"));

        if (now >= end) { // passed the end time
            collection.deleteOne(document);
            return false;
        }

        return true; // they are banned
    }

    public void banPlayer(Ban ban) {
        try {
            Document existing = collection.find(new Document("uuid", ban.getUuid().toString())).first();
            if (existing != null) {
                collection.deleteOne(existing);
            }

            UUID uuid = ban.getUuid();
            long endTime = ban.getEndTime();
            int id = ban.getId();
            String reason = ban.getReason();
            BanReason type = ban.getType();

            Document document = new Document("uuid", uuid.toString())
                    .append("end-time", endTime + "")
                    .append("id", id + "")
                    .append("reason", reason)
                    .append("type", type.toString());

            collection.insertOne(document);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pardonPlayer(UUID uuid) {
        collection.deleteOne(new Document("uuid", uuid.toString()));
    }

    public Ban getBan(UUID uuid) {
       try {
           Document doc = collection.find(new Document("uuid", uuid.toString())).first();
           return new Ban(uuid, doc.getString("reason"), BanReason.valueOf(doc.getString("type")), Long.parseLong(doc.getString("end-time")), Integer.parseInt(doc.getString("id")));
       } catch (Exception e) {
           e.printStackTrace();
           return null;
       }
    }

}
