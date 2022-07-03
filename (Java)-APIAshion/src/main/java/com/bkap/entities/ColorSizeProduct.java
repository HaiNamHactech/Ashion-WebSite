package com.bkap.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name ="color_size_prd")
public class ColorSizeProduct {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "quatity")
	private int quantity;
	
	@ManyToOne
	@JoinColumn(name = "colorId")
	private Color color;
	
	@ManyToOne
	@JoinColumn(name ="sizeId")
	private Size size;
	
	@ManyToOne
	@JoinColumn(name = "prdId")
	private Product productCSP;
	
	public ColorSizeProduct() {
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

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	public Product getProductCSP() {
		return productCSP;
	}

	public void setProductCSP(Product productCSP) {
		this.productCSP = productCSP;
	}

	
	
	

}
