package codigo.codetest.idp.endpoint.controller.rest.auth;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import codigo.codetest.common.util.rest.JsonMsgWithData;
import codigo.codetest.common.util.rest.JsonMsgWithoutData;
import codigo.codetest.idp.endpoint.constant.EndpointStatusCodes;
import codigo.codetest.idp.endpoint.constant.EndpointStatusMsgs;
import codigo.codetest.idp.endpoint.constant.EndpointURLList;
import codigo.codetest.idp.service.jwt.JWTValidationService;
import codigo.codetest.idp.service.jwt.JWTValidationServiceParam;
import codigo.codetest.idp.service.jwt.JWTValidationServiceResult;
import codigo.codetest.idp.service.users.exception.UserNotFoundException;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class JWTValidateAndReturnUserController {

	private final JWTValidationService jwtValidationService;
	
	@PostMapping(EndpointURLList.AUTH_WITH_JWT)
	public JsonMsgWithData<String, JWTValidateAndReturnUserControllerResult> handl(
			@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt) 
					throws UserNotFoundException {
		//prcd1 
		JWTValidationServiceResult userResult = 
				this.jwtValidationService.serve(new JWTValidationServiceParam(jwt));
		
		//prcd2 prepare JSON message.
		return this.prcd1PrepareJsonMsg(userResult);
	}
	
	private JsonMsgWithData<String, JWTValidateAndReturnUserControllerResult> prcd1PrepareJsonMsg(
			JWTValidationServiceResult userResult) {
		
		JWTValidateAndReturnUserControllerResult cResult = 
				new JWTValidateAndReturnUserControllerResult(userResult.getUser());
		
		JsonMsgWithData<String, JWTValidateAndReturnUserControllerResult> jsonMsg = 
				new JsonMsgWithData<String, JWTValidateAndReturnUserControllerResult>();
		jsonMsg.constructMsg(EndpointStatusCodes.JVARU_I_001, 
				EndpointStatusMsgs.getStatusMsg(EndpointStatusCodes.JVARU_I_001), 
				cResult);
		
		return jsonMsg;
	}
	
	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody JsonMsgWithoutData<String> handleUserNotFoundException(UserNotFoundException excpt) {
		JsonMsgWithoutData<String> jsonResult = new JsonMsgWithoutData<String>();
		jsonResult.setStatusCode(EndpointStatusCodes.JVARU_E_001);
		jsonResult.setStatusMsg(EndpointStatusMsgs.getStatusMsg(EndpointStatusCodes.JVARU_E_001));
		
		return jsonResult;
	}
}
