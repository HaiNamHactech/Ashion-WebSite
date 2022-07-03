package com.bkap.filter;

public class SliderFilter {
	
	private int sliderId;
	private String title;
	private String content;
	private int displayNo;
	
	private String sort;
	
	private int page;
	
	private int pageSize;
	
	private boolean asc;

	public SliderFilter() {
		this.page = 0;
		this.pageSize = 3;
		this.sort = "title";
		this.asc = true;
	}

	public int getSliderId() {
		return sliderId;
	}

	public void setSliderId(int sliderId) {
		this.sliderId = sliderId;
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
