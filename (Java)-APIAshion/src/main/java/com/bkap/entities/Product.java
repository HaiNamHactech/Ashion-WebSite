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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "product")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value= {"hibernateLazyInitializer","handler"})
public class Product {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name= "productId")
	private int productId;
	@Column(name="productName")
	private String productName;
	@Column(name="newProduct")
	private Boolean newProduct;
	@Column(name="trendProduct")
	private Boolean trendProduct;
	@Column(name="featureProduct")
	private Boolean featureProduct;
	@Column(name="price")
	private float price;
	@Column(name="discount")
	private int discount;
	@Column(name="thumbnail")
	private String thumbnail;
	@Column(name="countBy")
	private int countBy;
	@Column(name="vote")
	private int vote;
	@Column(name="brand")
	private String brand;
	@Column(name ="descriptionShort")
	private String descriptionShort;
	@Column(name = "descriptionDetail")
	private String descriptionDetail;
	@Column(name="status")
	private Boolean status;
	@Column(name="createDate")
	@CreatedDate
	private Date createDate;
	@Column(name="modifieDate")
	@LastModifiedDate
	private Date modifieDate;
	@Column(name="createBy")
	@CreatedBy
	private String createBy;
	@Column(name="modifieBy")
	@LastModifiedBy
	private String modifieBy;
	
	@ManyToOne
	@JoinColumn(name = "categoryId" , referencedColumnName="categoryId")
	private CategoryProduct categoryProduct;
	
	@JsonIgnore
	@OneToMany(mappedBy="product",fetch=FetchType.LAZY)
	private List<ProductImage> productImages;
	
	@JsonIgnore
	@OneToMany(mappedBy="productCSP", fetch= FetchType.LAZY)
	private List<ColorSizeProduct> colorSizeProducts;
	
	@JsonIgnore
	@OneToMany(mappedBy="product", fetch= FetchType.LAZY)
	private List<OrderDetail> orderDetails;

	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public Product() {
		
	}
	
	public List<ColorSizeProduct> getColorSizeProducts() {
		return colorSizeProducts;
	}
	
	public void setColorSizeProducts(List<ColorSizeProduct> colorSizeProducts) {
		this.colorSizeProducts = colorSizeProducts;
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

	public CategoryProduct getCategoryProduct() {
		return categoryProduct;
	}

	public void setCategoryProduct(CategoryProduct categoryProduct) {
		this.categoryProduct = categoryProduct;
	}

	public List<ProductImage> getProductImages() {
		return productImages;
	}

	public void setProductImages(List<ProductImage> productImages) {
		this.productImages = productImages;
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
	
	

}
