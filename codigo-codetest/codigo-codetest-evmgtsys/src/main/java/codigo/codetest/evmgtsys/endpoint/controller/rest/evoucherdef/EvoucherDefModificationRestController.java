package codigo.codetest.evmgtsys.endpoint.controller.rest.evoucherdef;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import codigo.codetest.common.util.rest.JsonMsgWithoutData;
import codigo.codetest.evmgtsys.domain.entity.EvoucherDef;
import codigo.codetest.evmgtsys.domain.entity.PaymentMethodDiscount;
import codigo.codetest.evmgtsys.endpoint.constant.EndpointStatusCodes;
import codigo.codetest.evmgtsys.endpoint.constant.EndpointStatusMsgs;
import codigo.codetest.evmgtsys.endpoint.constant.EndpointURLList;
import codigo.codetest.evmgtsys.service.evoucherdef.EvoucherDefModificationService;
import codigo.codetest.evmgtsys.service.evoucherdef.EvoucherDefModificationServiceParam;

@RestController
public class EvoucherDefModificationRestController {

	private final EvoucherDefModificationService evoucherDefModificationService;
	
	@Autowired
	public EvoucherDefModificationRestController(EvoucherDefModificationService evoucherDefModificationService) {
		this.evoucherDefModificationService = evoucherDefModificationService;
	}
	
	@PutMapping(EndpointURLList.EVOUCHER_DEF)
	public JsonMsgWithoutData<String> putEvoucherDef(@RequestBody EvoucherDefModificationRestControllerParam param) {
		EvoucherDefModificationServiceParam svParam = this.prcd1PrepareEvoucherDefModificationServiceParam(param);
		
		this.prcd2PutToDatabase(svParam);
		
		return this.prcd3PrepareJsonMsg();
	}
	
	private EvoucherDefModificationServiceParam prcd1PrepareEvoucherDefModificationServiceParam(EvoucherDefModificationRestControllerParam param) {
		EvoucherDef svEvoucherDef = new EvoucherDef();
		
		EvoucherDefModificationRestControllerParam.EvoucherDef cEvoucherDef = param.getEvoucherDef();
		
		svEvoucherDef.setId(cEvoucherDef.getId());
		svEvoucherDef.setTitle(cEvoucherDef.getTitle());
		svEvoucherDef.setDescription(cEvoucherDef.getDescription());
		svEvoucherDef.setExpiryDate(OffsetDateTime.parse(cEvoucherDef.getExpiryDateStr()));
		svEvoucherDef.setImageLocation(cEvoucherDef.getImageLocation());
		svEvoucherDef.setAmount(cEvoucherDef.getAmount());
		svEvoucherDef.setQuantity(cEvoucherDef.getQuantity());
		
		svEvoucherDef.setBuyingType(cEvoucherDef.getBuyingType());
		svEvoucherDef.setMaxEvoucherLimit(cEvoucherDef.getMaxEvoucherLimit());
		svEvoucherDef.setGiftPerUserLimit(cEvoucherDef.getGiftPerUserLimit());
		
		svEvoucherDef.setModifiedBy("kzhn");
		
		for (EvoucherDefModificationRestControllerParam.PaymentMethodDiscount cPaymentMethodDiscount : cEvoucherDef.getPaymentMethodDiscounts()) {
			PaymentMethodDiscount svPaymentMethodDiscount = new PaymentMethodDiscount();
			svPaymentMethodDiscount.setId(cPaymentMethodDiscount.getId());
			svPaymentMethodDiscount.setPmethodId(cPaymentMethodDiscount.getPmethodId());
			svPaymentMethodDiscount.setDiscountPercent(cPaymentMethodDiscount.getDiscountPercent());
			
			svEvoucherDef.getPaymentMethodDiscounts().add(svPaymentMethodDiscount);
		}
		
		EvoucherDefModificationServiceParam svParam = new EvoucherDefModificationServiceParam();
		svParam.setEvoucherDefToReplace(svEvoucherDef);
		return svParam;
	}
	
	private void prcd2PutToDatabase(EvoucherDefModificationServiceParam svParam) {
		this.evoucherDefModificationService.serve(svParam);
	}
	
	private JsonMsgWithoutData<String> prcd3PrepareJsonMsg() {
		JsonMsgWithoutData<String> jsonMsg = new JsonMsgWithoutData<String>();
		jsonMsg.setStatusCode(EndpointStatusCodes.EDM_I_001);
		jsonMsg.setStatusMsg(EndpointStatusMsgs.getStatusMsg(EndpointStatusCodes.EDM_I_001));
		
		return jsonMsg;
	}
}
