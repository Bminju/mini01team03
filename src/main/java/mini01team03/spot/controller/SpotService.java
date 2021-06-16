package mini01team03.spot.controller;

import java.sql.SQLException;

import mini01team03.cost.model.MarkerVO;
import mini01team03.spot.model.ListVO;

public interface SpotService {

	public int insertSpot(MarkerVO markerVO) throws SQLException;
	//before 페이지 일정 정보 추가
	public int insertBeforeList(ListVO listVO) throws SQLException;
}