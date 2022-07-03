package com.bkap.entities;


import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "blog")
@EntityListeners(AuditingEntityListener.class)
public class Blog {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name ="blogId")
	private int blogId;
	
	@Column(name = "titleBlog")
	private String titleBlog;
	
	@Column(name = "createBy")
	@CreatedBy
	private String createBy;
	
	@Column(name = "modifieBy")
	@LastModifiedBy
	private String modifieBy;
	
	@Column(name = "createDate")
	@CreatedDate
	private Date createDate;
	
	@Column(name ="modifieDate")
	@LastModifiedDate
	private Date modifieDate;
	
	@Column(name = "content")
	private String content;
	    
	@Column(name = "urlImg")
	private String urlImg;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "categoryId", referencedColumnName="categoryId")
	private CategoryBlog categoryBlog;
	
	@JsonIgnore
	@OneToMany(mappedBy="blog",fetch=FetchType.EAGER)
	private List<CommentBlog> commentBlogs;
	

	public Blog() {
	}

	public Blog(int blogId, String titleBlog, String createBy, String modifieBy, Date createDate, String content,
			String urlImg) {
		this.blogId = blogId;
		this.titleBlog = titleBlog;
		this.createBy = createBy;
		this.modifieBy = modifieBy;
		this.createDate = createDate;
		this.content = content;
		this.urlImg = urlImg;
	}

	public int getBlogId() {
		return blogId;
	}

	public void setBlogId(int blogId) {
		this.blogId = blogId;
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

	public Date getModifieDate() {
		return modifieDate;
	}

	public void setModifieDate(Date modifieDate) {
		this.modifieDate = modifieDate;
	}

	public CategoryBlog getCategoryBlog() {
		return categoryBlog;
	}

	public void setCategoryBlog(CategoryBlog categoryBlog) {
		this.categoryBlog = categoryBlog;
	}

	public List<CommentBlog> getCommentBlogs() {
		return commentBlogs;
	}

	public void setCommentBlogs(List<CommentBlog> commentBlogs) {
		this.commentBlogs = commentBlogs;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
