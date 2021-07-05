package mini01team03.board.model;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;



@Repository
@Mapper
public interface BoardDAO {
	//글작성
	public int insertBoard(BoardVO boardVO) throws SQLException;
	
	//게시글 List
	//public List<BoardVO> getBoardList() throws SQLException;
	public BoardVO getBoard(Integer bnum) throws SQLException;
	//페이징
	public List<BoardVO> getBoardListPaging(BoardVO boardVO) throws SQLException;
	public int selectBoardTotal(BoardVO boardVO) throws SQLException;
	//조회수
	public int hitIncrease(Integer bnum) throws SQLException;

	//글 수정
	public int updateBoard(BoardVO boardVO) throws SQLException;
	
	//글 삭제 
	public int deleteBoard(Integer bnum) throws SQLException;

}
