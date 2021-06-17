package mini01team03.spot.controller;

import java.sql.SQLException;
import java.util.List;

import mini01team03.cost.model.MarkerVO;
import mini01team03.spot.model.ListVO;
import mini01team03.spot.model.TotalVO;

public interface SpotService {

	public int insertSpot(MarkerVO markerVO) throws SQLException;
	//before 페이지 일정 정보 추가
	public int insertBeforeList(ListVO listVO) throws SQLException;
	//before 페이지에서 저장한 정보 db에서 가져오기
	public List<ListVO> getAddress(String userid) throws SQLException;
	//before 페이지의 여행 제목, 총 경비 db에 추가
	public int insertTotalPrice(TotalVO totalVO) throws SQLException;
}