package com.bkap.dto;

import java.sql.Date;

public class CommentDto {
	
	private int commentId;
	
	private int userId;
	
	private int  blogId;
	
	private String content;
	
	private String createBy;
	
	private String modifieBy;
	
	private Date createDate;
	
	private Date modifieDate;
	
	private int feedbackCommentId;
	
	private int favourite;
	
	private String avatar ;
	
	private Boolean status;
	
		
	public CommentDto() {
	}
	
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getBlogId() {
		return blogId;
	}
	public void setBlogId(int blogId) {
		this.blogId = blogId;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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

	public int getFeedbackCommentId() {
		return feedbackCommentId;
	}

	public void setFeedbackCommentId(int feedbackCommentId) {
		this.feedbackCommentId = feedbackCommentId;
	}

	public int getFavourite() {
		return favourite;
	}

	public void setFavourite(int favourite) {
		this.favourite = favourite;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	
	
	
	
	
	
	
	
	

}
