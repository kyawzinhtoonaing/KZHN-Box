package codigo.codetest.evmgtsys.service.evoucherdef;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codigo.codetest.common.util.datetime.ZoneOffsetStrings;
import codigo.codetest.evmgtsys.dao.IEvoucherDefDao;
import codigo.codetest.evmgtsys.domain.entity.EvoucherDef;
import codigo.codetest.evmgtsys.domain.entity.PaymentMethodDiscount;

@Service("evoucherDefCreationService")
public class EvoucherDefCreationService {
	
	private final IEvoucherDefDao evoucherDefDao;
	
	@Autowired
	public EvoucherDefCreationService(IEvoucherDefDao evoucherDefDao) {
		this.evoucherDefDao = evoucherDefDao;
	}

	@Transactional(rollbackFor = Exception.class)
	public void serve(EvoucherDefCreationServiceParam param) throws Exception {
		
		// prcd1 prepare to save
		EvoucherDef evoucherDefToSave = this.prcd1PrepareToSave(param.getEvoucherDefToSave());
		
		// prcd2 save EvoucherDef
		this.prcd2SaveEvoucherDef(evoucherDefToSave);
	}
	
	private EvoucherDef prcd1PrepareToSave(EvoucherDef evoucherDefToSave) {
		
		evoucherDefToSave.setId(UUID.randomUUID().toString().split("-")[0]);
		
		if (evoucherDefToSave.getBuyingType().charValue() == EvoucherDef.BUYING_TYPE_ZERO) {
			evoucherDefToSave.setGiftPerUserLimit(Integer.valueOf(0));
		}
		
		evoucherDefToSave.setIsactive(Boolean.TRUE);
		evoucherDefToSave.setCreatedOn(OffsetDateTime.now(ZoneOffset.of(ZoneOffsetStrings.PLUS_06_30)));
		
		for (PaymentMethodDiscount paymentMethodDiscount: evoucherDefToSave.getPaymentMethodDiscounts()) {
			paymentMethodDiscount.setId(evoucherDefToSave.getId() + "_" + paymentMethodDiscount.getPmethodId());
		}
		
		return evoucherDefToSave;
	}
	
	private void prcd2SaveEvoucherDef(EvoucherDef evoucherDefToSave) {
		this.evoucherDefDao.save(evoucherDefToSave);
	}
}
