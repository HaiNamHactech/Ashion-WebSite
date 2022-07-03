package com.bkap.filter;

import java.io.Serializable;

public class CategoryProductFilter implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int categoryId;
	
	private String categoryName;
	
	private int parentId;
	
	private String slug;
	
	private int displayNo;
	
	private String description;
	
	private Boolean showHome;
	
	private String breadcrumbLink;
	
	private int totalItemPrd;
	
	private String sort;
	
	private int page;
	
	private int pageSize;
	
	private boolean asc;

	public CategoryProductFilter() {
		this.page = 0;
		this.pageSize = 3;
		this.sort = "categoryName";
		this.asc = true;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public int getDisplayNo() {
		return displayNo;
	}

	public void setDisplayNo(int displayNo) {
		this.displayNo = displayNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getShowHome() {
		return showHome;
	}

	public void setShowHome(Boolean showHome) {
		this.showHome = showHome;
	}

	public String getBreadcrumbLink() {
		return breadcrumbLink;
	}

	public void setBreadcrumbLink(String breadcrumbLink) {
		this.breadcrumbLink = breadcrumbLink;
	}

	public int getTotalItemPrd() {
		return totalItemPrd;
	}

	public void setTotalItemPrd(int totalItemPrd) {
		this.totalItemPrd = totalItemPrd;
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
