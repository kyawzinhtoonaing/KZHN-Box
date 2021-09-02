package codigo.codetest.evmgtsys.endpoint.controller.rest.evoucherdef;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import codigo.codetest.common.util.rest.JsonMsgWithoutData;
import codigo.codetest.evmgtsys.domain.entity.EvoucherDef;
import codigo.codetest.evmgtsys.domain.entity.PaymentMethodDiscount;
import codigo.codetest.evmgtsys.endpoint.constant.EndpointStatusCodes;
import codigo.codetest.evmgtsys.endpoint.constant.EndpointStatusMsgs;
import codigo.codetest.evmgtsys.endpoint.constant.EndpointURLList;
import codigo.codetest.evmgtsys.service.evoucherdef.EvoucherDefCreationService;
import codigo.codetest.evmgtsys.service.evoucherdef.EvoucherDefCreationServiceParam;

@RestController
public class EvoucherDefCreationRestController {
	
	private final EvoucherDefCreationService evoucherDefCreationService;
	
	@Autowired
	public EvoucherDefCreationRestController(EvoucherDefCreationService evoucherDefCreationService) {
		this.evoucherDefCreationService = evoucherDefCreationService;
	}

	@PostMapping(EndpointURLList.EVOUCHER_DEF)
	@ResponseStatus(HttpStatus.CREATED)
	public JsonMsgWithoutData<String> createEvoucherDef(@RequestBody EvoucherDefCreationRestControllerParam param) throws Exception {
		
		// prcd1 prepare EvoucherDefCreationServiceParam.
		EvoucherDefCreationServiceParam svParam = this.prcd1PrepareEvoucherDefCreationServiceParam(param);
		
		// prcd2 save EvoucherDef.
		this.prcd2SaveEvoucherDef(svParam);
		
		// prcd3 create JSON msg and return it.
		return prcd3PrepareJsonMsg();
	}
	
	private EvoucherDefCreationServiceParam prcd1PrepareEvoucherDefCreationServiceParam(EvoucherDefCreationRestControllerParam param) {
		EvoucherDef svEvoucherDef = new EvoucherDef();
		
		EvoucherDefCreationRestControllerParam.EvoucherDef cEvoucherDef = param.getEvoucherDef();
		
		svEvoucherDef.setTitle(cEvoucherDef.getTitle());
		svEvoucherDef.setDescription(cEvoucherDef.getDescription());
		svEvoucherDef.setExpiryDate(OffsetDateTime.parse(cEvoucherDef.getExpiryDateStr()));
		svEvoucherDef.setImageLocation(cEvoucherDef.getImageLocation());
		svEvoucherDef.setAmount(cEvoucherDef.getAmount());
		svEvoucherDef.setQuantity(cEvoucherDef.getQuantity());
		svEvoucherDef.setBuyingType(cEvoucherDef.getBuyingType());
		svEvoucherDef.setMaxEvoucherLimit(cEvoucherDef.getMaxEvoucherLimit());
		svEvoucherDef.setGiftPerUserLimit(cEvoucherDef.getGiftPerUserLimit());
		
		svEvoucherDef.setCreatedBy("kzhn");
		
		for (EvoucherDefCreationRestControllerParam.PaymentMethodDiscount cPaymentMethodDiscount : cEvoucherDef.getPaymentMethodDiscounts()) {
			PaymentMethodDiscount svPaymentMethodDiscount = new PaymentMethodDiscount();
			svPaymentMethodDiscount.setPmethodId(cPaymentMethodDiscount.getPmethodId());
			svPaymentMethodDiscount.setDiscountPercent(cPaymentMethodDiscount.getDiscountPercent());
			
			svEvoucherDef.addPaymentMethodDiscount(svPaymentMethodDiscount);
		}
		
		EvoucherDefCreationServiceParam svParam = new EvoucherDefCreationServiceParam();
		svParam.setEvoucherDefToSave(svEvoucherDef);
		return svParam;
	}
	
	private void prcd2SaveEvoucherDef(EvoucherDefCreationServiceParam svParam) throws Exception {
		this.evoucherDefCreationService.serve(svParam);
	}
	
	private JsonMsgWithoutData<String> prcd3PrepareJsonMsg() {
		JsonMsgWithoutData<String> jsonMsg = new JsonMsgWithoutData<String>();
		jsonMsg.setStatusCode(EndpointStatusCodes.EDC_I_001);
		jsonMsg.setStatusMsg(EndpointStatusMsgs.getStatusMsg(EndpointStatusCodes.EDC_I_001));
		
		return jsonMsg;
	}
}
