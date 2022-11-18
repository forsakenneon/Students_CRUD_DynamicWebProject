import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentUtils {
	public static String generateId() {
		return UUID.randomUUID().toString();
	}
}