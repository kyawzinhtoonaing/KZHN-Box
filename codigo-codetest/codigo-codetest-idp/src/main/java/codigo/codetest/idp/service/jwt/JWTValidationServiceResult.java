package codigo.codetest.idp.service.jwt;

import codigo.codetest.idp.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JWTValidationServiceResult {
	private User user;
}
