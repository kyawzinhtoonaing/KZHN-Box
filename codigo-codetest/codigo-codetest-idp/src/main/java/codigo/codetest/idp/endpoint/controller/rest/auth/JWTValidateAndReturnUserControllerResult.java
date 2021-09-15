package codigo.codetest.idp.endpoint.controller.rest.auth;

import codigo.codetest.idp.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JWTValidateAndReturnUserControllerResult {
	private User user;
}
