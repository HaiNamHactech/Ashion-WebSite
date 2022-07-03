package com.bkap.filter;

public class InstagramFilter {
	
	private int instaId;
	
	private int displayNo;
	
	private String sort;
	
	private int page;
	
	private int pageSize;
	
	private boolean asc;

	public InstagramFilter() {
		this.page = 0;
		this.pageSize = 3;
		this.sort = "instaId";
		this.asc = true;
	}

	public int getInstaId() {
		return instaId;
	}

	public void setInstaId(int instaId) {
		this.instaId = instaId;
	}

	public int getDisplayNo() {
		return displayNo;
	}

	public void setDisplayNo(int displayNo) {
		this.displayNo = displayNo;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public boolean isAsc() {
		return asc;
	}

	public void setAsc(boolean asc) {
		this.asc = asc;
	}
	
	

}
