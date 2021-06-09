package mini01team03.user.controller;

import java.sql.SQLException;

import mini01team03.user.model.GuserVO;
import mini01team03.user.model.KuserVO;
import mini01team03.user.model.UserVO;

public interface UserService {
	public UserVO getLoginInfo(String email) throws SQLException;

	public int insertKaProfile(KuserVO kakaoUser) throws SQLException;
	
	public int insertGaProfile(GuserVO googleUser) throws SQLException;
	
	public int insertUser(UserVO userVO) throws SQLException;
	//아이디 중복체크 
	public int userIdchk(UserVO userid) throws SQLException;
	//아이디 찾기
	public String findid(UserVO userVO) throws SQLException;
	
	//비밀번호 찾기 
	public String findPwd(UserVO userVO)  throws SQLException;

	//마이페이지 회원정보 수정
	public void updateUserpwd(UserVO uptUserVO) throws SQLException;

	//마이페이지 회원정보 삭제
	public int userinfoDelete(UserVO userVO) throws SQLException; 
}
