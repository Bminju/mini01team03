package mini01team03.spot.controller;

import java.sql.SQLException;

import mini01team03.cost.model.MarkerVO;

public interface SpotService {

	public int insertSpot(MarkerVO markerVO) throws SQLException;
	
}