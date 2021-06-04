package mini01team03.user.controller;

import java.sql.SQLException;

import mini01team03.user.model.KuserVO;
import mini01team03.user.model.UserVO;

public interface UserService {
	public UserVO getLoginInfo(String email) throws SQLException;

	public int insertKaProfile(KuserVO kakaoUser) throws SQLException;
	
	public int insertUser(UserVO userVO) throws SQLException;
	//아이디 중복체크 
	public int userIdchk(UserVO userid) throws SQLException;
	//아이디 찾기
	public String findid(UserVO userVO) throws SQLException;
}
