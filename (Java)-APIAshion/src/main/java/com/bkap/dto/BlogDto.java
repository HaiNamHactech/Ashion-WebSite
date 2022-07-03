package com.bkap.dto;

import java.util.Date;

public class BlogDto {
	
	private int blogId;
	
	private int categoryId;
	
	private String titleBlog;
	
	private String content;
	
	private String urlImg;
	
	private String categoryName;
	
	private int totalComment;
	
	private String createBy;
	
	private String modifieBy;
	
	private Date createDate;
	
	private Date modifieDate;
	
	public BlogDto() {
	}

	public BlogDto(int blogId, int categoryId, String titleBlog, String createBy, String modifieBy, Date createDate,
			Date modifieDate, String content, String urlImg, String categoryName) {
		this.blogId = blogId;
		this.categoryId = categoryId;
		this.titleBlog = titleBlog;
		this.createBy = createBy;
		this.modifieBy = modifieBy;
		this.createDate = createDate;
		this.modifieDate = modifieDate;
		this.content = content;
		this.urlImg = urlImg;
		this.categoryName = categoryName;
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

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getModifieBy() {
		return modifieBy;
	}

	public void setModifieBy(String modifieBy) {
		this.modifieBy = modifieBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifieDate() {
		return modifieDate;
	}

	public void setModifieDate(Date modifieDate) {
		this.modifieDate = modifieDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrlImg() {
		return urlImg;
	}

	public void setUrlImg(String urlImg) {
		this.urlImg = urlImg;
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

	
	
	
	
	
	

}
