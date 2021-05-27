package mini01team03.user.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mini01team03.user.model.UserDAO;
import mini01team03.user.model.UserVO;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	UserDAO userDAO;

	@Override
	public UserVO getLoginInfo(String email) throws SQLException {
		UserVO userVO = userDAO.getLoginInfo(email);
		return userVO;
	}
	
	@Override
	   public int insertKaProfile(UserVO kakaoUser) throws SQLException {
	      int cnt = userDAO.insertKaProfile(kakaoUser);
	      return cnt;
	   }

}
