package mini01team03.user.controller;

import java.sql.SQLException;

import mini01team03.user.model.MailVO;
import mini01team03.user.model.UserVO;

public interface SendEmailService {
	
public MailVO createMailAndChangePassword(UserVO userVO) throws SQLException;
	
public void updatePassword(String str, String email) throws SQLException;
	
public String getTempPassword() throws SQLException;
	
public void mailSend(MailVO mailVO) throws SQLException;

public MailVO AuthKeySend(String email) throws SQLException;


public String getAuthKey() throws SQLException;

}
