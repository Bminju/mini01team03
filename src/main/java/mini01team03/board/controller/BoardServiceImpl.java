package mini01team03.board.controller;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mini01team03.board.model.BoardDAO;
import mini01team03.board.model.BoardVO;
import mini01team03.board.paging.PaginationInfo;

@Service
@Transactional
public class BoardServiceImpl implements BoardService{

	@Autowired
	BoardDAO boardDAO;
	
	//게시글 작성
	@Override
	public int insertBoard(BoardVO boardVO) throws SQLException {
		int cnt = boardDAO.insertBoard(boardVO);
		return cnt;
	}

	//게시글 List
	@Override
	public List<BoardVO> getBoardList() throws SQLException {
		List<BoardVO> boardList = boardDAO.getBoardList();
		return boardList;
	}

	// 검색처리할 때 사용
	@Override
	public List<BoardVO> getBoardListPaging(BoardVO boardVO) throws SQLException {
		List<BoardVO> boardList = Collections.emptyList();
		
		int boardTotal = boardDAO.selectBoardTotal(boardVO);
		
		PaginationInfo paginationInfo = new PaginationInfo(boardVO);
		paginationInfo.setTotalRecord(boardTotal);
		
		boardVO.setPaginationInfo(paginationInfo);
		
		if (boardTotal > 0) {  //검색 결과가 있을 경우
			boardList = boardDAO.getBoardListPaging(boardVO);
		}
		
		return boardList; 
	}
	
	@Override
	public BoardVO getBoard(Integer bnum) throws SQLException {
		BoardVO boardVO = boardDAO.getBoard(bnum);
		return boardVO;
	}

	@Override
	public int hitIncrease(Integer bnum) throws SQLException {
		return boardDAO.hitIncrease(bnum);
	}

	@Override
	public int updateBoard(BoardVO boardVO) throws SQLException {
		int cnt = boardDAO.updateBoard(boardVO);
		return cnt;
	}

	@Override
	public int deleteBoard(Integer bnum) throws SQLException {
		int cnt = boardDAO.deleteBoard(bnum);
		return cnt;
	}

}
