package mini01team03.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import mini01team03.user.model.GuserVO;
import mini01team03.user.model.UserDAO;
import mini01team03.user.model.UserVO;

//시큐리티 설정에서 loginProcessingUrl("/login");
// /login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어 있는 loadUserByUsername 함수가 실행
@Service
public class PrincipalDetailsService implements UserDetailsService{

   @Autowired
   private UserDAO userDAO;
   
	
   @Override
   public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
	   System.out.println("PrincipalDetailsService - userid:"+userid); //값이 넘어오질 않아 !!!!!!!!!!!!!!!!!!!!
	   
      GuserVO guserVO = userDAO.findByUserid(userid); //여기서 username은 형식(이름)뿐일 뿐 실질적인 값은 userid가 들어간 것임.

      if(guserVO == null) {
         System.out.println("null");
    	  return null;
      }else {
    	  System.out.println("origin_userid:"+guserVO.getUserid());
    	  System.out.println("origin_userpwd:"+guserVO.getUserpwd());
    	  
         return new PrincipalDetails(guserVO);
      }
      
   }

}