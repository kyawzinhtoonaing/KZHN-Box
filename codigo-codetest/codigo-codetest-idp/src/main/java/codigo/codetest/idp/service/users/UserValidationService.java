package codigo.codetest.idp.service.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import codigo.codetest.idp.domain.entity.User;
import codigo.codetest.idp.service.users.exception.InvalidUserException;
import codigo.codetest.idp.service.users.exception.UserNotFoundException;

@Service("userValidationService")
public class UserValidationService {
	
	private final UserRetrievalByUsernameService userRetrievalByUsernameService;
	
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public UserValidationService(
			UserRetrievalByUsernameService userRetrievalByUsernameService,
			PasswordEncoder passwordEncoder) {
		this.userRetrievalByUsernameService = userRetrievalByUsernameService;
		this.passwordEncoder = passwordEncoder;
	}
	
	public UserValidationServiceResult serve(UserValidationServiceParam param) throws UserNotFoundException, 
			InvalidUserException {
		
		User verifiedUser = this.prcd1CheckValidity(param);
		
		return this.prcd2PrepareResult(verifiedUser);
	}
	
	private User prcd1CheckValidity(UserValidationServiceParam param) throws UserNotFoundException, 
			InvalidUserException {
		User incomingUser = param.getIncomingUser();
		
		UserRetrievalByUsernameServiceParam svParam = new UserRetrievalByUsernameServiceParam();
		svParam.setUsername(incomingUser.getUsername());
		UserRetrievalByUsernameServiceResult svResult = this.userRetrievalByUsernameService.serve(svParam);
		User existingUser = svResult.getUser();
		
		// Password match
		if (!this.passwordEncoder.matches(incomingUser.getPassword(), existingUser.getPassword())) {
			throw new InvalidUserException();
		}
		
		existingUser.setPassword(null);
		
		return existingUser;
	}
	
	private UserValidationServiceResult prcd2PrepareResult(User verifiedUser) {
		UserValidationServiceResult result = new UserValidationServiceResult();
		result.setVerifiedUser(verifiedUser);
		
		return result;
	}
}
