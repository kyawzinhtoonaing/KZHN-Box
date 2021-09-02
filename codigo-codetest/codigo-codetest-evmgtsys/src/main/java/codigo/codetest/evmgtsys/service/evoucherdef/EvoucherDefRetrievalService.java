package codigo.codetest.evmgtsys.service.evoucherdef;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import codigo.codetest.evmgtsys.dao.IEvoucherDefDao;
import codigo.codetest.evmgtsys.domain.entity.EvoucherDef;
import codigo.codetest.evmgtsys.domain.entity.PaymentMethodDiscount;

@Service("evoucherDefRetrievalService")
public class EvoucherDefRetrievalService {

	private final IEvoucherDefDao evoucherDefDao;
	
	@Autowired
	public EvoucherDefRetrievalService(IEvoucherDefDao evoucherDefDao) {
		this.evoucherDefDao = evoucherDefDao;
	}
	
	public EvoucherDefRetrievalServiceResult serve(EvoucherDefRetrievalServiceParam param) {
		
		// prcd1 retrieve EvoucherDefs from database, and return the result
		return prcd1Retrieve(param);
	}
	
	private EvoucherDefRetrievalServiceResult prcd1Retrieve(EvoucherDefRetrievalServiceParam param) {
		PageRequest page = PageRequest.of(param.getCurrentPage(), param.getPageSize(), Sort.by("title").ascending());
		Page<EvoucherDef> pageResult = this.evoucherDefDao.findAll(page);
		
		int currentPage = pageResult.getNumber();
		int pageSize = pageResult.getSize();
		int totalPages = pageResult.getTotalPages();
		long totalRecords = pageResult.getTotalElements();
		
		EvoucherDefRetrievalServiceResult result = new EvoucherDefRetrievalServiceResult();
		result.setCurrentPage(currentPage);
		result.setPageSize(pageSize);
		result.setTotalPages(totalPages);
		result.setTotalRecords(totalRecords);
		
		//if (totalRecords == 0 || pageSize == 0) {
		if (pageResult.isEmpty()) {
			result.setEvoucherDefs(null);
			return result;
		}
		
		List<EvoucherDef> evdList = pageResult.getContent();
		
		List<EvoucherDef> evoucherDefs = new ArrayList<EvoucherDef>();
		for (EvoucherDef evd : evdList) {
			EvoucherDef evoucherDef = new EvoucherDef(evd.getId(), 
				evd.getTitle(),
				evd.getDescription(), 
				evd.getExpiryDate(), 
				evd.getImageLocation(), 
				evd.getAmount(),
				evd.getQuantity(),
				evd.getBuyingType(),
				evd.getMaxEvoucherLimit(),
				evd.getGiftPerUserLimit(),
				evd.getIsactive(),
				evd.getCreatedBy(),
				evd.getCreatedOn(),
				evd.getModifiedBy(),
				evd.getModifiedOn());
			
			Set<PaymentMethodDiscount> pmdSet = evd.getPaymentMethodDiscounts();
			for (PaymentMethodDiscount pmd : pmdSet) {
				PaymentMethodDiscount paymentMethodDiscount = new PaymentMethodDiscount(pmd.getId(), pmd.getPmethodId(), pmd.getDiscountPercent());
				evoucherDef.getPaymentMethodDiscounts().add(paymentMethodDiscount);
			}
			
			evoucherDefs.add(evoucherDef);
		}
		
		result.setEvoucherDefs(evoucherDefs);
		
		return result;
	}
}
