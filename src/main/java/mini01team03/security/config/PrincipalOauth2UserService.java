package mini01team03.security.config;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import mini01team03.user.controller.UserService;
import mini01team03.user.model.GuserVO;
import mini01team03.user.model.KuserVO;
import mini01team03.user.model.UserDAO;
import mini01team03.user.model.UserVO;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired 
	UserDAO userDAO;
	
	@Autowired 
	UserService userService;
	
	// HttpServletRequest request, HttpSession session 이것을 추가하면 override 어노테이션을 제거하라고 뜸. 근데 제거하면 로그인 자체가 진행이 안됨. 
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
		//code를 통해 구성한 정보
		System.out.println("getClientRegistration:" +userRequest.getClientRegistration()); //registrationid로 어떤 OAuth로 로그인 했는지 확인가능
		System.out.println("getAccessToken:" +userRequest.getAccessToken().getTokenValue()); //토큰은 여기서 그닥 중요하지 않음!
		 //구글로그인 버튼 클릭->구글로그인창->로그인 완료->code를 리턴(OAuth-Client라이브러리가 받음)->code를 통해서 바로 AccessToken을 요청함
		//->여기까지가 userRequest 정보
		//userRequest를 이용하여 회원프로필 받아야함(사용하는 함수:loadUser함수)->구글로부터 회원 프로필 받음
		
		OAuth2User oauth2User = super.loadUser(userRequest); // google의 회원 프로필 조회
		//token을 통해 응답받은 회원정보
		System.out.println("getAttributes:" +oauth2User.getAttributes());
		//구글 로그인이 완료된 뒤의 후처리 시작 !! 회원가입을 강제로 진행해볼 예정
		//String role=userRequest.getClientRegistration().getClientId();
		//System.out.println(userRequest.getClientRegistration().getClientId());
		String email=oauth2User.getAttribute("email");
		String userid=email+"_"+oauth2User.getAttribute("sub");
		String username=oauth2User.getAttribute("name");
		String userpwd=bCryptPasswordEncoder.encode("겟인데어"); //크게 의미 없음
		
		UserVO googleUser = UserVO.builder()
	            .userid(userid)
	            .userpwd(userpwd)  //비밀번호 임시로 하기
	            .email(email)
	            .username(username)
	            .role("google")
	            .build();
		
		UserVO originUser;
		String email1 = googleUser.getEmail();
		
			try {
				originUser = userService.getLoginInfo(email1);
				if(originUser == null) {
					System.out.println("기존 회원이 아니기에 자동 회원가입을 진행합니다");
					userService.insertUser(googleUser);	
				}
				System.out.println("자동 로그인을 진행합니다.");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 로그인 처리 코드 작성하기
			//session = request.getSession();
			//session.setAttribute("email", googleUser.getUserid());
			
		
		
		/*if(originUser == null) {
			System.out.println("기존 회원이 아니기에 자동 회원가입을 진행합니다");
			userService.insertUser(googleUser);
			
		}*/
		
		//return "/test/oauth/login";
		return new PrincipalDetails(googleUser, oauth2User.getAttributes()); //이것이 authentication 객체 안에 들어가게 됨
	}
}
