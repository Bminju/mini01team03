package mini01team03.spot.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mini01team03.cost.model.MarkerVO;
import mini01team03.spot.model.ListVO;
import mini01team03.spot.model.SpotDAO;

@Service
public class SpotServiceImpl implements SpotService {

	@Autowired
	SpotDAO spotDAO;

	@Override
	public int insertSpot(MarkerVO markerVO) throws SQLException {
		int cnt = spotDAO.insertSpot(markerVO);   //다시 보기
		return cnt;
	}
	//before 일정 정보 추가
	@Override
	public int insertBeforeList(ListVO listVO) throws SQLException {
		int cnt = spotDAO.insertBeforeList(listVO);  
		return cnt;
	}
	
}
