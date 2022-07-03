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
@Table(name = "categoryProduct")
public class CategoryProduct {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "categoryId")
	private int categoryId;
	@Column(name = "categoryName")
	private String categoryName;
	@Column(name = "parentId")
	private int parentId;
	@Column(name = "slug")
	private String slug;
	@Column(name = "displayNo")
	private int displayNo;
	@Column(name = "discription")
	private String description;
	@Column(name = "showHome")
	private Boolean showHome;
	@Column(name = "urlImg")
	private String urlImg ;
	@Column(name = "breadcrumbLink")
	private String breadcrumbLink;
	
	@JsonIgnore
	@OneToMany(fetch=FetchType.EAGER,mappedBy="categoryProduct")
	private List<Product> products;
	
	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public CategoryProduct() {
		
	}

	public CategoryProduct(int categoryId) {
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


	
	
	
	
}
