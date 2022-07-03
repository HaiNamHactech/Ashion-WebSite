package com.bkap.dto;

public class ColorSizeProductDto {
	
	private int id;
	private String productName;
	private String sizeName;
	private String colorName;
	private String code;
	private int quantity;
	private int productId;
	private int colorId;
	private int sizeId;
	


	public ColorSizeProductDto() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
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

	public int getColorId() {
		return colorId;
	}

	public void setColorId(int colorId) {
		this.colorId = colorId;
	}

	public int getSizeId() {
		return sizeId;
	}

	public void setSizeId(int sizeId) {
		this.sizeId = sizeId;
	}

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSizeName() {
		return sizeName;
	}

	public void setSizeName(String sizeName) {
		this.sizeName = sizeName;
	}

	@Override
	public String toString() {
		return "ColorSizeProductDto [id=" + id + ", colorId=" + colorId + ", sizeId=" + sizeId + ", quantity="
				+ quantity + ", colorName=" + colorName + ", code=" + code + ", sizeName=" + sizeName + ", productId="
				+ productId + ", productName=" + productName + "]";
	}
	
	

}
