package codigo.codetest.estore.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import codigo.codetest.estore.domain.entity.EvoucherPurchase;

@Repository("iEvoucherPurchaseDao")
public interface IEvoucherPurchaseDao 
	extends PagingAndSortingRepository<EvoucherPurchase, String> {

}
