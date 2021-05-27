package mini01team03.user.model;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserDAO {
	public UserVO getLoginInfo(String email) throws SQLException;

	
	public int insertKaProfile(UserVO kakaoUser) throws SQLException;

}
