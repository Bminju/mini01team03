package mini01team03.board.paging;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

//컨트롤러에서 일일이 파라미터 전달(@RequestParam)로 처리하는 것은 비효율적이므로 
//공통으로 사용할 새로운 클래스를 만들어서 처리

public class Paging {
	private int currentPnum; //현재페이지번호 
	private int dataSize;  //출력할 게시글 개수 
	private int pagingSize; //하단 보여질 페이지 크기 
	private String findKeyword; // 키워드 검색
	private String findType; // 제목,내용,작성자등 검색 타입
	
	public Paging() {
		this.currentPnum = 1; //기본값 현재페이지 1
		this.dataSize = 7; // 한페이지에 보여질 게시글 수 
		this.pagingSize = 5;  //기본 출력할 페이지수는 5
	}

//	public int getStartPage() {
//		return (currentPnum - 1) * dataSize;
//	}
	
	//특정 페이지를 클릭했을 때 올바른 페이지로 이동하도록 처리
	public String makeQueryString(int pageNo) {

		UriComponents uriComponents = UriComponentsBuilder.newInstance()
				.queryParam("currentPnum", pageNo)
				.queryParam("dataSize", dataSize)
				.queryParam("pagingSize", pagingSize)
				.queryParam("findType", findType)
				.queryParam("findKeyword", findKeyword)
				.build()
				.encode();

		return uriComponents.toUriString();
	}


	public int getCurrentPnum() {
		return currentPnum;
	}

	public void setCurrentPnum(int currentPnum) {
		this.currentPnum = currentPnum;
	}

	public int getDataSize() {
		return dataSize;
	}

	public void setDataSize(int dataSize) {
		this.dataSize = dataSize;
	}

	public int getPagingSize() {
		return pagingSize;
	}

	public void setPagingSize(int pagingSize) {
		this.pagingSize = pagingSize;
	}

	public String getFindKeyword() {
		return findKeyword;
	}

	public void setFindKeyword(String findKeyword) {
		this.findKeyword = findKeyword;
	}

	public String getFindType() {
		return findType;
	}

	public void setFindType(String findType) {
		this.findType = findType;
	}
	
	
}
