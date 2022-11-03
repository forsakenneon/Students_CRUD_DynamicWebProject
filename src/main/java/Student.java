import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Student {
	private String _id;
	private String firstName;
	private String middleName;
	private String lastName;
}