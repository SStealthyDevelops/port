package core.triplx.punishment.mongo;

import com.mongodb.client.MongoCollection;
import core.triplx.punishment.constructors.mute.Mute;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

import java.util.UUID;

public class MuteData {

    @Getter
    @Setter
    private static MuteData instance;

    private static final MongoCollection<Document> collection = MongoManager.getDatabase().getCollection("mutes");

    public boolean isMuted(UUID uuid) {
        final long now = System.currentTimeMillis();
        Document document = collection.find(new Document("uuid", uuid.toString())).first();
        if (document == null) return false;
        if (document.getString("end-time").equals("-1")) return true;
        long end = Long.parseLong(document.getString("end-time"));

        if (now >= end) {
            collection.deleteOne(document);
            return false;
        }

        return true;
    }

    public void mutePlayer(Mute mute) {
        try {
            Document existing = collection.find(new Document("uuid", mute.getUuid().toString())).first();
            if (existing != null) {
                collection.deleteOne(existing);
            }

            UUID uuid = mute.getUuid();
            long endTime = mute.getEndTime();
            int id = mute.getId();
            String reason = mute.getReason();

            Document document = new Document("uuid", uuid.toString())
                    .append("end-time", endTime + "")
                    .append("id", id + "")
                    .append("reason", reason);

            collection.insertOne(document);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pardonPlayer(UUID uuid) {
        collection.deleteOne(new Document("uuid", uuid.toString()));
    }

    public Mute getMute(UUID uuid) {
        try {
            Document doc = collection.find(new Document("uuid", uuid.toString())).first();
            return new Mute(uuid, doc.getString("reason"), Long.parseLong(doc.getString("end-time")), Integer.parseInt(doc.getString("id")));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
