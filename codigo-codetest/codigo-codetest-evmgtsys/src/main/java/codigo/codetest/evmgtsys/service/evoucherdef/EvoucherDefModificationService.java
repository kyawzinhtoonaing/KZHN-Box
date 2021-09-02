package codigo.codetest.evmgtsys.service.evoucherdef;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codigo.codetest.common.util.datetime.ZoneOffsetStrings;
import codigo.codetest.evmgtsys.dao.IEvoucherDefDao;
import codigo.codetest.evmgtsys.domain.entity.EvoucherDef;
import codigo.codetest.evmgtsys.domain.entity.PaymentMethodDiscount;

@Service("evoucherDefModificationService")
public class EvoucherDefModificationService {

	private final IEvoucherDefDao evoucherDefDao;
	
	@Autowired
	public EvoucherDefModificationService(IEvoucherDefDao evoucherDefDao) {
		this.evoucherDefDao = evoucherDefDao;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void serve(EvoucherDefModificationServiceParam param) {
		EvoucherDef evoucherDefToReplace = this.prcd1PrepareForModiciation(param);
		
		this.prcd2ReplaceWith(evoucherDefToReplace);
	}
	
	private EvoucherDef prcd1PrepareForModiciation(EvoucherDefModificationServiceParam param) {
		EvoucherDef evoucherDefToReplace = param.getEvoucherDefToReplace();
		
		for (PaymentMethodDiscount paymentMethodDiscount: evoucherDefToReplace.getPaymentMethodDiscounts()) {
			paymentMethodDiscount.setId(evoucherDefToReplace.getId() + "_" + paymentMethodDiscount.getPmethodId());
		}
		
		evoucherDefToReplace.setModifiedOn(OffsetDateTime.now(ZoneOffset.of(ZoneOffsetStrings.PLUS_06_30)));
		
		return evoucherDefToReplace;
	}
	
	private void prcd2ReplaceWith(EvoucherDef evoucherDefToReplace) {
		Optional<EvoucherDef> optEvd = this.evoucherDefDao.findById(evoucherDefToReplace.getId());
		
		if (optEvd.isEmpty()) return;
		
		EvoucherDef oldEvd = optEvd.get();
		
		// Deleting PaymentMethodDiscounts and BuyTypes.
		oldEvd.getPaymentMethodDiscounts().clear();
		
		// Adding PaymentMethodDiscounts and BuyTypes.
		for (PaymentMethodDiscount pmdToReplace : evoucherDefToReplace.getPaymentMethodDiscounts()) {
			oldEvd.addPaymentMethodDiscount(pmdToReplace);
		}
		
		// Update EvoucherDef.
		oldEvd.setTitle(evoucherDefToReplace.getTitle());
		oldEvd.setDescription(evoucherDefToReplace.getDescription());
		oldEvd.setExpiryDate(evoucherDefToReplace.getExpiryDate());
		oldEvd.setImageLocation(evoucherDefToReplace.getImageLocation());
		oldEvd.setAmount(evoucherDefToReplace.getAmount());
		oldEvd.setQuantity(evoucherDefToReplace.getQuantity());
		
		oldEvd.setBuyingType(evoucherDefToReplace.getBuyingType());
		
		if (oldEvd.getBuyingType().charValue() == EvoucherDef.BUYING_TYPE_ZERO) {
			oldEvd.setGiftPerUserLimit(Integer.valueOf(0));
		} else {
			oldEvd.setGiftPerUserLimit(evoucherDefToReplace.getGiftPerUserLimit());
		}
		
		oldEvd.setMaxEvoucherLimit(evoucherDefToReplace.getMaxEvoucherLimit());
		
		oldEvd.setModifiedBy(evoucherDefToReplace.getModifiedBy());
		oldEvd.setModifiedOn(evoucherDefToReplace.getModifiedOn());
		
		this.evoucherDefDao.save(oldEvd);
	}
}
