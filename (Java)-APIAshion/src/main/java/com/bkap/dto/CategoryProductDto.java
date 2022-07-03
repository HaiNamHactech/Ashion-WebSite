package com.bkap.dto;

public class CategoryProductDto {

	private int categoryId;
	
	private String categoryName;
	
	private int parentId;
	
	private String slug;
	
	private int displayNo;
	
	private String description;
	
	private Boolean showHome;
	
	private String urlImg;
	
	private String breadcrumbLink;
	
	private int totalItemPrd;

	public CategoryProductDto() {
	}

	public CategoryProductDto(int categoryId, String categoryName, int parentId, String slug, int displayNo,
			String description, Boolean showHome, String urlImg, String breadcrumbLink, int totalItemPrd) {
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.parentId = parentId;
		this.slug = slug;
		this.displayNo = displayNo;
		this.description = description;
		this.showHome = showHome;
		this.urlImg = urlImg;
		this.breadcrumbLink = breadcrumbLink;
		this.totalItemPrd = totalItemPrd;
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

	public String getUrlImg() {
		return urlImg;
	}

	public void setUrlImg(String urlImg) {
		this.urlImg = urlImg;
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
	
	
	
}
