package codigo.codetest.evmgtsys.endpoint.constant;

import java.util.HashMap;
import java.util.Map;

public class EndpointStatusMsgs {

	private static final Map<String, String> msgMap = new HashMap<String, String>();
	
	static {
		msgMap.put(EndpointStatusCodes.EDC_I_001, "Evoucher definition has been saved.");
		msgMap.put(EndpointStatusCodes.EDR_I_001, "Evoucher definition retrival succeeds.");
		msgMap.put(EndpointStatusCodes.EDR_W_001, "There is no data.");
		msgMap.put(EndpointStatusCodes.EDRI_I_001, "Evoucher definition is found.");
		msgMap.put(EndpointStatusCodes.EDRI_E_001, "Evoucher definition is not found.");
		msgMap.put(EndpointStatusCodes.EDAD_I_001, "Activeness of evoucher definition has been changed.");
		msgMap.put(EndpointStatusCodes.EDM_I_001, "Evoucher definition has been updated.");
	}
	
	public static String getStatusMsg(String statusCode) {
		return msgMap.get(statusCode);
	}
}
