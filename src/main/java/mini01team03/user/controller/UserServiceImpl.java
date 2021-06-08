package mini01team03.user.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mini01team03.user.model.GuserVO;
import mini01team03.user.model.KuserVO;
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
	   public int insertKaProfile(KuserVO kakaoUser) throws SQLException {
	      int cnt = userDAO.insertKaProfile(kakaoUser);
	      return cnt;
	   }

	@Override
	public int insertUser(UserVO userVO) throws SQLException {
		int cnt = userDAO.insertUser(userVO);
		return cnt;
	}

	@Override
	public int userIdchk(UserVO userid) throws SQLException {
		int result = userDAO.userIdchk(userid);
		return result;
	}

	@Override
	public String findid(UserVO userVO) throws SQLException {
		String result = userDAO.findid(userVO);
		return result;
	}

	@Override
	public String findPwd(UserVO userVO) throws SQLException {
		UserVO dbUserVO = userDAO.findPwd(userVO);
		String result = userVO.getEmail();
		return result;
		
	}

	
	@Override
	public int insertGaProfile(GuserVO googleUser) throws SQLException {
		int cnt = userDAO.insertGaProfile(googleUser);
		return cnt;
	}
}
