package codigo.codetest.evmgtsys.endpoint.controller.rest.evoucherdef;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import codigo.codetest.common.util.rest.JsonMsgWithData;
import codigo.codetest.evmgtsys.endpoint.constant.EndpointStatusCodes;
import codigo.codetest.evmgtsys.endpoint.constant.EndpointStatusMsgs;
import codigo.codetest.evmgtsys.endpoint.constant.EndpointURLList;
import codigo.codetest.evmgtsys.service.evoucherdef.EvoucherDefRetrievalByIdService;
import codigo.codetest.evmgtsys.service.evoucherdef.EvoucherDefRetrievalByIdServiceParam;
import codigo.codetest.evmgtsys.service.evoucherdef.EvoucherDefRetrievalByIdServiceResult;

@RestController
public class EvoucherDefRetrievalByIdRestController {

	private final EvoucherDefRetrievalByIdService evoucherDefRetrievalByIdService;
	
	@Autowired
	public EvoucherDefRetrievalByIdRestController(EvoucherDefRetrievalByIdService evoucherDefRetrievalByIdService) {
		this.evoucherDefRetrievalByIdService = evoucherDefRetrievalByIdService;
	}
	
	@GetMapping(EndpointURLList.EVOUCHER_DEF_BY_ID)
	public ResponseEntity<JsonMsgWithData<String, EvoucherDefRetrievalByIdRestControllerResult>> retrieveEvoucherDefById(@PathVariable("id") String id) {
		// Retrieve EVoucherDef by ID, then return the result.
		return this.prcd1Retrieve(id);
	}
	
	private ResponseEntity<JsonMsgWithData<String, EvoucherDefRetrievalByIdRestControllerResult>> prcd1Retrieve(String id) {
		EvoucherDefRetrievalByIdServiceParam svParam = new EvoucherDefRetrievalByIdServiceParam();
		svParam.setEvoucherDefId(id);
		
		EvoucherDefRetrievalByIdServiceResult svResult = this.evoucherDefRetrievalByIdService.serve(svParam);
		
		JsonMsgWithData<String, EvoucherDefRetrievalByIdRestControllerResult> jsonMsg = 
				new JsonMsgWithData<String, EvoucherDefRetrievalByIdRestControllerResult>();
		
		
		if (svResult.getEvoucherDef() == null) {
			jsonMsg.setStatusCode(EndpointStatusCodes.EDRI_E_001);
			jsonMsg.setStatusMsg(EndpointStatusMsgs.getStatusMsg(EndpointStatusCodes.EDRI_E_001));
			return new ResponseEntity<>(jsonMsg, HttpStatus.NOT_FOUND);
		} 
		
		EvoucherDefRetrievalByIdRestControllerResult cResult = new EvoucherDefRetrievalByIdRestControllerResult();
		cResult.setEvoucherDef(svResult.getEvoucherDef());
		jsonMsg.setStatusCode(EndpointStatusCodes.EDRI_I_001);
		jsonMsg.setStatusMsg(EndpointStatusMsgs.getStatusMsg(EndpointStatusCodes.EDRI_I_001));
		jsonMsg.setData(cResult);
		
		return new ResponseEntity<>(jsonMsg, HttpStatus.OK);
	}
}
