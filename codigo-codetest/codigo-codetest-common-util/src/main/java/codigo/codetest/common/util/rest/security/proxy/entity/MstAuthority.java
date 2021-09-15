package codigo.codetest.common.util.rest.security.proxy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MstAuthority {
	
	private String authority;
	private String displayName;
}
