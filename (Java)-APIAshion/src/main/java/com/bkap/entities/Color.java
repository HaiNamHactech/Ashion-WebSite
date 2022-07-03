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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "color")
@JsonIgnoreProperties(value= {"hibernateLazyInitializer","handler"})
public class Color {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int colorId;
	@Column(name = "colorName")
	private String colorName;
	@Column(name ="code")
	private String code;
	
	@JsonIgnore
	@OneToMany(mappedBy="color", fetch= FetchType.LAZY)
	private List<OrderDetail> orderDetails;
	
	public Color() {
	}
	
	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}


	public int getColorId() {
		return colorId;
	}

	public void setColorId(int colorId) {
		this.colorId = colorId;
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

	
	
	
	
	

}
