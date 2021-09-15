package codigo.codetest.common.util.rest.security.proxy.entity;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	private String username;
	private String password;
	private Set<MstAuthority> authorities;
}
