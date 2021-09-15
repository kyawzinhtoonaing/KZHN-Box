package codigo.codetest.idp.service.users;

import codigo.codetest.idp.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserValidationServiceParam {
	private User incomingUser;
}
