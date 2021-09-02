package codigo.codetest.evmgtsys.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import codigo.codetest.evmgtsys.domain.entity.EvoucherDef;

@Repository("iEvoucherDefDao")
public interface IEvoucherDefDao extends PagingAndSortingRepository<EvoucherDef, String> {

}
