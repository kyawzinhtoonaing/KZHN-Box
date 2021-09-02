package codigo.codetest.evmgtsys.endpoint.controller.rest.evoucherdef;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import codigo.codetest.common.util.rest.JsonMsgWithData;
import codigo.codetest.evmgtsys.endpoint.constant.EndpointStatusCodes;
import codigo.codetest.evmgtsys.endpoint.constant.EndpointStatusMsgs;
import codigo.codetest.evmgtsys.endpoint.constant.EndpointURLList;
import codigo.codetest.evmgtsys.service.evoucherdef.EvoucherDefRetrievalService;
import codigo.codetest.evmgtsys.service.evoucherdef.EvoucherDefRetrievalServiceParam;
import codigo.codetest.evmgtsys.service.evoucherdef.EvoucherDefRetrievalServiceResult;

@RestController
public class EvoucherDefRetrievalRestController {

	private final EvoucherDefRetrievalService evoucherDefRetrievalService;
	
	@Autowired
	public EvoucherDefRetrievalRestController(EvoucherDefRetrievalService evoucherDefRetrievalService) {
		this.evoucherDefRetrievalService = evoucherDefRetrievalService;
	}
	
	@GetMapping(EndpointURLList.EVOUCHER_DEF)
	public JsonMsgWithData<String, EvoucherDefRetrievalRestControllerResult> retrieveEvoucherDefs(@ModelAttribute EvoucherDefRetrievalRestControllerParam param) {
		// prcd1 retrieve data from database, return them.
		return this.prcd1RetrieveEvoucherDefs(param);
	}
	
	private JsonMsgWithData<String, EvoucherDefRetrievalRestControllerResult> prcd1RetrieveEvoucherDefs(EvoucherDefRetrievalRestControllerParam param) {
		EvoucherDefRetrievalServiceParam svParam = new EvoucherDefRetrievalServiceParam();
		svParam.setCurrentPage(param.getCurrentPage());
		svParam.setPageSize(param.getPageSize());
		
		EvoucherDefRetrievalServiceResult svResult = this.evoucherDefRetrievalService.serve(svParam);
		
		JsonMsgWithData<String, EvoucherDefRetrievalRestControllerResult> jsonMsg = new JsonMsgWithData<String, EvoucherDefRetrievalRestControllerResult>();
		if (svResult.getEvoucherDefs() == null) {
			jsonMsg.setStatusCode(EndpointStatusCodes.EDR_W_001);
			jsonMsg.setStatusMsg(EndpointStatusMsgs.getStatusMsg(EndpointStatusCodes.EDR_W_001));
			return jsonMsg;
		}
		
		EvoucherDefRetrievalRestControllerResult result = new EvoucherDefRetrievalRestControllerResult();
		result.setCurrentPage(svResult.getCurrentPage());
		result.setPageSize(svResult.getPageSize());
		result.setTotalPages(svResult.getTotalPages());
		result.setTotalRecords(svResult.getTotalRecords());
		result.setEvoucherDefs(svResult.getEvoucherDefs());
		
		jsonMsg.setStatusCode(EndpointStatusCodes.EDR_I_001);
		jsonMsg.setStatusMsg(EndpointStatusMsgs.getStatusMsg(EndpointStatusCodes.EDR_I_001));
		jsonMsg.setData(result);
		
		return jsonMsg;
	}
}
