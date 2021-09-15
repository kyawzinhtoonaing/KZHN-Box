package codigo.codetest.estore.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import codigo.codetest.common.util.rest.security.filter.JwtAuthenticationFilter;
import codigo.codetest.common.util.rest.security.proxy.service.JWTValidateAndReturnUserService;

@Configuration
public class EstorePromomgtSecurityConfig extends WebSecurityConfigurerAdapter {
	@Value("${codigo.codetest.idp.server.url}")
	private String idpBaseUrl;
	
	@Value("${codigo.codetest.idp.server.auth-by-jwt}")
	private String idpAuthJwtUri;
	
	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter(new JWTValidateAndReturnUserService(idpBaseUrl, idpAuthJwtUri));
	}
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http
        	.addFilterAt(
	            jwtAuthenticationFilter(),
	            BasicAuthenticationFilter.class);

        http.authorizeRequests()
                .anyRequest().authenticated();
        
        http
	        .sessionManagement()
	        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	        .and();
    }
}
