package codigo.codetest.evmgtsys.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import codigo.codetest.evmgtsys.domain.entity.MstPaymentMethod;

@Repository("iMstPaymentMethodDao")
public interface IMstPaymentMethodDao extends CrudRepository<MstPaymentMethod, String> {

}
