package codigo.codetest.idp.endpoint.controller.rest.auth;

import lombok.Data;

@Data
public class AuthWithUsernameAndPasswordControllerParam {
	private String username;
	private String password;
}
