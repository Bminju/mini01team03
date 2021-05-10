package mini01team03;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	@GetMapping({"","index"})
	public String index() throws Exception {
		return "index";
	}
}
