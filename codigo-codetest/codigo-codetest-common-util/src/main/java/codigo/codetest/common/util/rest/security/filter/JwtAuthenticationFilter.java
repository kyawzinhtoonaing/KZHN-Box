package codigo.codetest.common.util.rest.security.filter;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import codigo.codetest.common.util.rest.security.auth.UsernamePasswordAuthentication;
import codigo.codetest.common.util.rest.security.proxy.service.JWTValidateAndReturnUserService;
import codigo.codetest.common.util.rest.security.proxy.service.exception.InvalidJWTException;
import lombok.AllArgsConstructor;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final JWTValidateAndReturnUserService jwtValidateAndReturnUserService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = request.getHeader(HttpHeaders.AUTHORIZATION);
        
        if (jwt == null) {
        	filterChain.doFilter(request, response);
        	return;
        }

        try {
			UsernamePasswordAuthentication auth = this.jwtValidateAndReturnUserService.serve(jwt);
			SecurityContextHolder.getContext().setAuthentication(auth);
		} catch (InvalidJWTException e) {
			
		}

        filterChain.doFilter(request, response);
    }
}
