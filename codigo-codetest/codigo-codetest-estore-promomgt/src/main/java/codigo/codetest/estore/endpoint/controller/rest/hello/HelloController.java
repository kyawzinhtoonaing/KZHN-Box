package codigo.codetest.estore.endpoint.controller.rest.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import codigo.codetest.estore.proxy.evmgtsys.entity.EvoucherDef;
import codigo.codetest.estore.proxy.evmgtsys.service.evoucherdef.EvoucherDefRetrievalByIdService;
import codigo.codetest.estore.proxy.evmgtsys.service.evoucherdef.EvoucherDefRetrievalByIdServiceParam;
import codigo.codetest.estore.proxy.evmgtsys.service.evoucherdef.EvoucherDefRetrievalByIdServiceResult;

@RestController
public class HelloController {
	
	EvoucherDefRetrievalByIdService evoucherDefRetrievalByIdService;
	
	@Autowired
	public HelloController(
			EvoucherDefRetrievalByIdService evoucherDefRetrievalByIdService) {
		this.evoucherDefRetrievalByIdService = evoucherDefRetrievalByIdService;
	}

	@GetMapping("/{id}")
	public EvoucherDef hello(@PathVariable("id") String id) {
		EvoucherDefRetrievalByIdServiceParam svParam = 
				new EvoucherDefRetrievalByIdServiceParam();
		
		svParam.setEvoucherDefId(id);
		EvoucherDefRetrievalByIdServiceResult result = this
				.evoucherDefRetrievalByIdService.serve(svParam);
		
		return result.getEvoucherDef();
	}
}
