package mini01team03.spot.model;

import mini01team03.user.model.UserVO;

//슬기 추가
public class TotalVO {
	
	private String travel_title;
	private int total;
	private UserVO Userid;
	
	public String getTravel_title() {
		return travel_title;
	}
	public void setTravel_title(String travel_title) {
		this.travel_title = travel_title;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public UserVO getUserid() {
		return Userid;
	}
	public void setUserid(UserVO userid) {
		this.Userid = userid;
	}
	
	
}
