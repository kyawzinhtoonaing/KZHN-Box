package codigo.codetest.idp.service.users;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import codigo.codetest.idp.dao.IUsersDao;
import codigo.codetest.idp.domain.entity.User;
import codigo.codetest.idp.service.users.exception.UserNotFoundException;

@Service("userRetrievalByUsernameService")
public class UserRetrievalByUsernameService {
	
	private final IUsersDao iUsersDao;
	
	@PersistenceContext
    private EntityManager entityManager;
	
	@Autowired
	public UserRetrievalByUsernameService(IUsersDao iUsersDao) {
		this.iUsersDao = iUsersDao;
	}

	public UserRetrievalByUsernameServiceResult serve(UserRetrievalByUsernameServiceParam param) throws UserNotFoundException {
		User user = this.prcd1(param.getUsername());
		
		return this.prcd2PrepareResult(user);
	}
	
	private User prcd1(String username) throws UserNotFoundException {
		Optional<User> optUser = this.iUsersDao.findById(username);
		if (optUser.isEmpty()) {
			throw new UserNotFoundException();
		}
		
		User user = optUser.get();
		this.entityManager.detach(user);
		
		
		return user;
	}
	
	private UserRetrievalByUsernameServiceResult prcd2PrepareResult(User user) {
		UserRetrievalByUsernameServiceResult result = new UserRetrievalByUsernameServiceResult();
		result.setUser(user);
		
		return result;
	}
}
