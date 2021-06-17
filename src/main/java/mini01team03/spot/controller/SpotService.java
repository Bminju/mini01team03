package mini01team03.spot.controller;

import java.sql.SQLException;
import java.util.List;

import mini01team03.cost.model.MarkerVO;
import mini01team03.spot.model.ListVO;

public interface SpotService {

	public int insertSpot(MarkerVO markerVO) throws SQLException;
	//before 페이지 일정 정보 추가
	public int insertBeforeList(ListVO listVO) throws SQLException;
	//before 페이지에서 저장한 정보 db에서 가져오기
	public List<ListVO> getAddress() throws SQLException;
}