package codigo.codetest.idp.service.jwt;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import codigo.codetest.idp.domain.entity.User;
import codigo.codetest.idp.service.constant.JWTClaimsNames;
import codigo.codetest.idp.service.users.UserRetrievalByUsernameService;
import codigo.codetest.idp.service.users.UserRetrievalByUsernameServiceParam;
import codigo.codetest.idp.service.users.exception.UserNotFoundException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service("jwtValidationService")
public class JWTValidationService {
	
	@Value("${jwt.signing.key}")
	private String signingKey;
	
	private final UserRetrievalByUsernameService userRetrievalByUsernameService;
	
	@Autowired
	public JWTValidationService(UserRetrievalByUsernameService userRetrievalByUsernameService) {
		this.userRetrievalByUsernameService = userRetrievalByUsernameService;
	}
	
	public JWTValidationServiceResult serve(JWTValidationServiceParam param) throws UserNotFoundException {
		
		Claims claims = this.prcd1ExtractClamimFromJWT(param);
        
		return new JWTValidationServiceResult(this.prcd2RetrieveUserUsingClaim(claims));
	}
	
	private Claims prcd1ExtractClamimFromJWT(JWTValidationServiceParam param) {
		SecretKey key = Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(param.getJwt())
                .getBody();
        
        return claims;
	}
	
	private User prcd2RetrieveUserUsingClaim(Claims claims) throws UserNotFoundException {
		String username = String.valueOf(claims.get(JWTClaimsNames.USERNAME));
        
        UserRetrievalByUsernameServiceParam svParam = new UserRetrievalByUsernameServiceParam();
        svParam.setUsername(username);
        User user = this.userRetrievalByUsernameService.serve(svParam).getUser();
        
        user.setPassword(null);
		
		return user;
	}
}
