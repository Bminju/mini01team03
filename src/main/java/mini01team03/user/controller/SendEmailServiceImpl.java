package mini01team03.user.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mini01team03.user.model.MailVO;
import mini01team03.user.model.UserDAO;
import mini01team03.user.model.UserVO;

@Service
@Transactional
public class SendEmailServiceImpl implements SendEmailService{
	
	@Autowired
	UserDAO userDAO;

	@Autowired
	private JavaMailSender mailSender;
	private static final String FROM_ADDRESS = "03.team.travle@gmail.com";

	@Override
	public MailVO createMailAndChangePassword(UserVO userVO) throws SQLException {
		 String str = getTempPassword();
	     MailVO dto = new MailVO();
	     dto.setAddress(userVO.getEmail());
	     dto.setTitle(userVO.getUsername()+"님의 임시비밀번호 안내 이메일 입니다.");
	     dto.setMessage("안녕하세요. 임시비밀번호 안내 관련 이메일 입니다." + "[" + userVO.getUsername() + "]" +"님의 임시 비밀번호는 "
	        + str + " 입니다.");
	     updatePassword(str, userVO.getEmail());
		return dto;
	}

	@Override
	public void updatePassword(String str, String email) throws SQLException {
		 String userpwd = str;
	     UserVO userVO  = userDAO.findUserByUserId(email);
	        if(userVO == null) {
	        	System.out.println("email:"+email +"없음");
	        		
	        }else {
	        	int id = userVO.getId();
	        	UserVO uptUserVO = new UserVO();
	        	uptUserVO.setId(id);
	        	uptUserVO.setUserpwd(userpwd);
	        	userDAO.updateUserpwd(uptUserVO);	
	        	System.out.println(uptUserVO.getId());
	        }
		
	}

	@Override
	public String getTempPassword() throws SQLException {
		char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        String str = "";

        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
		return str;
	}

	@Override
	
	public void mailSend(MailVO mailVO) throws SQLException {
		System.out.println("이멜 전송 완료!");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailVO.getAddress());
        message.setFrom(FROM_ADDRESS);
        message.setSubject(mailVO.getTitle());
        message.setText(mailVO.getMessage());

        mailSender.send(message);
    }
		
	}

