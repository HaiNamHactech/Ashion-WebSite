package com.bkap.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "categoryBlog")
public class CategoryBlog {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "categoryId")
	private int categoryId;
	@Column(name = "categoryName")
	private String categoryName;
	@Column(name ="slug")
	private String slug;
	@Column(name ="parentId")
	private int parentId;
	
	@JsonIgnore
	@OneToMany(mappedBy="categoryBlog",fetch=FetchType.EAGER)
	public List<Blog> listBlog;
	
	public CategoryBlog() {
	}

	public CategoryBlog(int categoryId) {
		this.categoryId = categoryId;
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

	public List<Blog> getListBlog() {
		return listBlog;
	}

	public void setListBlog(List<Blog> listBlog) {
		this.listBlog = listBlog;
	}
	
	
	
	
	

}
