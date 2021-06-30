package mini01team03.board.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;

import mini01team03.board.model.BoardVO;


//content 유효성 검사 
@Component
public class BoardValidator implements Validator{

	   @Override
	    public boolean supports(Class<?> clazz) {
	        return BoardVO.class.equals(clazz);
	    }

	    @Override
	    public void validate(Object obj, Errors errors) {
	        BoardVO b = (BoardVO) obj;
	        if(StringUtils.isEmpty(b.getContent())){
	            errors.rejectValue("content","key","내용을 입력하세요.");
	        }
	    }
}
