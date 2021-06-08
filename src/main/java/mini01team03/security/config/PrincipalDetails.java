package mini01team03.security.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;


import lombok.Data;

import mini01team03.user.model.UserVO;

// Authentication 객체에 저장할 수 있는 유일한 타입
@Data
public class PrincipalDetails implements UserDetails, OAuth2User{

	private static final long serialVersionUID = 1L;
	private UserVO userVO;
	private Map<String, Object> attributes; //PrincipalOauth2UserService에 있는 getAttributes 형태가 Map<string, object>임

	// 일반 시큐리티 로그인시 사용 //이거 제대로 구현해야함 !! 
	public PrincipalDetails(UserVO userVO) {
		this.userVO = userVO;
	}
	
	// OAuth2.0 로그인시 사용
	public PrincipalDetails(UserVO userVO, Map<String, Object> attributes) {
		this.userVO = userVO;
		this.attributes = attributes;
	}
	
	public UserVO getUserVO() {
		return userVO;
	}

	@Override
	public String getUsername() {
		return userVO.getUsername();
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
		return userVO.getUserid()+"";
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collet = new ArrayList<GrantedAuthority>();
		collet.add(()->{ return userVO.getEmail();});
		return collet;  //아무렇게나 쓴거임
	}

	@Override
	public String getPassword() {
		return userVO.getUserpwd();
	}
	
}

