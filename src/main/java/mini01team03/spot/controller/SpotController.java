package mini01team03.spot.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import mini01team03.cost.model.CostVO;
import mini01team03.cost.model.MarkerVO;
import mini01team03.spot.model.ListVO;

@Controller
@RequestMapping("")
public class SpotController {
	
	@Autowired
	SpotService spotService;
	
	@GetMapping("address")
	public String address() throws Exception {
		return "cost/address";
	}
	
	@GetMapping("address2")
	public String address2() throws Exception {
		return "cost/address2";
	}
	
	@ResponseBody
	@PostMapping("cost/save")
	public Map costSave(@RequestBody MarkerVO markerVO[]) throws SQLException {
		for(int i = 0; i < markerVO.length; i++) {
			
			System.out.println(markerVO[i].getTitle());
			System.out.println(markerVO[i].getAddress());
			System.out.println(markerVO[i].getLa());
			System.out.println(markerVO[i].getMa());
			System.out.println(markerVO[i].getPrice());			
			spotService.insertSpot(markerVO[i]);
		}
		Map map = new HashMap();
		map.put("msg", "success");
		return map; 
	}
	@ResponseBody
	@PostMapping("beforelist")
	public Map beforelist(@RequestBody ListVO listVO[]) throws SQLException {
		for(int i = 0; i <listVO.length; i++) {
			//getTitle을 \n 기준으로 잘라서 3부분을 만들어야해
			//새로운 변수에 넣어서 list vo에 set 하기
			System.out.println(listVO[i].getTitle());
			System.out.println(listVO[i].getStart());
			System.out.println(listVO[i].getEnd());
			spotService.insertBeforeList(listVO[i]);
		}
		Map before = new HashMap();
		before.put("msg", "success");
		
		return before ;

	}
	
}