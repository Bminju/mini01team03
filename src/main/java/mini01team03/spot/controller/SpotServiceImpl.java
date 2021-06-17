package mini01team03.spot.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mini01team03.cost.model.MarkerVO;
import mini01team03.spot.model.ListVO;
import mini01team03.spot.model.SpotDAO;
import mini01team03.spot.model.TotalVO;

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
	//before 페이지에서 저장한 정보 db에서 가져오기
	@Override
	public List<ListVO> getAddress(String userid) throws SQLException {
		List<ListVO> spotList = spotDAO.getAddress(userid);
		return spotList;
	}
	@Override
	public int insertTotalPrice(TotalVO totalVO) throws SQLException {
		int cnt = spotDAO.insertTotalPrice(totalVO);
		return cnt;
	}
	
}
