package codigo.codetest.estore.endpoint.controller.rest.evoucherpurchase;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import codigo.codetest.estore.endpoint.constant.EndpointURLList;
import codigo.codetest.common.util.rest.JsonMsgWithData;
import codigo.codetest.common.util.rest.JsonMsgWithoutData;
import codigo.codetest.estore.domain.entity.EvoucherOwner;
import codigo.codetest.estore.domain.entity.EvoucherTask;
import codigo.codetest.estore.domain.entity.UserPaymentMethod;
import codigo.codetest.estore.endpoint.constant.EndpointStatusCodes;
import codigo.codetest.estore.endpoint.constant.EndpointStatusMsgs;
import codigo.codetest.estore.service.evoucherpurchase.EvoucherPurchaseService;
import codigo.codetest.estore.service.evoucherpurchase.EvoucherPurchaseServiceParam;
import codigo.codetest.estore.service.evoucherpurchase.EvoucherPurchaseServiceResult;
import codigo.codetest.estore.service.evoucherpurchase.exception.EVoucherDefIsExpiredException;
import codigo.codetest.estore.service.evoucherpurchase.exception.EVoucherDefIsNotActiveException;
import codigo.codetest.estore.service.evoucherpurchase.exception.GiftPerUserLimitOverException;
import codigo.codetest.estore.service.evoucherpurchase.exception.InvalidBuyingTypeException;
import codigo.codetest.estore.service.evoucherpurchase.exception.InvalidPaymentMethodException;
import codigo.codetest.estore.service.evoucherpurchase.exception.MaxEvoucherLimitOverException;
import codigo.codetest.estore.service.evoucherpurchase.exception.NotEnoughBalanceException;
import codigo.codetest.estore.service.evoucherpurchase.exception.NotValidPaymentMethodOwnerException;
import codigo.codetest.estore.service.evoucherpurchase.exception.OnlyMeUsageCanNotBuyForOtherException;

@RestController
public class EvoucherPurchaseRestController {

	private final EvoucherPurchaseService evoucherPurchaseService;
	
	@Autowired
	public EvoucherPurchaseRestController(EvoucherPurchaseService evoucherPurchaseService) {
		this.evoucherPurchaseService = evoucherPurchaseService;
	}
	
	@PostMapping(EndpointURLList.EVOUCHERPURCHASE)
	@ResponseStatus(HttpStatus.CREATED)
	public JsonMsgWithData<String, EvoucherPurchaseRestControllerResult> purchaseEvoucher(@RequestBody EvoucherPurchaseRestControllerParam param) throws OnlyMeUsageCanNotBuyForOtherException, 
			MaxEvoucherLimitOverException, 
			InvalidBuyingTypeException, 
			GiftPerUserLimitOverException, 
			InvalidPaymentMethodException, 
			NotValidPaymentMethodOwnerException, 
			NotEnoughBalanceException, 
			EVoucherDefIsNotActiveException, 
			EVoucherDefIsExpiredException {
		EvoucherPurchaseServiceParam svParam = this.prcd1PrepareEvoucherPurchaseServiceParam(param);
		
		EvoucherPurchaseServiceResult svResult = this.prcd2SaveEvoucherPurchase(svParam);
		
		EvoucherPurchaseRestControllerResult cResult = this.prcd3PrepareEvoucherPurchaseRestControllerResult(svResult);
		
		return this.prcd4PrepareJsomMsg(cResult);
	}
	
	private EvoucherPurchaseServiceParam prcd1PrepareEvoucherPurchaseServiceParam(
			EvoucherPurchaseRestControllerParam param) {
		EvoucherPurchaseServiceParam svParam = new EvoucherPurchaseServiceParam();
		svParam.setEvoucherDefId(param.getEvoucherDefId());
		
		List<EvoucherOwner> evoucherOwners = new ArrayList<EvoucherOwner>();
		if (param.getEvoucherOwners() != null) {
			for (EvoucherPurchaseRestControllerParam.EvoucherOwner evo : param.getEvoucherOwners()) {
				EvoucherOwner svEvo = new EvoucherOwner(null, evo.getOwnerPhNumber(), evo.getEvoucherCount(), evo.getForSelf());
				evoucherOwners.add(svEvo);
			}
		}
		svParam.setEvoucherOwners(evoucherOwners);
		
		EvoucherPurchaseRestControllerParam.UserPaymentMethod userPaymentMethod = param.getUserPaymentMethod();

		UserPaymentMethod svUpm = new UserPaymentMethod(userPaymentMethod.getId(), 
				userPaymentMethod.getUsername(), 
				userPaymentMethod.getPmethodId(), 
				null);
		
		svParam.setUserPaymentMethod(svUpm);
		
		return svParam;
	}
	
	private EvoucherPurchaseServiceResult prcd2SaveEvoucherPurchase(EvoucherPurchaseServiceParam svParam) throws OnlyMeUsageCanNotBuyForOtherException, 
			MaxEvoucherLimitOverException, 
			InvalidBuyingTypeException, 
			GiftPerUserLimitOverException, 
			InvalidPaymentMethodException, 
			NotValidPaymentMethodOwnerException, 
			NotEnoughBalanceException, 
			EVoucherDefIsNotActiveException, 
			EVoucherDefIsExpiredException {
		
		EvoucherPurchaseServiceResult svResult = this.evoucherPurchaseService.serve(svParam);
		return svResult;
	}

	private EvoucherPurchaseRestControllerResult prcd3PrepareEvoucherPurchaseRestControllerResult(
			EvoucherPurchaseServiceResult svResult) {
		EvoucherPurchaseRestControllerResult cResult = new EvoucherPurchaseRestControllerResult();
		
		cResult.setEvoucherPurchaseId(svResult.getEvoucherPurchase().getId());
		
		if (svResult.getEvoucherTasks() != null) {
			List<String> evoucherTaskIds = new ArrayList<String>();
			for (EvoucherTask evt : svResult.getEvoucherTasks()) {
				evoucherTaskIds.add(evt.getEoId());
			}
			cResult.setEvoucherTaskIds(evoucherTaskIds);
		}
		
		return cResult;
	}
	
	private JsonMsgWithData<String, EvoucherPurchaseRestControllerResult> prcd4PrepareJsomMsg(EvoucherPurchaseRestControllerResult cResult) {
		JsonMsgWithData<String, EvoucherPurchaseRestControllerResult> jsonMsg = 
				new JsonMsgWithData<String, EvoucherPurchaseRestControllerResult>();
		jsonMsg.setStatusCode(EndpointStatusCodes.EVP_I_001);
		jsonMsg.setStatusMsg(EndpointStatusMsgs.getStatusMsg(EndpointStatusCodes.EVP_I_001));
		jsonMsg.setData(cResult);
		
		return jsonMsg;
	}
	
	@ExceptionHandler
	public @ResponseBody JsonMsgWithoutData<String> handleOnlyMeUsageCanNotBuyForOtherException(
			OnlyMeUsageCanNotBuyForOtherException excpt) {
		JsonMsgWithoutData<String> jsonResult = new JsonMsgWithoutData<String>();
		jsonResult.setStatusCode(EndpointStatusCodes.EVP_E_001);
		jsonResult.setStatusMsg(EndpointStatusMsgs.getStatusMsg(EndpointStatusCodes.EVP_E_001));
		
		return jsonResult;
	}
	
	@ExceptionHandler
	public @ResponseBody JsonMsgWithoutData<String> handleMaxEvoucherLimitOverException(MaxEvoucherLimitOverException excpt) {
		JsonMsgWithoutData<String> jsonResult = new JsonMsgWithoutData<String>();
		jsonResult.setStatusCode(EndpointStatusCodes.EVP_E_002);
		jsonResult.setStatusMsg(EndpointStatusMsgs.getStatusMsg(EndpointStatusCodes.EVP_E_002));
		
		return jsonResult;
	}
	
	@ExceptionHandler
	public @ResponseBody JsonMsgWithoutData<String> handleInvalidBuyingTypeException(InvalidBuyingTypeException excpt) {
		JsonMsgWithoutData<String> jsonResult = new JsonMsgWithoutData<String>();
		jsonResult.setStatusCode(EndpointStatusCodes.EVP_E_003);
		jsonResult.setStatusMsg(EndpointStatusMsgs.getStatusMsg(EndpointStatusCodes.EVP_E_003));
		
		return jsonResult;
	}
	
	@ExceptionHandler
	public @ResponseBody JsonMsgWithoutData<String> handleGiftPerUserLimitOverException(GiftPerUserLimitOverException excpt) {
		JsonMsgWithoutData<String> jsonResult = new JsonMsgWithoutData<String>();
		jsonResult.setStatusCode(EndpointStatusCodes.EVP_E_004);
		jsonResult.setStatusMsg(EndpointStatusMsgs.getStatusMsg(EndpointStatusCodes.EVP_E_004));
		
		return jsonResult;
	}
	
	@ExceptionHandler
	public @ResponseBody JsonMsgWithoutData<String> handleInvalidPaymentMethodException(InvalidPaymentMethodException excpt) {
		JsonMsgWithoutData<String> jsonResult = new JsonMsgWithoutData<String>();
		jsonResult.setStatusCode(EndpointStatusCodes.EVP_E_005);
		jsonResult.setStatusMsg(EndpointStatusMsgs.getStatusMsg(EndpointStatusCodes.EVP_E_005));
		
		return jsonResult;
	}
	
	@ExceptionHandler
	public @ResponseBody JsonMsgWithoutData<String> handleNotValidPaymentMethodOwnerException(NotValidPaymentMethodOwnerException excpt) {
		JsonMsgWithoutData<String> jsonResult = new JsonMsgWithoutData<String>();
		jsonResult.setStatusCode(EndpointStatusCodes.EVP_E_006);
		jsonResult.setStatusMsg(EndpointStatusMsgs.getStatusMsg(EndpointStatusCodes.EVP_E_006));
		
		return jsonResult;
	}
	
	@ExceptionHandler
	public @ResponseBody JsonMsgWithoutData<String> handleNotEnoughBalanceException(NotEnoughBalanceException excpt) {
		JsonMsgWithoutData<String> jsonResult = new JsonMsgWithoutData<String>();
		jsonResult.setStatusCode(EndpointStatusCodes.EVP_E_007);
		jsonResult.setStatusMsg(EndpointStatusMsgs.getStatusMsg(EndpointStatusCodes.EVP_E_007));
		
		return jsonResult;
	}
	
	@ExceptionHandler
	public @ResponseBody JsonMsgWithoutData<String> handleEVoucherDefIsNotActiveException(EVoucherDefIsNotActiveException excpt) {
		JsonMsgWithoutData<String> jsonResult = new JsonMsgWithoutData<String>();
		jsonResult.setStatusCode(EndpointStatusCodes.EVP_E_008);
		jsonResult.setStatusMsg(EndpointStatusMsgs.getStatusMsg(EndpointStatusCodes.EVP_E_008));
		
		return jsonResult;
	}
	
	@ExceptionHandler
	public @ResponseBody JsonMsgWithoutData<String> handleEVoucherDefIsExpiredException(EVoucherDefIsExpiredException excpt) {
		JsonMsgWithoutData<String> jsonResult = new JsonMsgWithoutData<String>();
		jsonResult.setStatusCode(EndpointStatusCodes.EVP_E_009);
		jsonResult.setStatusMsg(EndpointStatusMsgs.getStatusMsg(EndpointStatusCodes.EVP_E_009));
		
		return jsonResult;
	}
}
