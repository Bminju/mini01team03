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
	public List<ListVO> getAddress(String userid) throws SQLException;
}
