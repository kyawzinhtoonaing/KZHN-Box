package codigo.codetest.idp.endpoint.controller.rest.auth;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import codigo.codetest.idp.endpoint.constant.EndpointStatusCodes;
import codigo.codetest.idp.endpoint.constant.EndpointStatusMsgs;
import codigo.codetest.idp.endpoint.constant.EndpointURLList;
import codigo.codetest.idp.service.jwt.JWTGeneratorService;
import codigo.codetest.idp.service.jwt.JWTGeneratorServiceParam;
import codigo.codetest.idp.service.jwt.JWTGeneratorServiceResult;
import codigo.codetest.idp.service.users.UserValidationService;
import codigo.codetest.idp.service.users.UserValidationServiceParam;
import codigo.codetest.idp.service.users.UserValidationServiceResult;
import codigo.codetest.idp.service.users.exception.InvalidUserException;
import codigo.codetest.idp.service.users.exception.UserNotFoundException;
import codigo.codetest.common.util.rest.JsonMsgWithoutData;
import codigo.codetest.idp.domain.entity.User;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class AuthWithUsernameAndPasswordController {
	
	private final UserValidationService userValidationService;
	
	private final JWTGeneratorService jwtGeneratorService;

	@PostMapping(EndpointURLList.AUTH_WITH_USERNAME_AND_PASSWORD)
	public ResponseEntity<JsonMsgWithoutData<String>> handle(
			@RequestBody AuthWithUsernameAndPasswordControllerParam param) 
					throws UserNotFoundException, 
						InvalidUserException {
		
		// prcd1 validate user.
		User user = new User(param.getUsername(), param.getPassword(), null);
		UserValidationServiceResult result = this.userValidationService.serve(new UserValidationServiceParam(user));
		
		// prcd2 generate jwt
		JWTGeneratorServiceResult jwtResult = 
				this.jwtGeneratorService.serve(new JWTGeneratorServiceParam(result.getVerifiedUser().getUsername()));
		
		// prcd3 prepare ResponseEntity
		return this.prcd3PrepareResponseEntity(jwtResult);
	}
	
	private ResponseEntity<JsonMsgWithoutData<String>> prcd3PrepareResponseEntity(JWTGeneratorServiceResult jwtResult) {
		
		JsonMsgWithoutData<String> jsonMsg = new JsonMsgWithoutData<>();
		jsonMsg.constructMsg(EndpointStatusCodes.AWUAP_I_001, 
				EndpointStatusMsgs.getStatusMsg(EndpointStatusCodes.AWUAP_I_001));
		
		return ResponseEntity.ok()
					.header(HttpHeaders.AUTHORIZATION, jwtResult.getJwt())
					.body(jsonMsg);
	}
	
	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody JsonMsgWithoutData<String> handleUserNotFoundException(UserNotFoundException excpt) {
		JsonMsgWithoutData<String> jsonResult = new JsonMsgWithoutData<String>();
		jsonResult.setStatusCode(EndpointStatusCodes.AWUAP_E_001);
		jsonResult.setStatusMsg(EndpointStatusMsgs.getStatusMsg(EndpointStatusCodes.AWUAP_E_001));
		
		return jsonResult;
	}
	
	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody JsonMsgWithoutData<String> handleInvalidUserException(InvalidUserException excpt) {
		JsonMsgWithoutData<String> jsonResult = new JsonMsgWithoutData<String>();
		jsonResult.setStatusCode(EndpointStatusCodes.AWUAP_E_001);
		jsonResult.setStatusMsg(EndpointStatusMsgs.getStatusMsg(EndpointStatusCodes.AWUAP_E_001));
		
		return jsonResult;
	}
}

