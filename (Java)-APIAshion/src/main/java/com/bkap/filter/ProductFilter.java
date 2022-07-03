package com.bkap.filter;

import java.io.Serializable;
import java.util.List;

public class ProductFilter implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int productId;
	
	
	
	private String productName;

	private Boolean newProduct;

	private Boolean trendProduct;

	private Boolean featureProduct;

	private float priceMin;
	private float priceMax;

	private int discount;

	private int vote;

	private String brand;

	private Boolean status;
	
	private int categoryPrdId;
	
	private String slugCategory;
	
	private List<String> color;
	
	private List<String> size; 
	
	private String sort;
	
	private int page;
	
	private int pageSize;
	
	private boolean asc;

	public ProductFilter() {
		this.page = 0;
		this.pageSize = 10;
		this.sort = "productName";
		this.asc = true;
		this.priceMin = 0;
	}
	
	public String getSlugCategory() {
		return slugCategory;
	}

	public void setSlugCategory(String slugCategory) {
		this.slugCategory = slugCategory;
	}


	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Boolean getNewProduct() {
		return newProduct;
	}

	public void setNewProduct(Boolean newProduct) {
		this.newProduct = newProduct;
	}

	public Boolean getTrendProduct() {
		return trendProduct;
	}

	public void setTrendProduct(Boolean trendProduct) {
		this.trendProduct = trendProduct;
	}

	public Boolean getFeatureProduct() {
		return featureProduct;
	}

	public void setFeatureProduct(Boolean featureProduct) {
		this.featureProduct = featureProduct;
	}

	public float getPriceMin() {
		return priceMin;
	}

	public void setPriceMin(float priceMin) {
		this.priceMin = priceMin;
	}

	public float getPriceMax() {
		return priceMax;
	}

	public void setPriceMax(float priceMax) {
		this.priceMax = priceMax;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public int getVote() {
		return vote;
	}

	public void setVote(int vote) {
		this.vote = vote;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public int getCategoryPrdId() {
		return categoryPrdId;
	}

	public void setCategoryPrdId(int categoryPrdId) {
		this.categoryPrdId = categoryPrdId;
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

	public List<String> getColor() {
		return color;
	}

	public void setColor(List<String> color) {
		this.color = color;
	}

	public List<String> getSize() {
		return size;
	}

	public void setSize(List<String> size) {
		this.size = size;
	}

	

	

	
	
	


}
