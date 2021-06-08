package mini01team03;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import mini01team03.user.model.UserDAO;
import mini01team03.user.model.UserVO;

//여기는 시큐리티 테스트용입니다.
@Controller //View를 리턴하겠다
public class IndexController {
	
	@Autowired
	UserDAO userDAO;
	
	/*@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;*/
	
	@GetMapping({"","/","index"})
	public String index(Authentication authentication, @AuthenticationPrincipal OAuth2User oauth,HttpServletRequest request, HttpSession session) throws Exception {
		if(authentication != null) {
			System.out.println("authentication:"+authentication);
			System.out.println(authentication.getPrincipal());
			OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
			System.out.println(oauth2User.getAttribute("email").toString());
			session = request.getSession();
			
			if(session.getAttribute("email") == null) {
				System.out.println(session.getAttribute("email"));
				if(oauth2User.getAttribute("email").toString() != null) {
					session.setAttribute("email", oauth2User.getAttribute("email").toString());
				}
			}
		}
		return "index";
	}
	
	
	@GetMapping("/user")
	public @ResponseBody String user() throws Exception {
		return "user";
	}
	
	/*@GetMapping("/login") //ajax 로그인과 이어지게 해야 함
	public @ResponseBody String login() throws Exception {
		return "login";
	}
	
	@PostMapping("/joinProc")
	public String joinProc(UserVO userVO) {
		System.out.println("회원가입 진행 : " + userVO);
		String rawPassword = userVO.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		userVO.setPassword(encPassword);
		user.setRole("ROLE_USER");
		userDAO.save(userVO);
		return "redirect:/";
	}*/
	
}
