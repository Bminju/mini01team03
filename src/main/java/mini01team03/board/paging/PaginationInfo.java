package mini01team03.board.paging;

public class PaginationInfo {
	private Paging paging;  //페이징 계산에 필요한 파라미터들이 담김 클래스 
	private int totalRecord;  // 전체 데이터 수 
	private int totalPage; // 전체 페이지 수 
	private int firstPage;  // 페이지 리스트의 첫 페이지 번호 
	private int lastPage; //페이지 리스트의 마지막 페이지 번호 
	private int firstRecordIndex; //mapper 조건절에 사용 
	private boolean PreviousPage; //이전 페이지 존재여부
	private boolean NextPage;
	
	public PaginationInfo(Paging paging) {
		if(paging.getCurrentPnum() < 1) {
			paging.setCurrentPnum(1);
		}
		if(paging.getDataSize() < 1 || paging.getDataSize() > 100) {
			paging.setDataSize(10);
		}
		if(paging.getPagingSize() < 5 || paging.getPagingSize() > 20) {
			paging.setPagingSize(10);
		}
		this.paging = paging;
	}
	
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
		
		if(totalRecord > 0) {
			calculation();
		}
	}
	private void calculation() {
		//전체 페이지 수 (현재 페이지 번호가 전체 페이지 수보다 크면 현재 페이지 번호에 전체 페이지 수를 저장)
		totalPage = ((totalRecord - 1) / paging.getDataSize()) + 1; 
				if (paging.getCurrentPnum() > totalPage) {
					paging.setCurrentPnum(totalPage);
				}
				//페이지 리스트의 첫 페이지 번호 
				firstPage = ((paging.getCurrentPnum() - 1) / paging.getPagingSize()) * paging.getPagingSize() + 1;
				//페이지 리스트의 마지막 페이지 번호
				lastPage = firstPage + paging.getPagingSize() - 1;
				if (lastPage > totalPage) {
					lastPage = totalPage;
				}
				
				//mapper limit에 사용
				firstRecordIndex = (paging.getCurrentPnum() - 1) * paging.getDataSize();
				
				
				//이전,다음 페이지 존재 여부 
				PreviousPage = firstPage != 1;
				NextPage = (lastPage * paging.getDataSize()) < totalRecord;
	}

	public Paging getPaging() {
		return paging;
	}

	public void setPaging(Paging paging) {
		this.paging = paging;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}

	public int getLastPage() {
		return lastPage;
	}

	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}

	public int getFirstRecordIndex() {
		return firstRecordIndex;
	}

	public void setFirstRecordIndex(int firstRecordIndex) {
		this.firstRecordIndex = firstRecordIndex;
	}

	public boolean isPreviousPage() {
		return PreviousPage;
	}

	public void setPreviousPage(boolean PreviousPage) {
		this.PreviousPage = PreviousPage;
	}

	public boolean isNextPage() {
		return NextPage;
	}

	public void setNextPage(boolean NextPage) {
		this.NextPage = NextPage;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

}