package com.bkap.filter;

import java.io.Serializable;

public class OrderDetailFilter implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int orderDetailId;

	private int orderId;

	private String productName;

	private String sort;

	private int page;

	private int pageSize;

	private boolean asc;

	public OrderDetailFilter() {
		this.page = 0;
		this.pageSize = 6;
		this.sort = "orderDetailId";
		this.asc = true;
	}

	public int getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(int orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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
	
	
	
	
}
