package mini01team03.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import mini01team03.user.model.UserDAO;
import mini01team03.user.model.UserVO;

@Service
public class PrincipalDetailsService implements UserDetailsService{

	@Autowired
	private UserDAO userDAO;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserVO userVO = userDAO.findByUsername(username);
		if(userVO == null) {
			return null;
		}else {
			return new PrincipalDetails(userVO);
		}
		
	}

}
