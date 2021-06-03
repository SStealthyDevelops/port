package core.triplx.punishment.mongo;

import com.mongodb.client.MongoCollection;
import core.triplx.punishment.constructors.Warning;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WarnData {

    @Getter
    @Setter
    private static WarnData instance;

    MongoCollection<Document> collection = MongoManager.getDatabase().getCollection("warns");

    public void warnPlayer(Warning warning) {
        try {
            if (collection.find(new Document("uuid", warning.getUuid().toString())).first() != null) { // player already has warnings
                Document doc = collection.find(new Document("uuid", warning.getUuid().toString())).first();
                @SuppressWarnings("all") ArrayList<String> strings = (ArrayList<String>) doc.get("warns");
                String toAdd = warning.getCategory() + ":" + warning.getReason();
                strings.add(toAdd);
                update(warning.getUuid(), "warns", strings);
            } else {
                UUID uuid = warning.getUuid();
                ArrayList<String> warnings = new ArrayList<>();
                warnings.add(warning.getCategory() + ":" + warning.getReason());
                Document doc = new Document("uuid", uuid.toString())
                        .append("warns", warnings);
                collection.insertOne(doc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void update(UUID uuid, @SuppressWarnings("SameParameterValue") String key, Object newVal) {
        Document doc = collection.find(new Document("uuid", uuid.toString())).first();

        if (doc != null) {
            Bson updatedValue = new Document(key, newVal);
            Bson updateOp = new Document("$set", updatedValue);

            collection.updateOne(doc, updateOp);
        }

    }

    public Warning[] getWarnings(final UUID uuid) {

        List<Warning> warnings = new ArrayList<>();

        Document doc = collection.find(new Document("uuid", uuid.toString())).first();

        if (doc == null) {
            return new Warning[0];
        }

        @SuppressWarnings("all") List<String> stringList = (ArrayList<String>) doc.get("warns");

        for (String s : stringList) {
            String[] split = s.split(":");
            Warning warning = new Warning(uuid, split[0], split[1]);
            warnings.add(warning);
        }

        return warnings.toArray(new Warning[0]);
    }

}
