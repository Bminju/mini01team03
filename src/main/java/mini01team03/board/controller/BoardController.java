package mini01team03.board.controller;


import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import mini01team03.board.model.BoardDAO;
import mini01team03.board.model.BoardVO;
import mini01team03.board.validator.BoardValidator;
import mini01team03.user.controller.UserService;
import mini01team03.user.model.UserVO;

@Controller
@RequestMapping("")
public class BoardController {
	 
	@Autowired
	BoardService boardService;
	@Autowired
	UserService userSerivce;
	@Autowired
	BoardValidator boardValidator;
	@Autowired
	BoardDAO boardDAO;
	
	//게시글 조회
	@GetMapping("user/content")
	//특정 게시글 조회에 필요한 bnum값을 파라미터로 전달 받음
	public String write(Model model, @RequestParam(value="bnum", required = false) Integer bnum)throws Exception{

		BoardVO board = boardService.getBoard(bnum);
			boardService.hitIncrease(bnum); //조회수 증가
			model.addAttribute("boardVO", board); //글조회
	
		return "board/content";
	}
	//글쓰기 - DB 저장 
	@PostMapping("/user/write")
	public String writePost(@Valid BoardVO boardVO, BindingResult bindingResult, Model model, @RequestParam(required = false) Integer bnum, HttpServletRequest request,HttpSession session) throws Exception {
		
		 Object writer = session.getAttribute("email"); //세션에서 userid 가져오기
		 String user = (String)writer; //object타입을 string으로 변환 
		 UserVO userid = new UserVO(); // boardVO에 userid가 UserVO를 가져오므로
		 userid.setUserid(user); //userid 객체에 string user를 담음
		 boardVO.setUserid(userid); //userid를 boardVO에 담음
		
		boardValidator.validate(boardVO, bindingResult); // 유효성 검사 
		if (bindingResult.hasErrors()) {
			return "board/write"; 
		 } if(bnum == null) {
				boardService.insertBoard(boardVO);
			}else {
				boardService.updateBoard(boardVO);
			}
		//로그인한 ID 작성자로 db 저장
		return "redirect:/community"; //redirect로 list조회가 새로 작동하면서 화면 이동.
}

	@GetMapping("user/write")
	public String write(BoardVO boardVO, Model model,  @RequestParam(required = false) Integer bnum) throws Exception {
		if(bnum == null) {
			model.addAttribute("boardVO", new BoardVO()); 
		}else {
			BoardVO board = boardService.getBoard(bnum);
			model.addAttribute("boardVO", board); //글조회
		}
		return "board/write";
	}
	
	//게시글 뿌려주기 - paging
	@GetMapping("community")
	public String BoardList( Model model,@ModelAttribute("boardVO") BoardVO boardVO) throws SQLException {
		List<BoardVO> boardList = boardService.getBoardListPaging(boardVO);
		System.out.println("리스트ID " + boardList.get(0).getUserid());
		model.addAttribute("board", boardList); //리스트 타임리프 
		return "board/community";
	}
	
	//게시글 수정
	@GetMapping("user/update")
	//특정 게시글 조회에 필요한 bnum값을 파라미터로 전달 받음, null이 넘어올 경우를 대비해 required = false 로 설정
	public String update(@RequestParam(required = false) Integer bnum, Model model)throws Exception{
		
		if(bnum != null) {
			System.out.println("게시글번호:" + bnum);
			BoardVO board = boardService.getBoard(bnum);
			model.addAttribute("boardVO", board); //글조회
			 
		}else {
			System.out.println("게시글번호222:" + bnum);
			model.addAttribute("boardVO", new BoardVO());
		}
		return "board/write";
	}
	
	//게시글 삭제 
	@GetMapping("user/delete")
	public String delete(@RequestParam(required = false) Integer bnum) throws Exception{
		System.out.println("삭제완" + bnum);
		boardService.deleteBoard(bnum);
		return "redirect:/community";
	}
	
	
 }


