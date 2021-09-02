package codigo.codetest.estore.service.evoucherpurchase;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import codigo.codetest.common.util.datetime.ZoneOffsetStrings;
import codigo.codetest.estore.dao.IEvoucherPurchaseDao;
import codigo.codetest.estore.dao.IEvoucherTaskDao;
import codigo.codetest.estore.dao.IUserPaymentMethodDao;
import codigo.codetest.estore.domain.entity.EvoucherOwner;
import codigo.codetest.estore.domain.entity.EvoucherPurchase;
import codigo.codetest.estore.domain.entity.EvoucherTask;
import codigo.codetest.estore.domain.entity.UserPaymentMethod;
import codigo.codetest.estore.proxy.evmgtsys.entity.EvoucherDef;
import codigo.codetest.estore.proxy.evmgtsys.entity.PaymentMethodDiscount;
import codigo.codetest.estore.proxy.evmgtsys.service.evoucherdef.EvoucherDefRetrievalByIdService;
import codigo.codetest.estore.proxy.evmgtsys.service.evoucherdef.EvoucherDefRetrievalByIdServiceParam;
import codigo.codetest.estore.proxy.evmgtsys.service.evoucherdef.EvoucherDefRetrievalByIdServiceResult;
import codigo.codetest.estore.service.evoucherpurchase.exception.EVoucherDefIsExpiredException;
import codigo.codetest.estore.service.evoucherpurchase.exception.EVoucherDefIsNotActiveException;
import codigo.codetest.estore.service.evoucherpurchase.exception.GiftPerUserLimitOverException;
import codigo.codetest.estore.service.evoucherpurchase.exception.InvalidBuyingTypeException;
import codigo.codetest.estore.service.evoucherpurchase.exception.InvalidPaymentMethodException;
import codigo.codetest.estore.service.evoucherpurchase.exception.MaxEvoucherLimitOverException;
import codigo.codetest.estore.service.evoucherpurchase.exception.NotEnoughBalanceException;
import codigo.codetest.estore.service.evoucherpurchase.exception.NotValidPaymentMethodOwnerException;
import codigo.codetest.estore.service.evoucherpurchase.exception.OnlyMeUsageCanNotBuyForOtherException;

@Service("evoucherPurchaseService")
public class EvoucherPurchaseService {

	private final EvoucherDefRetrievalByIdService evoucherDefRetrievalByIdService;
	
	private final IUserPaymentMethodDao iUserPaymentMethodDao;
	
	private final IEvoucherPurchaseDao iEvoucherPurchaseDao;
	
	private final IEvoucherTaskDao iEvoucherTaskDao;
	
	@Autowired
	public EvoucherPurchaseService(EvoucherDefRetrievalByIdService evoucherDefRetrievalByIdService,
			IUserPaymentMethodDao iUserPaymentMethodDao,
			IEvoucherPurchaseDao iEvoucherPurchaseDao,
			IEvoucherTaskDao iEvoucherTaskDao) {
		this.evoucherDefRetrievalByIdService = evoucherDefRetrievalByIdService;
		this.iUserPaymentMethodDao = iUserPaymentMethodDao;
		this.iEvoucherPurchaseDao = iEvoucherPurchaseDao;
		this.iEvoucherTaskDao = iEvoucherTaskDao;
	}
	
	public EvoucherPurchaseServiceResult serve(EvoucherPurchaseServiceParam param) 
			throws OnlyMeUsageCanNotBuyForOtherException, 
				MaxEvoucherLimitOverException, 
				InvalidBuyingTypeException, 
				GiftPerUserLimitOverException, 
				InvalidPaymentMethodException, 
				NotValidPaymentMethodOwnerException, 
				NotEnoughBalanceException, 
				EVoucherDefIsNotActiveException, 
				EVoucherDefIsExpiredException {
		EvoucherDef evoucherDef = this.prcd1RetrieveEvoucherDef(param);
		
		this.prcd2CheckValidityOfEVoucherDef(evoucherDef);
		
		this.prcd3CheckBuyType(param, evoucherDef);
		
		BigDecimal cost = this.prcd4CalculateCost(param, evoucherDef);
		
		BigDecimal discountAmt = this.prcd5CalculateDiscount(param, evoucherDef, cost);
		
		this.prcd6CheckBalance(param, cost, discountAmt);
		
		EvoucherPurchase evoucherPurchase = this.prcd7PrepareEvoucherPurchase(param, cost, discountAmt);
		
		this.prcd8SaveEvoucherPurchase(evoucherPurchase);
		
		List<EvoucherTask> evoucherTasks = this.prcd9PrepareEvoucherTask(evoucherPurchase);
		
		this.prcd10SaveEvoucherTasks(evoucherTasks);
		
		return this.prcd11PrepareResult(evoucherPurchase, evoucherTasks);
	}
	
	private EvoucherDef prcd1RetrieveEvoucherDef(EvoucherPurchaseServiceParam param) {
		EvoucherDefRetrievalByIdServiceParam svParam = new EvoucherDefRetrievalByIdServiceParam();
		svParam.setEvoucherDefId(param.getEvoucherDefId());
		EvoucherDefRetrievalByIdServiceResult svResult = evoucherDefRetrievalByIdService.serve(svParam);
		EvoucherDef evoucherDef = svResult.getEvoucherDef();
		
		return evoucherDef;
	}
	
	private void prcd2CheckValidityOfEVoucherDef(EvoucherDef evoucherDef) 
			throws EVoucherDefIsNotActiveException, 
					EVoucherDefIsExpiredException {
		if (!evoucherDef.getIsactive()) {
			throw new EVoucherDefIsNotActiveException();
		}
		
		OffsetDateTime now = OffsetDateTime.now(ZoneOffset.of(ZoneOffsetStrings.PLUS_06_30));
		OffsetDateTime expDate = evoucherDef.getExpiryDate();
		if (now.compareTo(expDate) > 0) {
			throw new EVoucherDefIsExpiredException();
		}
	}
	
	private void prcd3CheckBuyType(EvoucherPurchaseServiceParam param, 
			EvoucherDef evoucherDef) 
					throws OnlyMeUsageCanNotBuyForOtherException, 
						MaxEvoucherLimitOverException, 
						InvalidBuyingTypeException,
						GiftPerUserLimitOverException {
		//int selfPhCount = 0;
		//int otherPhCount = 0;
		
		int selfVoucherCount = 0;
		int otherVoucherCount = 0;
		
		if (param.getEvoucherOwners() != null) {
			for (EvoucherOwner evo : param.getEvoucherOwners()) {
				if (evo.getForSelf()) {
					//selfPhCount += 1;
					selfVoucherCount +=  (evo.getEvoucherCount() == null) ? 0 : evo.getEvoucherCount().intValue();
				} else {
					//otherPhCount += 1;
					otherVoucherCount += (evo.getEvoucherCount() == null) ? 0 : evo.getEvoucherCount().intValue();
				}
			}
		}
		
		switch(evoucherDef.getBuyingType().charValue()) {
		case EvoucherDef.BUYING_TYPE_ZERO:
			if (otherVoucherCount > 0) {
				throw new OnlyMeUsageCanNotBuyForOtherException();
			}
			if (selfVoucherCount > evoucherDef.getMaxEvoucherLimit().intValue()) {
				throw new MaxEvoucherLimitOverException();
			}
			break;
		case EvoucherDef.BUYING_TYPE_ONE:
			if (selfVoucherCount > evoucherDef.getMaxEvoucherLimit().intValue()) {
				throw new MaxEvoucherLimitOverException();
			}
			if (otherVoucherCount > evoucherDef.getGiftPerUserLimit().intValue()) {
				throw new GiftPerUserLimitOverException();
			}
			break;
		default:
			throw new InvalidBuyingTypeException();
		}
	}
	
	private BigDecimal prcd4CalculateCost(EvoucherPurchaseServiceParam param, EvoucherDef evoucherDef) {
		int purchaseCount = 0;
				
		if (param.getEvoucherOwners() != null) {
			for (EvoucherOwner evo : param.getEvoucherOwners()) {
				purchaseCount += (evo.getEvoucherCount() == null) ? 0 : evo.getEvoucherCount().intValue();
			}
		}
		
		return BigDecimal.valueOf(evoucherDef.getAmount().intValue() * purchaseCount);
	}
	
	private BigDecimal prcd5CalculateDiscount(EvoucherPurchaseServiceParam param, EvoucherDef evoucherDef, BigDecimal cost) {
		String paymentMethodId = param.getUserPaymentMethod().getPmethodId();
		int discountPercent = 0;
		BigDecimal discountAmount = BigDecimal.ZERO;
		
		for (PaymentMethodDiscount pmd: evoucherDef.getPaymentMethodDiscounts()) {
			if (pmd.getPmethodId().equals(paymentMethodId)) {
				discountPercent = pmd.getDiscountPercent();
				break;
			}
		}
		
		if (discountPercent > 0) {
			discountAmount = BigDecimal.valueOf(cost.doubleValue() * (discountPercent/100));
		}
		
		return discountAmount;
	}

	private void prcd6CheckBalance(EvoucherPurchaseServiceParam param, BigDecimal cost, BigDecimal discountAmt) 
			throws InvalidPaymentMethodException, 
					NotValidPaymentMethodOwnerException, 
					NotEnoughBalanceException {
		Optional<UserPaymentMethod> optUpm = this.iUserPaymentMethodDao.findById(param.getUserPaymentMethod().getId());
		
		if (optUpm.isEmpty()) {
			throw new InvalidPaymentMethodException();
		}
		
		UserPaymentMethod upm = optUpm.get();
		if (!upm.getUsername().equals(param.getUserPaymentMethod().getUsername())) {
			throw new NotValidPaymentMethodOwnerException();
		}
		
		BigDecimal charge = cost.subtract(discountAmt);
		BigDecimal balance = upm.getBalance();
		
		// balance is less than charge
		if (balance.compareTo(charge) == -1) {
			throw new NotEnoughBalanceException();
		}
	}

	private EvoucherPurchase prcd7PrepareEvoucherPurchase(EvoucherPurchaseServiceParam param, BigDecimal cost, BigDecimal discountAmt) {
		EvoucherPurchase evp = new EvoucherPurchase();
		
		evp.setId(UUID.randomUUID().toString());
		evp.setEvoucherDefId(param.getEvoucherDefId());
		
		int onlyMeUsageCount = 0;
		int giftToOtherCount = 0;
		
		if (param.getEvoucherOwners() != null) {
			
			for (EvoucherOwner evoParam : param.getEvoucherOwners()) {
				EvoucherOwner evo = new EvoucherOwner();
				evo.setId(UUID.randomUUID().toString());
				evo.setOwnerPhNumber(evoParam.getOwnerPhNumber());
				evo.setEvoucherCount(evoParam.getEvoucherCount());
				evo.setForSelf(evoParam.getForSelf());
				
				if (evo.getForSelf()) {
					onlyMeUsageCount += 1;
				} else {
					giftToOtherCount += 1;
				}
				
				evp.addEvoucherOwner(evo);
			}
		}
		
		evp.setOnlyMeUsageCount(onlyMeUsageCount);
		evp.setGiftToOthersCount(giftToOtherCount);
		evp.setPaidMethod(param.getUserPaymentMethod().getId());
		evp.setCost(cost);
		evp.setDiscount(discountAmt);
		evp.setPurchaseDatetime(OffsetDateTime.now(ZoneOffset.of(ZoneOffsetStrings.PLUS_06_30)));
		evp.setPurchaseBy(param.getUserPaymentMethod().getUsername());
		
		return evp;
	}

	private void prcd8SaveEvoucherPurchase(EvoucherPurchase evoucherPurchase) {
		this.iEvoucherPurchaseDao.save(evoucherPurchase);
	}

	private List<EvoucherTask> prcd9PrepareEvoucherTask(EvoucherPurchase evoucherPurchase) {
		OffsetDateTime now = OffsetDateTime.now(ZoneOffset.of(ZoneOffsetStrings.PLUS_06_30));
		List<EvoucherTask> evoucherTasks = new ArrayList<EvoucherTask>();
		
		if (evoucherPurchase.getEvoucherOwners() != null) {
			for (EvoucherOwner evo : evoucherPurchase.getEvoucherOwners()) {
				EvoucherTask evt = new EvoucherTask();
				
				evt.setEoId(evo.getId());
				evt.setEpId(evoucherPurchase.getId());
				evt.setIsTaskComplete(false);
				evt.setCreationDatetime(now);
				
				evoucherTasks.add(evt);
			}
		}
		
		return evoucherTasks;
	}

	private void prcd10SaveEvoucherTasks(List<EvoucherTask> evoucherTasks) {
		this.iEvoucherTaskDao.saveAll(evoucherTasks);
	}
	
	private EvoucherPurchaseServiceResult prcd11PrepareResult(EvoucherPurchase evoucherPurchase,
			List<EvoucherTask> evoucherTasks) {
		EvoucherPurchaseServiceResult result = new EvoucherPurchaseServiceResult();
		result.setEvoucherPurchase(evoucherPurchase);
		result.setEvoucherTasks(evoucherTasks);
		
		return result;
	}
}
