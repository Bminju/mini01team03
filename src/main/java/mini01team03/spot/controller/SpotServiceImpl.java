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
	public List<ListVO> getAddress(ListVO listVO) throws SQLException {
		List<ListVO> spotList = spotDAO.getAddress(listVO);
		return spotList;
	}
	@Override
	public int insertTotalPrice(TotalVO totalVO) throws SQLException {
		int cnt = spotDAO.insertTotalPrice(totalVO);
		return cnt;
	}
	@Override
	public int beforeinfoDelete(ListVO listVO[]) throws SQLException {
		int cnt = spotDAO.beforeinfoDelete(listVO[0]);
		return cnt;
	}
	@Override
	public int costDelete(TotalVO totalVO) throws SQLException {
		int cnt = spotDAO.costDelete(totalVO);
		return cnt;
	}
	@Override
	public int updatePrice(ListVO listVO) throws SQLException {
		int cnt = spotDAO.updatePrice(listVO);
		return cnt;
	}
	//before페이지에 travel list 뿌리기
	@Override
	public List<TotalVO> travelList(String userid) throws SQLException {
		List<TotalVO>travel_list = spotDAO.travelList(userid);
		return travel_list;
	}
	@Override
	public String findCost(TotalVO totalVO) throws SQLException {
		String result = spotDAO.findCost(totalVO);
		return result;
	}
	@Override
	public int cost_id_Update() throws SQLException {
		int cnt = spotDAO.cost_id_Update();
		return cnt;
	}
	//차트에 날자별 금액 뿌리기
	@Override
	public List<ListVO> getAddress1(ListVO listVO) throws SQLException {
		List<ListVO> spotList = spotDAO.getAddress1(listVO);
		return spotList;
	}
	@Override
	public List<ListVO> getAddress2(ListVO listVO) throws SQLException {
		List<ListVO> spotList = spotDAO.getAddress2(listVO);
		return spotList;
	}
}
