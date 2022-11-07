import com.google.gson.Gson;

public class JsonUtil {
	public final static Gson gson = new Gson();

	public static Student fromJsontoStudent(String jsonstudent) throws Exception {
		return gson.fromJson(jsonstudent, Student.class);
	}
}