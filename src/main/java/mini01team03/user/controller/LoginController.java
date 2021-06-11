package mini01team03.user.controller;


import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
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
import mini01team03.user.model.MailVO;
import mini01team03.user.model.OAuthToken;
import mini01team03.user.model.UserVO;

@Controller
public class LoginController {
	
	private static Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	UserService userService; 
	@Autowired
	SendEmailService sendEmailService;
	  //로그인 ajax
	  @ResponseBody
	  @GetMapping("/login")
		public String Login(HttpServletRequest request, HttpServletResponse response) {
			RequestCache requestCache = new HttpSessionRequestCache();
			SavedRequest savedRequest = requestCache.getRequest(request, response); 
			
			try {
				//여러가지 이유로 이전페이지 정보가 없는 경우가 있음.
				//https://stackoverflow.com/questions/6880659/in-what-cases-will-http-referer-be-empty
				request.getSession().setAttribute("prevPage", savedRequest.getRedirectUrl());
			} catch(NullPointerException e) {
				request.getSession().setAttribute("prevPage", "/");
			}
			return "login";
		}
	  /*@PostMapping("/login1")
	  public String Login(@RequestBody UserVO userVO, HttpServletRequest request, HttpServletResponse response) throws SQLException {
			RequestCache requestCache = new HttpSessionRequestCache();
			SavedRequest savedRequest = requestCache.getRequest(request, response); 
			System.out.println(userVO.getUserid());
			String userid = userVO.getUserid();
			UserVO dbUserVO = userService.getLoginInfo(userid);
			try {
				//여러가지 이유로 이전페이지 정보가 없는 경우가 있음.
				//https://stackoverflow.com/questions/6880659/in-what-cases-will-http-referer-be-empty
				if(userVO.getUserpwd().equals(dbUserVO.getUserpwd())) {
				request.getSession().setAttribute("email", dbUserVO.getUserid());
				}
			} catch(NullPointerException e) {
				request.getSession().setAttribute("prevPage", "/");
			}
			return "login";
		}*/
	  /*public String loginPost(@RequestBody UserVO userVO, HttpServletRequest request, HttpSession session) throws SQLException {
		  //System.out.println(userVO.getEmail());
		  String userid = userVO.getUserid();
		  UserVO dbUserVO = userService.getLoginInfo(userid); //이렇게 해버리면 아이디가 맞는지 틀리는지 체크가 안되지 않나?
		  if(userVO.getUserpwd().equals(dbUserVO.getUserpwd())) {
			 session = request.getSession();  //세션을 얻는다.
			 session.setAttribute("email", dbUserVO.getUserid());//setAttribute는 name, value쌍으로 객체를 저장
			 System.out.print(session.getAttribute("email"));//email을 키값으로 밸류인 getUserid를 불러옴
		   return "ok";  //session 추가
		  }else {
			  return "fail";
		  }
	  }*/
	  //아이디 찾기 ajax
	  @ResponseBody
	  @PostMapping("findid")
	  public String findid (@RequestBody UserVO userVO) throws SQLException {
		 // System.out.println(userVO.getUsername());
		  String result = userService.findid(userVO); 
		  return result; //result에 담긴 값은 조건에 맞는 userid임
	  }
	  
	  //비밀번호 찾기 ajax
	  @ResponseBody
	  @PostMapping("findPwd")
	  public String findPwd(@RequestBody UserVO userVO) throws SQLException {
		 //System.out.println(userVO);
		  String result = userService.findPwd(userVO); 
		  return result;
	  }
	  //등록된 이메일로 임시비밀번호를 발송, 발송된 임시비번으로 사용자 pw 변경 
	  @ResponseBody
	  @PostMapping("findPwd/sendEmail")
	  public void sendEmail(@RequestBody UserVO userVO) throws SQLException {
		  System.out.println("cont Email :" + userVO.getEmail());
		  MailVO dto = sendEmailService.createMailAndChangePassword(userVO);
	      sendEmailService.mailSend(dto);
	  }
	  
	  //회원가입 ajax
	  @ResponseBody
	  @PostMapping("join")
	  public String joinUser(@RequestBody UserVO userVO, HttpServletRequest request, HttpSession session) throws SQLException {
		  userService.insertUser(userVO);
		  session = request.getSession();
	      session.setAttribute("email", userVO.getUserid());//setAttribute는 name, value쌍으로 객체를 저장
	      return "redirect:/index";
	  }
	  
	  //아이디 중복체크 
	  @ResponseBody
	  @PostMapping("chkid")
	  public int userIdchk(@RequestBody UserVO userid) throws SQLException {
		   int result = userService.userIdchk(userid);
		   return result;
	  }
	  
	  //로그아웃시 세션 해제
	  @GetMapping("logout")
	  @ResponseBody
	  public String logout(HttpSession session) throws Exception {
		  session.invalidate();
		  return "logout";
	  }
	  
	  //마이페이지에 회원정보 뿌리기
	  @ResponseBody
	  @GetMapping("modify")
	  public UserVO modify(HttpServletRequest request,HttpSession session,UserVO userVO)throws Exception  {
		  Object my_info = session.getAttribute("email"); //세션에 저장 된 값을 얻기 위함
		  String ma_info = (String)my_info; //cast연산자로 String 형태로 형 변환을 한다.
		  //System.out.println(ma_info); //세션에 존재하는 아이디 값을 불러왔음
		 	  
		  UserVO modiVO = userService.getLoginInfo(ma_info);
		  
		 // System.out.println(modiVO.getUserid());
		 // System.out.println(modiVO.getUserpwd());
		 //System.out.println(modiVO.getUsername());
		 // System.out.println(modiVO.getEmail());
		  
		  return modiVO;
	  }
	  //회원정보 수정 시 비밀번호 확인
	  @ResponseBody
	  @PostMapping("chkoriPwd")
	  public String oriPwdchk(@RequestBody UserVO userVO, HttpServletRequest request,HttpSession session) throws SQLException {
		  Object userinfo = session.getAttribute("email"); //세션에 저장 된 아이디 값을 불러온다.
		  String userinfo2 = (String)userinfo; //cast연산자로 String 형태로 형 변환을 한다.
		  System.out.println(userinfo2); //세션에 존재하는 아이디 값을 불러왔음
		  UserVO passVO = userService.getLoginInfo(userinfo2); //세션과 일치하는 유저의 모든 정보 불러옴
		  System.out.println(passVO.getUserpwd());
		  
		  //세션에 존재하는 아이디 값의 비번과 입력받은 비번이 일치하면 리턴 OK
		  if(userVO.getUserpwd().equals(passVO.getUserpwd())) {
			  
			  return "ok";  
		  }else {
			  return "fail";
		  }  
	  }
	  //마이페이지에서 회원정보 수정하기
	  @ResponseBody
	  @PostMapping("modify/update")
	   public String modiUpdate(@RequestBody UserVO userVO, HttpServletRequest request, HttpSession session) throws SQLException {
		  Object userinfo = session.getAttribute("email"); //세션에 저장 된 아이디 값을 불러온다.
		  String userinfo2 = (String)userinfo; //cast연산자로 String 형태로 형 변환을 한다.
		  UserVO passVO = userService.getLoginInfo(userinfo2); //세션과 일치하는 유저의 모든 정보 불러옴
		  int id = passVO.getId(); //세션의 id값을 따로 뽑아
		  userVO.setId(id); //그 id값을 userVO(입력받은 값)에 넣어
		  userService.updateUserpwd(userVO); //update쿼리 실행
		  System.out.println(userVO.getUserpwd());
		  return "ok";
	  }
	  //마이페이지 회원정보 삭제
	  @GetMapping("delete")
	  @ResponseBody
	  public int infoDelete(HttpSession session) throws Exception {
		  Object userinfo = session.getAttribute("email"); //세션에 저장 된 아이디 값을 불러온다.
		  String userinfo2 = (String)userinfo; //cast연산자로 String 형태로 형 변환을 한다.
		  UserVO passVO = userService.getLoginInfo(userinfo2); //세션과 일치하는 유저의 모든 정보 불러옴
		  //passVO.getId(); //세션의 id값을 따로 뽑아
		  int cnt = userService.userinfoDelete(passVO);
		  System.out.println(cnt);
		  session.invalidate();
		  return cnt;
	  }
	  
	  
	  
	  
	  
	  
	  //카카오 로그인
	  @GetMapping("auth/kakao/callback")
	   public String kakaoCallback(String code, HttpServletRequest request, HttpSession session) throws SQLException { 
		  // responseboy는 data를 리턴해주는 컨트롤러 함수, code는 카카오에서 주는 인가코드임
	    
	      //post 방식으로 key=value 데이터를 요청(카카오톡으로)
	      //예전에는 HttpsURLConnection, Retrofit2(안드로이드에서 자주 사용), OkHttp 이런 라이브러리도 있음 
	      RestTemplate rt = new RestTemplate(); //이 라이브러리 사용하면 http 요청을 편하게 할 수 있음
	      
	      //HttpHeader 오브젝트 생성
	      HttpHeaders headers = new HttpHeaders();
	      headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8"); 
	      //내가 전송할 body http 데이터가 key value 형태의 데이터라고 알려주는 것임
	      
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
	            .role("kakao")
	            .build();
	      //System.out.println(kakaoUser.getEmail());
	      
	      String userid = kakaoUser.getUserid();
	      UserVO originUser = userService.getLoginInfo(userid);
	      if(originUser == null || originUser.getUserid() == null) {
	    	  System.out.println("기존 회원이 아니기에 자동 회원가입을 진행합니다");
	    	  userService.insertKaProfile(kakaoUser);
	      }
	      
	      System.out.println("자동 로그인을 진행합니다.");
			// 로그인 처리 코드 작성하기
	      	session = request.getSession();
			session.setAttribute("email", kakaoUser.getUserid());//setAttribute는 name, value쌍으로 객체를 저장
					
			return "redirect:/index";
	      
	      
	   }
	  
	  
	}

		
	 

