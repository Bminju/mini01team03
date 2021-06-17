package mini01team03.spot.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
			//getTitle을 \n 기준으로 잘라서 3부분을 만들기
			String[] array = listVO[i].getTitle().split("\n");
		    //getStart와 getEnd를 T기준으로 잘라서 날짜와 시간 추출
			String[] array2 = listVO[i].getStart().split("T");	
			String[] array3 = listVO[i].getEnd().split("T");	
			//array[0]에는 장소명, [1]에는 주소 [2]에는 카테고리 
			listVO[i].setStore(array[0]);  //각각 set으로 추가 해주기
			listVO[i].setAddress(array[1]);
			listVO[i].setCate(array[2]);
			//array2 시작 [0]에는 날짜 [1]에는 시간
			listVO[i].setStartDay(array2[0]);
			listVO[i].setStartTime(array2[1]);
			//array2 끝 [0]에는 날짜 [1]에는 시간
			listVO[i].setEndDay(array3[0]);
			listVO[i].setEndTime(array3[1]);
					
			spotService.insertBeforeList(listVO[i]);
		}
		Map before = new HashMap();
		before.put("msg", "success");
		
		return before ;

	}
	//db에서 저장된 주소 정보 가져오기. 혜지추가
	@ResponseBody
	@PostMapping("getAddress")
	public List<ListVO> getAddress(Model model, HttpServletRequest request, HttpSession session) throws SQLException {
		//세션에 저장된 userid값 가져오고 이 값을 기준으로 정보 가져오기 
		Object ob_userid=session.getAttribute("email");
		String userid = (String)ob_userid;
		System.out.println(userid);
		List<ListVO> spotList = spotService.getAddress(userid);
		model.addAttribute("spotList", spotList);
		System.out.println("spotList"+spotList);
		
		return spotList;
	}
	
}