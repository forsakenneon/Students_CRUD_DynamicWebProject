import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Student {
	private String _id; // for GET request
	private String id; // for POST request
	private String firstName;
	private String middleName;
	private String lastName;
}
