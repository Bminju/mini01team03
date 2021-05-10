package mini01team03.cost.model;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface CostDAO {

	public List<CostVO> getCostList() throws SQLException;
}
