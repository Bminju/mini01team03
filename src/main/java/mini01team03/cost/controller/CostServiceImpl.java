package mini01team03.cost.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mini01team03.cost.model.CostDAO;
import mini01team03.cost.model.CostVO;

@Service
public class CostServiceImpl implements CostService {

	@Autowired 
	CostDAO costDAO;
	
	@Override
	public List<CostVO> getCostList() throws SQLException {
		List<CostVO> costList = costDAO.getCostList();
		return costList;
	}

}
