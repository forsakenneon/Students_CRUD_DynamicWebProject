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
	private static DBContext connection = new DBContext();

	public static List<Student> getAll() throws Exception {
		MongoCursor<Document> cursor = DBContext.createMongoCursor("data", "Students", Student.class);
		var students = new ArrayList<Student>();
		try {
			while (cursor.hasNext()) {
				Student student = JsonUtil.fromJsontoStudent(cursor.next().toJson());
				students.add(student);
			}
		} finally {
			cursor.close();
		}
		return students;
	}

	public static void addOne(HttpServletRequest jsonstudent) {
		try {
			MongoCollection<Document> collection = connection.createDocument("data", "Students");
			Student student = JsonUtil.fromJsontoStudent(HTTPUtils.getBody(jsonstudent));
			var document = new Document("_id", StudentUtils.generateId());
			document.put("firstName", student.getFirstName());
			document.put("middleName", student.getMiddleName());
			document.put("lastName", student.getLastName());
			collection.insertOne(document);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateOne(HttpServletRequest jsonstudent) {
		try {
			String student = HTTPUtils.getBody(jsonstudent);
			String id = JsonUtil.fromJsontoStudent(student).get_id();
			MongoCollection<Student> collection = connection.fetchCollection("data", "Students", Student.class);
			collection.updateOne(new Document("_id", id),
					Updates.set("firstName", JsonUtil.fromJsontoStudent(student).getFirstName()));
			collection.updateOne(new Document("_id", id),
					Updates.set("middleName", JsonUtil.fromJsontoStudent(student).getMiddleName()));
			collection.updateOne(new Document("_id", id),
					Updates.set("lastName", JsonUtil.fromJsontoStudent(student).getLastName()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void deleteOne(HttpServletRequest request) {
		try {
			String id = JsonUtil.fromJsontoStudent(HTTPUtils.getBody(request)).get_id().toString();
			connection.fetchCollection("data", "Students", Student.class).deleteOne(new Document("_id", id));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}