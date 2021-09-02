package codigo.codetest.evmgtsys.endpoint.controller.rest.evoucherdef;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import codigo.codetest.common.util.rest.JsonMsgWithoutData;
import codigo.codetest.evmgtsys.endpoint.constant.EndpointStatusCodes;
import codigo.codetest.evmgtsys.endpoint.constant.EndpointStatusMsgs;
import codigo.codetest.evmgtsys.endpoint.constant.EndpointURLList;
import codigo.codetest.evmgtsys.service.evoucherdef.EvoucherDefActivateDeActivateService;
import codigo.codetest.evmgtsys.service.evoucherdef.EvoucherDefActivateDeActivateServiceParam;

@RestController
public class EvoucherDefActivateDeActivateRestController {

	private final EvoucherDefActivateDeActivateService evoucherDefActivateDeActivateService;
	
	@Autowired
	public EvoucherDefActivateDeActivateRestController(
			EvoucherDefActivateDeActivateService evoucherDefActivateDeActivateService) {
		this.evoucherDefActivateDeActivateService = evoucherDefActivateDeActivateService;
	}
	
	@PatchMapping(EndpointURLList.EVOUCHER_DEF_CHANGE_ACTIVENESS)
	public JsonMsgWithoutData<String> changeActiveness(@RequestBody EvoucherDefActivateDeActivateRestControllerParam param) throws Exception {
		this.prcd1Change(param);
		
		return this.prcd2PrepareJsonMsg();
	}
	
	private void prcd1Change(EvoucherDefActivateDeActivateRestControllerParam param) throws Exception {
		EvoucherDefActivateDeActivateServiceParam svParam = new EvoucherDefActivateDeActivateServiceParam();
		svParam.setId(param.getId());
		svParam.setIsactive(param.getIsactive());
		this.evoucherDefActivateDeActivateService.serve(svParam);
	}
	
	private JsonMsgWithoutData<String> prcd2PrepareJsonMsg() {
		JsonMsgWithoutData<String> jsonMsg = new JsonMsgWithoutData<String>();
		jsonMsg.setStatusCode(EndpointStatusCodes.EDAD_I_001);
		jsonMsg.setStatusMsg(EndpointStatusMsgs.getStatusMsg(EndpointStatusCodes.EDAD_I_001));
		
		return jsonMsg;
	}
}
