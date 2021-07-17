package mini01team03.user.model;

import lombok.Builder;
import lombok.Data;

//구글 로그인 정보받기용 

@Data
@Builder
public class GuserVO {
	private String userid;
	private String userpwd;
	private String username;
	private String email;
	private String role;
}
