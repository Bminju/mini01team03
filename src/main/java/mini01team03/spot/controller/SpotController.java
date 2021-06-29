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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import mini01team03.cost.model.CostVO;
import mini01team03.cost.model.MarkerVO;
import mini01team03.spot.model.ListVO;
import mini01team03.spot.model.TotalVO;
import mini01team03.user.model.UserVO;

@Controller
@RequestMapping("")
public class SpotController {
	
	@Autowired
	SpotService spotService;
	
	@GetMapping("address")
	public String address(Model model, TotalVO totalVO, HttpServletRequest request, HttpSession session) throws Exception {
		//userid를 파라미터로 넣어서 
		Object ob_userid=session.getAttribute("email");
		String userid = (String)ob_userid;
		List<TotalVO> travel_list = spotService.travelList(userid);
		System.out.println("travel_list.size():"+travel_list.size());
		System.out.println(userid);
		model.addAttribute("travel_list", travel_list);
		return "cost/address";
	}
	//travel_title 리스트 받는 곳
		@GetMapping("address1")
		public String address1(Model model, TotalVO totalVO, HttpServletRequest request, HttpSession session) throws Exception {
				//userid를 파라미터로 넣어서 
		      Object ob_userid=session.getAttribute("email");
		      String userid = (String)ob_userid;
		      List<TotalVO> travel_list = spotService.travelList(userid);
		      System.out.println("travel_list.size():"+travel_list.size());
		      System.out.println(userid);
		      model.addAttribute("travel_list", travel_list);

			return "cost/address1";
		}
	
		//travel_title 받고 난 후에 캘린더 값 보여주기
		@PostMapping("address2/")
		public String address2(@RequestParam("travel") String travel,Model model) throws Exception {
			 model.addAttribute("travel", travel); //뷰에 보내기
			 System.out.println("travel:"+travel);
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
	//before페이지에서 일정 추가 /슬기추가
	@ResponseBody
	@PostMapping("beforelist")
	public Map beforelist(@RequestBody ListVO listVO[],HttpServletRequest request, HttpSession session) throws SQLException {
		//일정 중복 방지를 위한 delete
		UserVO userVO = new UserVO();
		userVO.setUserid((String)session.getAttribute("email"));
		
		listVO[0].setUserid(userVO);
		//db중복 입력을 방지하기 위한 delete
		spotService.beforeinfoDelete(listVO);
		
		
		//db insert문 시작
		for(int i = 0; i <listVO.length; i++) {
			
			//getTitle을 \n 기준으로 잘라서 3부분을 만들기
			System.out.println("listVO[i].getTitle():"+listVO[i].getTitle());
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
			
			//세션에서 userid 뽑은거 listVO 에 넣기
			Object my_info = session.getAttribute("email"); //세션에 저장 된 아이디 값을 얻기 위함
			String ma_info = (String)my_info; //cast연산자로 String 형태로 형 변환을 한다.
			//System.out.println(ma_info);  //세션에 저장된 아이디임
			
			UserVO userid = new UserVO(); //UserVO 타입의 userid객체 생성
			userid.setUserid(ma_info);//userid에 세션아이디 값 넣기
			listVO[i].setUserid(userid); // 세션 아이디 값이 들어있는 userid 를 listVO에 넣기
			System.out.println(listVO[i].getTravel());
			
			spotService.insertBeforeList(listVO[i]);
			
		}
		//cost_id를 update하는 구문 추가.......
		spotService.cost_id_Update();
		Map before = new HashMap();
		before.put("msg", "success");
		
		return before ;
	}
	
	//db에서 저장된 주소 정보 가져오기. 혜지추가
	@ResponseBody
	@PostMapping("/getAddress")
	public List<ListVO> getAddress(@RequestBody ListVO listVO, Model model, HttpServletRequest request, HttpSession session) throws SQLException {
		//세션에 저장된 userid값 가져오고 이 값을 기준으로 정보 가져오기 
		Object ob_userid=session.getAttribute("email");
		String userid1 = (String)ob_userid;
		//System.out.println(userid);
		UserVO userid = new UserVO(); //UserVO 타입의 userid객체 생성
		userid.setUserid(userid1);//userid에 세션아이디 값 넣기
		listVO.setUserid(userid); // 세션 아이디 값이 들어있는 userid 를 listVO에 넣기
		List<ListVO> spotList = spotService.getAddress(listVO);
		model.addAttribute("spotList", spotList);
		//System.out.println("spotList"+spotList);
		
		return spotList;
	}
	//price 입력받은 값 db에 입력하기. 혜지추가
	@ResponseBody
	@PostMapping("/setPrice")
	public int setPrice(@RequestBody ListVO listVO,HttpServletRequest request, HttpSession session) throws SQLException {
		//listVO에 price 값과 id 값이 저장되어 있음
		Object ob_userid=session.getAttribute("email");
	    String userid1 = (String)ob_userid;
	    //System.out.println(userid);
	    UserVO userid = new UserVO(); //UserVO 타입의 userid객체 생성
	    userid.setUserid(userid1);//userid에 세션아이디 값 넣기
	    listVO.setUserid(userid); // 세션 아이디 값이 들어있는 userid 를 listVO에 넣기
		int result = spotService.updatePrice(listVO);
		return result;
	}
	
	
	//여행제목, 총경비 db에 넘기기 .슬기추가
	@ResponseBody
	@PostMapping("cost")
	public int insertPrice(@RequestBody TotalVO totalVO ,HttpServletRequest request, HttpSession session) throws SQLException {
		//db에 중복 저장을 막기 위한 delete
		Object ob_userid=session.getAttribute("email");
		String userid1 = (String)ob_userid;
		UserVO userid = new UserVO(); //UserVO 타입의 userid객체 생성
		userid.setUserid(userid1);//userid에 세션아이디 값 넣기
		totalVO.setUserid(userid);
		//select한 후에
		//if문 사용해서 정보 있으면 update 없으면 insert로 바꾸기.........^^..
		
		spotService.costDelete(totalVO);
		
		//db에 여행제목, 총 경비 insert
		int cnt = spotService.insertTotalPrice(totalVO);
		return cnt;
	}
	
	//일정 선택 누를 시 cost 값 불러오는 로직
	@ResponseBody
	@PostMapping("findcost")
	public String findcost (@RequestBody TotalVO totalVO, HttpServletRequest request, HttpSession session) throws SQLException {
		Object ob_userid=session.getAttribute("email");
		String userid1 = (String)ob_userid;
		UserVO userid = new UserVO(); //UserVO 타입의 userid객체 생성
		userid.setUserid(userid1);//userid에 세션아이디 값 넣기
		totalVO.setUserid(userid);  		
		String result = spotService.findCost(totalVO); 
		return result; //result에 담긴 값은 조건에 맞는 total금액임
	  }
	
}