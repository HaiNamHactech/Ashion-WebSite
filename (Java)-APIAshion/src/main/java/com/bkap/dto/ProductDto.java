package com.bkap.dto;



import java.util.Date;
import java.util.List;

public class ProductDto {
	
	private int productId;
	
	private String productName;

	private Boolean newProduct;

	private Boolean trendProduct;

	private Boolean featureProduct;

	private float price;

	private int discount;

	private String thumbnail;
	
	private List<String> urlImgs;
	
	private int urlImgId;

	private int countBy;

	private int vote;

	private String brand;

	private Boolean status;
	
	private int categoryPrdId;
	
	private String descriptionShort;
	
	private String descriptionDetail;
	
	private Date createDate;

	private Date modifieDate;

	private String createBy;

	private String modifieBy;
	
	private List<ColorSizeProductDto> listColorSizeDto;
	
	private String categoryName;
	
	public int getProductId() {
		return productId;
	}

	public ProductDto() {
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

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
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

	public int getCountBy() {
		return countBy;
	}

	public void setCountBy(int countBy) {
		this.countBy = countBy;
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

	public List<ColorSizeProductDto> getListColorSizeDto() {
		return listColorSizeDto;
	}

	public void setListColorSizeDto(List<ColorSizeProductDto> listColorSizeDto) {
		this.listColorSizeDto = listColorSizeDto;
	}

	public int getCategoryPrdId() {
		return categoryPrdId;
	}

	public void setCategoryPrdId(int categoryPrdId) {
		this.categoryPrdId = categoryPrdId;
	}

	public String getDescriptionShort() {
		return descriptionShort;
	}

	public void setDescriptionShort(String descriptionShort) {
		this.descriptionShort = descriptionShort;
	}

	public String getDescriptionDetail() {
		return descriptionDetail;
	}

	public void setDescriptionDetail(String descriptionDetail) {
		this.descriptionDetail = descriptionDetail;
	}

	public List<String> getUrlImgs() {
		return urlImgs;
	}

	public void setUrlImgs(List<String> urlImgs) {
		this.urlImgs = urlImgs;
	}

	public int getUrlImgId() {
		return urlImgId;
	}

	public void setUrlImgId(int urlImgId) {
		this.urlImgId = urlImgId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}
