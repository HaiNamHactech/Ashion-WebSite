package com.bkap.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "productImage")
public class ProductImage {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int imgId;

	@Column(name = "urlImg")
	private String urlImgs;
	
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "productId", referencedColumnName="productId")
	private Product product;
	
	public int getImgId() {
		return imgId;
	}
	
	public void setImgId(int imgId) {
		this.imgId = imgId;
	}
	
	public String getUrlImg() {
		return urlImgs;
	}
	public void setUrlImg(String urlImg) {
		this.urlImgs = urlImg;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
	
	
	

}
