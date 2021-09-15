package codigo.codetest.idp.service.users;

import codigo.codetest.idp.domain.entity.User;
import lombok.Data;

@Data
public class UserValidationServiceResult {
	private User verifiedUser;
}
