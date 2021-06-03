package core.triplx.punishment.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;

public class MongoManager {

    @Getter
    private static MongoClientURI uri;

    @Getter
    private static MongoClient mongoClient;

    @Getter
    private static MongoDatabase database;

    public static void init() {
            uri = new MongoClientURI("mongodb://sstealthy:sstealthy@cluster0-shard-00-00-oyr6l.mongodb.net:27017,cluster0-shard-00-01-oyr6l.mongodb.net:27017,cluster0-shard-00-02-oyr6l.mongodb.net:27017/admin?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin&retryWrites=true&w=majority");
            mongoClient = new MongoClient(uri);
            database = mongoClient.getDatabase("triplx-punishments");
        }





}
