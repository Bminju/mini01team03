package mini01team03.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity //스프링 시큐리티 필터가 스프링 필터체인에 등록이 됩니다.
//여기서 말한 스프링 시큐리티 필터는 SecurityConfig임. 
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	PrincipalOauth2UserService principalOauth2UserService;
	
	@Autowired
	PrincipalDetailsService principalDetailsService;  
	
	@Autowired
	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

	@Autowired
	private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
	
	// Bean을 적으면 해당 메서드의 리턴되는 오브젝트를 IoC로 등록해준다.
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable(); //비활성화
		http.authorizeRequests()
			.antMatchers("/user/**").authenticated()
			//.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
			//.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') and hasRole('ROLE_USER')")
			//.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
			.anyRequest().permitAll() //위에 특정한 세 개 주소 이외에는 권한이 다 허용됩니다.
			.and()
			.formLogin()
			.loginPage("/login") //위에 특정 3개 주소에 들어갈 때 인증을 거치지 않으면 자동적으로 login 페이지로 매핑된다.
			.successHandler(customAuthenticationSuccessHandler)
			.failureHandler(customAuthenticationFailureHandler)
			.usernameParameter("userid") //로그인 html에서 쓰는 name이 username이 아닐 시 적어줘야지 principalDetailsService에서 값이 잘 매칭됨.
			.passwordParameter("userpwd")
			.loginProcessingUrl("/login") //login주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해준다.
			.defaultSuccessUrl("/")
			.and()
			.oauth2Login()
			.loginPage("/login")
			//구글 로그인이 완료된 뒤의 후처리가 필요. Tip. 엑세스 토큰+사용자 프로필 정보를 한방에 받음!
			.userInfoEndpoint()
			.userService(principalOauth2UserService);
			
		
			
			
	}
	
}
