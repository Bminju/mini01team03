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
	
	public int insertGaProfile(GuserVO googleUser) throws SQLException;
	
	public UserVO findByUsername(String username);

}
