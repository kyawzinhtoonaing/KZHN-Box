package codigo.codetest.evmgtsys.service.evoucherdef;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import codigo.codetest.evmgtsys.dao.IEvoucherDefDao;
import codigo.codetest.evmgtsys.domain.entity.EvoucherDef;
import codigo.codetest.evmgtsys.domain.entity.PaymentMethodDiscount;

@Service("evoucherDefRetrievalByIdService")
public class EvoucherDefRetrievalByIdService {
	
	private final IEvoucherDefDao evoucherDefDao;
	
	@Autowired
	public EvoucherDefRetrievalByIdService(IEvoucherDefDao evoucherDefDao) {
		this.evoucherDefDao = evoucherDefDao;
	}

	public EvoucherDefRetrievalByIdServiceResult serve(EvoucherDefRetrievalByIdServiceParam param) {
		
		// prcd1 retrieve EvoucherDef by ID, then return the result.
		return this.prcd1Retrieve(param);
	}
	
	private EvoucherDefRetrievalByIdServiceResult prcd1Retrieve(EvoucherDefRetrievalByIdServiceParam param) {
		Optional<EvoucherDef> optEvoucherDef = this.evoucherDefDao.findById(param.getEvoucherDefId());
		EvoucherDefRetrievalByIdServiceResult result = new EvoucherDefRetrievalByIdServiceResult();
		
		if (optEvoucherDef.isEmpty()) {
			result.setEvoucherDef(null);
			return result;
		}
		
		EvoucherDef evd = optEvoucherDef.get();
		EvoucherDef evoucherDef = new EvoucherDef(evd.getId(),
				evd.getTitle(),
				evd.getDescription(),
				evd.getExpiryDate(),
				evd.getImageLocation(),
				evd.getAmount(),
				evd.getQuantity(),
				evd.getBuyingType(),
				evd.getGiftPerUserLimit(),
				evd.getMaxEvoucherLimit(),
				evd.getIsactive(),
				evd.getCreatedBy(),
				evd.getCreatedOn(),
				evd.getModifiedBy(),
				evd.getModifiedOn());
		
		for (PaymentMethodDiscount pmd : evd.getPaymentMethodDiscounts()) {
			PaymentMethodDiscount paymentMethodDiscount = new PaymentMethodDiscount(pmd.getId(), pmd.getPmethodId(), pmd.getDiscountPercent());
			evoucherDef.getPaymentMethodDiscounts().add(paymentMethodDiscount);
		}
		
		result.setEvoucherDef(evoucherDef);
		
		return result;
	}
}
