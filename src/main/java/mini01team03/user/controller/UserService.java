package mini01team03.user.controller;

import java.sql.SQLException;

import mini01team03.user.model.UserVO;

public interface UserService {
	public UserVO getLoginInfo(String email) throws SQLException;

	public int insertKaProfile(UserVO kakaoUser) throws SQLException;
}
