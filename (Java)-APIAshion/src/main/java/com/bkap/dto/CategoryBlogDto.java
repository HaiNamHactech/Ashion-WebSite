package com.bkap.dto;

public class CategoryBlogDto {
	
	private int categoryId;
	
	private String categoryName;
	
	private String slug;
	
	private int parentId;
	
	private int totalItem;
	
	public CategoryBlogDto() {
		
	}
	
	

	public CategoryBlogDto(int categoryId, String categoryName, String slug, int parentId, int totalItem) {
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.slug = slug;
		this.parentId = parentId;
		this.totalItem = totalItem;
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

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getTotalItem() {
		return totalItem;
	}

	public void setTotalItem(int totalItem) {
		this.totalItem = totalItem;
	}
	
	
	

}
