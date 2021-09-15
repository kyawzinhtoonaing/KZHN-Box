package codigo.codetest.common.util.rest.security.proxy.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.reactive.function.client.WebClient;

import codigo.codetest.common.util.rest.security.auth.UsernamePasswordAuthentication;
import codigo.codetest.common.util.rest.security.proxy.entity.MstAuthority;
import codigo.codetest.common.util.rest.security.proxy.entity.User;
import codigo.codetest.common.util.rest.security.proxy.service.exception.InvalidJWTException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JWTValidateAndReturnUserService {
	private final String idpBaseUrl;
	private final String idpEndpointUri;
	
	public UsernamePasswordAuthentication serve(String jwt) throws InvalidJWTException {
		// prcd1, retrieve User from IDP.
		User user = this.prcd1RetrieveUserFromIdp(jwt);
		
		// prcd2, convert User to UsernamePasswordAuthentication.
		return this.prcd2ConvetUserToUsernamePasswordAuthentication(user);
	}
	
	private User prcd1RetrieveUserFromIdp(String jwt) throws InvalidJWTException {
		
		ResponseEntity<JWTValidateAndReturnUserJsonMsg> rse = WebClient.builder()
			.baseUrl(idpBaseUrl)
			.build()
			.post()
			.uri(idpEndpointUri)
			.header(HttpHeaders.AUTHORIZATION, jwt)
			.exchangeToMono(response -> {
		         return response.toEntity(JWTValidateAndReturnUserJsonMsg.class);
		     })
			.block();
			
		if (rse.getStatusCode() != HttpStatus.OK) {
			throw new InvalidJWTException();
		}
		
		JWTValidateAndReturnUserJsonMsg jsonMsg = rse.getBody();
		if (!jsonMsg.getStatusCode().equals(JWTValidateAndReturnUserControllerStatusCode.JVARU_I_001)) {
			throw new InvalidJWTException();
		}
		
		User user = jsonMsg.getData().getUser();
		
		return user;
	}
	
	private UsernamePasswordAuthentication prcd2ConvetUserToUsernamePasswordAuthentication(User user) {
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		for (MstAuthority mauth : user.getAuthorities()) {
			grantedAuthorities.add(new SimpleGrantedAuthority(mauth.getAuthority()));
		}
		
		return new UsernamePasswordAuthentication(user.getUsername(), null, grantedAuthorities);
	}
}
