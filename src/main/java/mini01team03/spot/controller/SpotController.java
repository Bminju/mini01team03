package mini01team03.spot.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import mini01team03.cost.model.MarkerVO;

@Controller
@RequestMapping("")
public class SpotController {
	
	@Autowired
	SpotService spotService;
	
	@GetMapping("address")
	public String address() throws Exception {
		return "cost/address";
	}
	
	@ResponseBody
	@PostMapping("cost/save")
	public Map costSave(@RequestBody MarkerVO markerVO[]) {
		for(int i = 0; i < markerVO.length; i++) {
			System.out.println(markerVO[i].getTitle());
			System.out.println(markerVO[i].getAddress());
			System.out.println(markerVO[i].getLa());
			System.out.println(markerVO[i].getMa());
		}
		
		Map map = new HashMap();
		map.put("msg", "success");
		return map;
	
}

}