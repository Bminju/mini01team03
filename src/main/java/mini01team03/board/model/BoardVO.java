package mini01team03.board.model;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import mini01team03.user.model.UserVO;

public class BoardVO extends PagingVO{
	
	private Integer bnum;
	@NotNull  //스프링부트에 내장된 유효성 검사 annotation
	@Size(min = 2, max = 30)
	private String title;
	
	private String content;
	private Integer hit;
	private LocalDateTime regdt;
	private UserVO userid;
	
	public UserVO getUserid() {
		return userid;
	}
	public void setUserid(UserVO userid) {
		this.userid = userid;
	}
	public Integer getBnum() {
		return bnum;
	}
	public void setBnum(Integer bnum) {
		this.bnum = bnum;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getHit() {
		return hit;
	}
	public void setHit(Integer hit) {
		this.hit = hit;
	}
	public LocalDateTime getRegdt() {
		return regdt;
	}
	public void setRegdt(LocalDateTime regdt) {
		this.regdt = regdt;
	}

	
	
}
