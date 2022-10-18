import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.google.gson.Gson;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Updates;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;

@Data
public class StudentService {

	private static MongoConnection connection = new MongoConnection();
	private static Gson gson = new Gson();

	public static List<Student> getAll() {
		MongoCursor<Document> cursor = Utils.createMongoCursor();
		List<Student> students = new ArrayList<Student>();
		try {
			while (cursor.hasNext()) {
				String jsonStudent = cursor.next().toJson();
				Student student = gson.fromJson(jsonStudent, Student.class);
				students.add(student);
			}
		} finally {
			cursor.close();
		}
		return students;
	}

	public static void add(HttpServletRequest request) {
		try {
			String jsonstudent;
			jsonstudent = Utils.getBody(request);
			String firstName = gson.fromJson(jsonstudent, Student.class).getFirstName().toString();
			String middleName = gson.fromJson(jsonstudent, Student.class).getMiddleName().toString();
			String lastName = gson.fromJson(jsonstudent, Student.class).getLastName().toString();
			Student student = Student.builder()
					.id(Utils.generateId())
					.firstName(firstName)
					.middleName(middleName)
					.lastName(lastName).build();
			connection.getCollection().insertOne(student);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void update(HttpServletRequest request) {
		try {
			String getid;
			getid = Utils.getBody(request); 
			String id = gson.fromJson(getid, Student.class).get_id().toString();
			connection.getCollection().updateOne(new Document("_id", id), Updates.set("firstName", "UPDATEDNAMEX"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void delete(HttpServletRequest request) {
		try {
			String getid;
			getid = Utils.getBody(request); 
			String id = gson.fromJson(getid, Student.class).get_id().toString();
			connection.getCollection().deleteOne(new Document("_id", id));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
