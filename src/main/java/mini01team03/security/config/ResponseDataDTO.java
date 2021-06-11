package mini01team03.security.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseDataDTO {
	
	private String code;
	private String status;
	private String message;
	private Object item;
	
}

