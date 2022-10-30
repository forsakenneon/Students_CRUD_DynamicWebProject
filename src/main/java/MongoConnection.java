import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;

import lombok.Data;

@Data
public class MongoConnection {
	private static final String MONGO_URI = "mongodb://localhost:29000/";
	private MongoCollection<Student> collection = MongoClients.create(MONGO_URI).getDatabase("data")
			.withCodecRegistry(CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
					CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build())))
			.getCollection("Students", Student.class);
}