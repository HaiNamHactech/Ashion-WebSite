package com.bkap.filter;

import java.io.Serializable;
import java.util.Date;

public class OrderFilter implements Serializable  {
	
	private static final long serialVersionUID = 1L;

	private int orderId;
	
	private Date createDate;
	
	private Date modifieDate;
	
	private int userId;
	
	private String userName;
	
	private String sort;
	
	private int page;
	
	private int pageSize;
	
	private boolean asc;

	public OrderFilter() {
		this.page = 0;
		this.pageSize = 6;
		this.sort = "orderId";
		this.asc = true;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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
