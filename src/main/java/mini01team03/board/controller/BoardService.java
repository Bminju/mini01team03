package mini01team03.board.controller;

import java.sql.SQLException;
import java.util.List;

import mini01team03.board.model.BoardVO;

public interface BoardService {
	//글쓰기
	public int insertBoard(BoardVO boardVO) throws SQLException;
	//게시글 List
	public List<BoardVO> getBoardList() throws SQLException;
	public List<BoardVO> getBoardListPaging(BoardVO boardVO) throws SQLException;
	public BoardVO getBoard(Integer bnum) throws SQLException;
	//조회수 
	public int hitIncrease(Integer bnum) throws SQLException;
	//글 수정
	public int updateBoard(BoardVO boardVO) throws SQLException;
	
	public int deleteBoard(Integer bnum) throws SQLException;

}
