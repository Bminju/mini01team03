package mini01team03.spot.model;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import mini01team03.cost.model.MarkerVO;

@Repository
@Mapper
public interface SpotDAO {

	public int insertSpot(MarkerVO markerVO) throws SQLException;
	//before 일정 정보 추가 
	public int insertBeforeList(ListVO listVO) throws SQLException;
	//before 페이지에서 저장한 정보 db에서 가져오기
	public List<ListVO> getAddress(ListVO listVO) throws SQLException;
	//before 페이지의 여행 제목, 총 경비 db에 추가
	public int insertTotalPrice(TotalVO totalVO) throws SQLException;
	//before db중복 입력을 방지하기 위한 delete
	public int beforeinfoDelete(ListVO listVO) throws SQLException;
	//before title과 cost 중복 입력 방지를 위한 delete
	public int costDelete(TotalVO totalVO) throws SQLException;
	//price 입력받은 값 db에 입력하기
	public int updatePrice(ListVO listVO) throws SQLException;
	//before페이지에 travel list 뿌리기
	public List<TotalVO> travelList(String userid) throws SQLException;
	//before페이지에서 total_cost값 가져오기
	public String findCost(TotalVO totalVO)throws SQLException;	
	//beforeinfo에 cost_id update하기
	public int cost_id_Update() throws SQLException;	
}	

