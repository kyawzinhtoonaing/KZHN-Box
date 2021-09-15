package codigo.codetest.idp.service.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import codigo.codetest.idp.service.constant.JWTClaimsNames;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service("jwtGeneratorService")
public class JWTGeneratorService {
	
	@Value("${jwt.signing.key}")
	private String signingKey;

	public JWTGeneratorServiceResult serve(JWTGeneratorServiceParam param) {
		
		String jwt = this.prcd1GenerateJWT(param);
		
		return this.prepareResult(jwt);
	}
	
	private String prcd1GenerateJWT(JWTGeneratorServiceParam param) {
		SecretKey key = Keys.hmacShaKeyFor(this.signingKey.getBytes(StandardCharsets.UTF_8));
		String jwt = Jwts.builder()
			.setClaims(Map.of(JWTClaimsNames.USERNAME, param.getUsername()))
			.signWith(key)
			.compact();
		
		return jwt;
	}
	
	private JWTGeneratorServiceResult prepareResult(String jwt) {
		JWTGeneratorServiceResult result = new JWTGeneratorServiceResult();
		result.setJwt(jwt);
		
		return result;
	}
}
