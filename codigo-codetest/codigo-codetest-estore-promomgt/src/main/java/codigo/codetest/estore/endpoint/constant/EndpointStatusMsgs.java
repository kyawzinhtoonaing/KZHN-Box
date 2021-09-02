package codigo.codetest.estore.endpoint.constant;

import java.util.HashMap;
import java.util.Map;

public class EndpointStatusMsgs {

	private static final Map<String, String> msgMap = new HashMap<String, String>();
	
	static {
		msgMap.put(EndpointStatusCodes.EVP_I_001, "Evoucher purchase completed. Evouchers are generating. Please, check the status later.");
		msgMap.put(EndpointStatusCodes.EVP_E_001, "This evoucher purchose for self using only!");
		msgMap.put(EndpointStatusCodes.EVP_E_002, "Maximum evoucher limit is over!");
		msgMap.put(EndpointStatusCodes.EVP_E_003, "Evoucher definition has invalid buying type!");
		msgMap.put(EndpointStatusCodes.EVP_E_004, "Gift-per-user limit is over!");
		msgMap.put(EndpointStatusCodes.EVP_E_005, "Invalid payment method!");
		msgMap.put(EndpointStatusCodes.EVP_E_006, "Invalid payment method owner!");
		msgMap.put(EndpointStatusCodes.EVP_E_007, "Not enough balance!");
		msgMap.put(EndpointStatusCodes.EVP_E_008, "Evoucher definition is not active!");
		msgMap.put(EndpointStatusCodes.EVP_E_009, "Evoucher definition is expired!");
	}
	
	public static String getStatusMsg(String statusCode) {
		return msgMap.get(statusCode);
	}
}
