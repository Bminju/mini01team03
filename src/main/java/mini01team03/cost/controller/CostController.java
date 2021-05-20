package mini01team03.cost.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import mini01team03.cost.model.CostVO;

@Controller
@RequestMapping("cost")
public class CostController {

	@Autowired
	CostService costService;
	
	@GetMapping("list")
	public String list(Model model) throws Exception {
		
		List<CostVO> costList = costService.getCostList();
		
		model.addAttribute("costList", costList);
		
		return "cost/costList";
	}
	
}
