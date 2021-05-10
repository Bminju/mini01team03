package mini01team03.cost.controller;

import java.sql.SQLException;
import java.util.List;

import mini01team03.cost.model.CostVO;

public interface CostService {

	public List<CostVO> getCostList() throws SQLException;
}
