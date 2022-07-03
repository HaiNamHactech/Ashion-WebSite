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
@Table(name ="orderDetail")
public class OrderDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int orderDetailId;
	@Column(name = "price")   
	private float price;
	@Column(name ="quantity")
	private int quantity;
	@Column(name = "total")
	private float total;
	
	@ManyToOne
	@JoinColumn(name = "orderId", referencedColumnName="orderId")
	private Order order;
	
	@ManyToOne
	@JoinColumn(name = "productId" , referencedColumnName="productId")
	private Product product;
	
	@ManyToOne
	@JoinColumn(name = "sizeId" , referencedColumnName="sizeId")
	private Size size;
	
	@ManyToOne
	@JoinColumn(name = "colorId" , referencedColumnName="colorId")
	private Color color;

	public int getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(int orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	
	
}
