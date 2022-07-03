package com.bkap.dto;

import java.util.List;

public class ProductImageDto {
	

	private int imgId;

	private  List<String> urlImgs;
	
	private int productId;
	

	public ProductImageDto() {
	}
	
	

	public ProductImageDto(List<String> urlImgs, int productId) {
		this.urlImgs = urlImgs;
		this.productId = productId;
	}



	public int getImgId() {
		return imgId;
	}

	public void setImgId(int imgId) {
		this.imgId = imgId;
	}


	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public List<String> getUrlImgs() {
		return urlImgs;
	}

	public void setUrlImgs(List<String> urlImgs) {
		this.urlImgs = urlImgs;
	}

	

	
	
	
	
}
