package mini01team03.user.controller;


import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import mini01team03.user.model.KakaoProfile;
import mini01team03.user.model.KuserVO;
import mini01team03.user.model.OAuthToken;
import mini01team03.user.model.UserVO;

@Controller
public class LoginController {
	
	private static Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	UserService userService;
	
	  @ResponseBody
	  @PostMapping("login")
	  public String loginPost(@RequestBody UserVO userVO, HttpServletRequest request, HttpSession session) throws SQLException {
		  //System.out.println(userVO.getEmail());
		  String email = userVO.getEmail();
		  UserVO dbUserVO = userService.getLoginInfo(email);
		  if(userVO.getUserpwd().equals(dbUserVO.getUserpwd())) {
			 session = request.getSession();
			 session.setAttribute("email", dbUserVO.getUserid());
			 System.out.print(session.getAttribute("email"));
		   return "ok";  //session 추가
		  }else {
			  return "fail";
		  }
	  }
	  
	  //회원가입 ajax
	  @ResponseBody
	  @PostMapping("join")
	  public String joinUser(@RequestBody UserVO userVO) throws SQLException {
		  userService.insertUser(userVO);
		  return "joinok";
	  }
	  
	  //아이디 중복체크 
	  @ResponseBody
	  @PostMapping("chkid")
	  public int userIdchk(@RequestBody UserVO userid) throws SQLException {
		   int result = userService.userIdchk(userid);
		   return result;
	  }
	  
	  //카카오 로그인
	  @GetMapping("auth/kakao/callback")
	   public @ResponseBody String kakaoCallback(String code) throws SQLException { // responseboy는 data를 리턴해주는 컨트롤러 함수, code는 카카오에서 주는 인가코드임
	      
	      //post 방식으로 key=value 데이터를 요청(카카오톡으로)
	      //예전에는 HttpsURLConnection, Retrofit2(안드로이드에서 자주 사용), OkHttp 이런 라이브러리도 있음 
	      RestTemplate rt = new RestTemplate(); //이 라이브러리 사용하면 http 요청을 편하게 할 수 있음
	      
	      //HttpHeader 오브젝트 생성
	      HttpHeaders headers = new HttpHeaders();
	      headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8"); //내가 전송할 body http 데이터가 key value 형태의 데이터라고 알려주는 것임
	      
	      //HttpBody 오브젝트 생성
	      MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	      params.add("grant_type", "authorization_code");
	      params.add("client_id", "f4756aac75cb4481cb7c932449df2447");
	      params.add("redirect_uri", "http://localhost:8080/auth/kakao/callback");
	      params.add("code", code);
	      
	      //HttpHeader와 HttpBody를 하나의 오브젝트에 담기 -> why? exchange 함수를 보면 HttpEntity 오브젝트를 넣어야 하기 때문에 
	      HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers); 
	      
	      //Http 요청하기 - POST방식으로 - 그리고 response 변수에 응답 받기
	      ResponseEntity<String> response = rt.exchange(
	         "https://kauth.kakao.com/oauth/token",
	         HttpMethod.POST,
	         kakaoTokenRequest,
	         String.class
	      );
	      
	      //json data를 처리하기 힘드니깐 / 이 입력받은 값을 담을 자바 오브젝트 생성하기 -> OAuthToken.java
	      //json data 담는 라이브러리 : Gson, Json Simple, ObjectMapper 등이 있음. 
	      ObjectMapper objectMapper = new ObjectMapper();
	      OAuthToken oauthToken = null;
	      try {
	         oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
	      } catch (JsonMappingException e) {
	         e.printStackTrace();
	      } catch (JsonProcessingException e) {
	         e.printStackTrace();
	      }
	      
	      //System.out.println(oauthToken);
	      System.out.println("카카오 엑세스 토큰:"+oauthToken.getAccess_token());  
	      
	      //////////////////////////////////////////////////////////////////다시 시작 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	      
	      RestTemplate rt2 = new RestTemplate(); //이 라이브러리 사용하면 http 요청을 편하게 할 수 있음
	      
	      //HttpHeader 오브젝트 생성
	      HttpHeaders headers2 = new HttpHeaders();
	      headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8"); //내가 전송할 body http 데이터가 key value 형태의 데이터라고 알려주는 것임
	      headers2.add("Authorization", "Bearer "+oauthToken.getAccess_token());
	      
	      //HttpHeader와 HttpBody를 하나의 오브젝트에 담기 -> why? exchange 함수를 보면 HttpEntity 오브젝트를 넣어야 하기 때문에 
	      HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = new HttpEntity<>(headers2); 
	      
	      //Http 요청하기 - POST방식으로 - 그리고 response 변수에 응답 받기
	      ResponseEntity<String> response2 = rt2.exchange(
	         "https://kapi.kakao.com/v2/user/me",
	         HttpMethod.POST,
	         kakaoProfileRequest2,
	         String.class
	      );
	      
	      System.out.println(response2.getBody());
	      //return response2.getBody(); //response만 불러오면 모든 값(json data)이 불러와짐. 그 중에 볼 것만 함수로 선택해서 보자.
	      
	      ObjectMapper objectMapper2 = new ObjectMapper();
	      KakaoProfile kakaoProfile = null;
	      
	      try {
	         kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
	      } catch (JsonMappingException e) {
	         e.printStackTrace();
	      } catch (JsonProcessingException e) {
	         e.printStackTrace();
	      }
	      
	      //User 오브젝트 : username, userpwd, email, userid
	      System.out.println("카카오 유저네임:"+kakaoProfile.getProperties().getNickname());
	      System.out.println("카카오 아이디:"+kakaoProfile.getId());
	      System.out.println("카카오 이메일:"+kakaoProfile.getKakao_account().getEmail());
	      
	      System.out.println("블로그 유저네임:"+kakaoProfile.getProperties().getNickname());
	      System.out.println("블로그 유저id:"+kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId());
	      System.out.println("블로그 이메일:"+kakaoProfile.getKakao_account().getEmail());
	      
	      KuserVO kakaoUser = KuserVO.builder()
	            .userid(kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId())
	            .userpwd("123")  //비밀번호 임시로 하기
	            .email(kakaoProfile.getKakao_account().getEmail())
	            .username(kakaoProfile.getProperties().getNickname())
	            //.oauth("kakao")
	            .build();
	      System.out.println("포스트 매핑 : ");
	      
	      userService.insertKaProfile(kakaoUser);
	      
	      
	      return response2.getBody();
	      
	   }
	}

		
	 

