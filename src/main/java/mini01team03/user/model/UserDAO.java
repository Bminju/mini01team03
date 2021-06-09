package mini01team03.user.model;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserDAO {
	
	//기본로그인
	public UserVO getLoginInfo(String email) throws SQLException;
	//카카오로그인
	public int insertKaProfile(KuserVO kakaoUser) throws SQLException;
	//회원가입
	public int insertUser(UserVO userVO) throws SQLException;
	//아이디 중복체크 
	public int userIdchk(UserVO userid) throws SQLException;
	//아이디 찾기
	public String findid(UserVO userVO) throws SQLException;
	//비밀번호 변경 
	public UserVO findPwd(UserVO userVO) throws SQLException;
	public UserVO findUserByUserId(String email) throws SQLException;
	public void updateUserpwd(UserVO uptUservo) throws SQLException;
	//마이페이지 회원정보 삭제
	public int userinfoDelete(UserVO userVO) throws SQLException; 
}

