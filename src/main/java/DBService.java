import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Updates;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.var;

@Data
public class DBService {
	private static MongoConnection connection = new MongoConnection();

	public static List<Student> getAll() {
		MongoCursor<Document> cursor = Utils.createMongoCursor();
		var students = new ArrayList<Student>();
		try {
			while (cursor.hasNext()) {
				String jsonStudent = cursor.next().toJson();
				Student student = JsonUtil.gson.fromJson(jsonStudent, Student.class);
				students.add(student);
			}
		} finally {
			cursor.close();
		}
		return students;
	}

	public static void addOne(HttpServletRequest request) {
		try {
			MongoCollection<Document> collection = Utils.createMongoCollection();
			String jsonstudent = Utils.getBody(request);
			String firstName = JsonUtil.fromJsontoStudent(jsonstudent).getFirstName();
			String middleName = JsonUtil.fromJsontoStudent(jsonstudent).getMiddleName();
			String lastName = JsonUtil.fromJsontoStudent(jsonstudent).getLastName();
			Document document = new Document("_id", Utils.generateId());
			document.put("firstName", firstName);
			document.put("middleName", middleName);
			document.put("lastName", lastName);
			collection.insertOne(document);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateOne(HttpServletRequest request) {
		try {
			String jsonstudent = Utils.getBody(request);
			String id = JsonUtil.fromJsontoStudent(jsonstudent).get_id();
			String firstName = JsonUtil.fromJsontoStudent(jsonstudent).getFirstName();
			String middleName = JsonUtil.fromJsontoStudent(jsonstudent).getMiddleName();
			String lastName = JsonUtil.fromJsontoStudent(jsonstudent).getLastName();
			MongoCollection<Student> collection = connection.getCollection();
			collection.updateOne(new Document("_id", id), Updates.set("firstName", firstName));
			collection.updateOne(new Document("_id", id), Updates.set("middleName", middleName));
			collection.updateOne(new Document("_id", id), Updates.set("lastName", lastName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void deleteOne(HttpServletRequest request) {
		try {
			String getid = Utils.getBody(request);
			String id = JsonUtil.gson.fromJson(getid, Student.class).get_id().toString();
			connection.getCollection().deleteOne(new Document("_id", id));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}