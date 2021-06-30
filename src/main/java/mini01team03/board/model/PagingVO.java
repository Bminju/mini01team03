package mini01team03.board.model;

import mini01team03.board.paging.Paging;
import mini01team03.board.paging.PaginationInfo;

public class PagingVO extends Paging{
	
	private PaginationInfo paginationInfo;

	public PaginationInfo getPaginationInfo() {
		return paginationInfo;
	}

	public void setPaginationInfo(PaginationInfo paginationInfo) {
		this.paginationInfo = paginationInfo;
	}
	
	
}
