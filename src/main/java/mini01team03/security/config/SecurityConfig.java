package mini01team03.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
	
	// Bean을 적으면 해당 메서드의 리턴되는 오브젝트를 IoC로 등록해준다.
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
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
			.defaultSuccessUrl("/")
			.and()
			.oauth2Login()
			.loginPage("/login")
			//구글 로그인이 완료된 뒤의 후처리가 필요. Tip. 엑세스 토큰+사용자 프로필 정보를 한방에 받음!
			.userInfoEndpoint()
			.userService(principalOauth2UserService);
			
	}
}
