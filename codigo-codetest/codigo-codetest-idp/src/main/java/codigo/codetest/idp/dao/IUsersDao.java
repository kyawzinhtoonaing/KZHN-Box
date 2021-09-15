package codigo.codetest.idp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import codigo.codetest.idp.domain.entity.User;

@Repository("iUsersDao")
public interface IUsersDao extends JpaRepository<User, String> {

}
