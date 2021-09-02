package codigo.codetest.estore.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import codigo.codetest.estore.domain.entity.EvoucherTask;

@Repository("iEvoucherTaskDao")
public interface IEvoucherTaskDao extends CrudRepository<EvoucherTask, String> {

}
