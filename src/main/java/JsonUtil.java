import com.google.gson.Gson;

public class JsonUtil {
	public final static Gson gson = new Gson();

	public static Student fromJsontoStudent(String jsonstudent) throws Exception {
		Student student = gson.fromJson(jsonstudent, Student.class);
		return student;
	}
}