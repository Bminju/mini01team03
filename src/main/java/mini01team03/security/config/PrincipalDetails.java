package mini01team03.security.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;


import lombok.Data;
import mini01team03.user.model.GuserVO;
import mini01team03.user.model.UserVO;

// Authentication 객체에 저장할 수 있는 유일한 타입
//시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
//로그인을 진행이 완료가 되면 시큐리티가 가지고 있는 session을 만들어줍니다. (Security contextHolder 키 값에 세션을 저장한다)
//시큐리티가 가지고 있는 session에 들어갈 수 있는 오브젝트는 authentication이여야 함. 
//Authentication 안에 user 정보가 있어야 함. User 오브젝트타입 => UserDetails 타입 객체. 
@Data
public class PrincipalDetails implements UserDetails, OAuth2User{

	private static final long serialVersionUID = 1L;
	private UserVO userVO;
	private GuserVO guserVO;  //GuserVO 로 바꾸고 ajax 해보기
	private Map<String, Object> attributes; //PrincipalOauth2UserService에 있는 getAttributes 형태가 Map<string, object>임


	// 일반 시큐리티 로그인시 사용 //이거 제대로 구현해야함 !! 
	public PrincipalDetails(GuserVO guserVO) {
		this.guserVO = guserVO;
	}
	
	// OAuth2.0 로그인시 사용
	public PrincipalDetails(GuserVO guserVO, Map<String, Object> attributes) {
		this.guserVO = guserVO;
		this.attributes = attributes;
	}
	
	public GuserVO getGuserVO() {
		return guserVO;
	}

	@Override
	public String getUsername() {
		return guserVO.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	// 리소스 서버로 부터 받는 회원정보
	@Override
	public Map<String, Object> getAttributes() {
		return attributes; //모든 정보를 한번에 저장!
	}

	// User의 PrimaryKey
	@Override
	public String getName() {
		return guserVO.getUserid()+"";
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collet = new ArrayList<GrantedAuthority>();
		collet.add(()->{ return guserVO.getEmail();});
		return collet;  //아무렇게나 쓴거임
	}

	@Override
	public String getPassword() {
		return guserVO.getUserpwd();
	}
	
}

