package com.bkap.entities;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name ="commentBlog")
@JsonIgnoreProperties(value= {"hibernateLazyInitializer","handler"})
public class CommentBlog {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int commentId;
	@Column(name = "createBy")
	private String createBy;
	@Column(name = "modifieBy")
	private String modifieBy;
	@Column(name = "content")
	private String content;
	@Column(name = "createDate")
	private Date createDate;
	@Column(name = "modifieDate")
	private Date modifieDate;
	@Column(name = "favourite")
	private int favourite;
	@Column(name = "feedbackCommentId")
	private int feedbackCommentId;
	@Column(name ="status")
	private Boolean status;
	
	@ManyToOne
	@JoinColumn(name="blogId")
	private Blog blog;
	
	@ManyToOne
	@JoinColumn(name ="userId")
	private Users userB;

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
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

	public int getFavourite() {
		return favourite;
	}

	public void setFavourite(int favourite) {
		this.favourite = favourite;
	}
	
	

	public int getFeedbackCommentId() {
		return feedbackCommentId;
	}

	public void setFeedbackCommentId(int feedbackCommentId) {
		this.feedbackCommentId = feedbackCommentId;
	}

	public Blog getBlog() {
		return blog;
	}

	public void setBlog(Blog blog) {
		this.blog = blog;
	}


	public Users getUserB() {
		return userB;
	}

	public void setUserB(Users userB) {
		this.userB = userB;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	

	
	
	

}
