package codigo.codetest.common.util.rest.security.proxy.service;

import codigo.codetest.common.util.rest.security.proxy.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JWTValidateAndReturnUserControllerResult {
	private User user;
}
