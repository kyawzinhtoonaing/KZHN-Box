package codigo.codetest.estore.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import codigo.codetest.estore.domain.entity.UserPaymentMethod;

@Repository("iUserPaymentMethodDao")
public interface IUserPaymentMethodDao extends CrudRepository<UserPaymentMethod, String> {

}
