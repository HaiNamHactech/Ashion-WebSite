package com.bkap.filter;

public class BlogFilter {
	
	private int blogId;
	
	private int categoryId;
	
	private String titleBlog;
	
	private String content;
	
	private String categoryName;
	
	private int totalComment;
	
	private String sort;
	
	private int page;
	
	private int pageSize;
	
	private boolean asc;
	
	public BlogFilter() {
		this.page = 0;
		this.pageSize = 3;
		this.sort = "titleBlog";
		this.asc = true;
	}

	public int getBlogId() {
		return blogId;
	}

	public void setBlogId(int blogId) {
		this.blogId = blogId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getTitleBlog() {
		return titleBlog;
	}

	public void setTitleBlog(String titleBlog) {
		this.titleBlog = titleBlog;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getTotalComment() {
		return totalComment;
	}

	public void setTotalComment(int totalComment) {
		this.totalComment = totalComment;
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
