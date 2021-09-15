package codigo.codetest.idp.endpoint.constant;

import java.util.HashMap;
import java.util.Map;

public class EndpointStatusMsgs {

	private static final Map<String, String> msgMap = new HashMap<String, String>();
	
	static {
		msgMap.put(EndpointStatusCodes.AWUAP_I_001, "Authentication succeeds. JWT is returned.");
		msgMap.put(EndpointStatusCodes.AWUAP_E_001, "Invalid username or password");
		msgMap.put(EndpointStatusCodes.JVARU_I_001, "JWT is valid. User is returned.");
		msgMap.put(EndpointStatusCodes.JVARU_E_001, "Invalid JWT.");
	}
	
	public static String getStatusMsg(String statusCode) {
		return msgMap.get(statusCode);
	}
}
