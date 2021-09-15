package codigo.codetest.common.util.rest.security.proxy.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JWTValidateAndReturnUserJsonMsg {
	private String statusCode;
	private String statusMsg;
	private JWTValidateAndReturnUserControllerResult data;
}
