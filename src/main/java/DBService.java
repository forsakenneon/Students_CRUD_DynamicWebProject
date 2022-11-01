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
				Student student = JsonUtil.getGson().fromJson(jsonStudent, Student.class);
				students.add(student);
			}
		} finally {
			cursor.close();
		}
		return students;
	}

	public static void addOne(HttpServletRequest request) {
		try {
			String jsonstudent = Utils.getBody(request);
			Student student = JsonUtil.getGson().fromJson(jsonstudent, Student.class);
			student.setId(Utils.generateId());
			connection.getCollection().insertOne(student);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateOne(HttpServletRequest request) {
		try {
			String jsonstudent = Utils.getBody(request);
			String id = JsonUtil.getGson().fromJson(jsonstudent, Student.class).get_id().toString();
			String firstName = JsonUtil.getGson().fromJson(jsonstudent, Student.class).getFirstName().toString();
			String middleName = JsonUtil.getGson().fromJson(jsonstudent, Student.class).getMiddleName().toString();
			String lastName = JsonUtil.getGson().fromJson(jsonstudent, Student.class).getLastName().toString();
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
			String id = JsonUtil.getGson().fromJson(getid, Student.class).get_id().toString();
			connection.getCollection().deleteOne(new Document("_id", id));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}