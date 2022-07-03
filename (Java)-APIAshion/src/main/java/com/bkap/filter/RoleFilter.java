package com.bkap.filter;

public class RoleFilter {
	
	private int roleId;
	
	private String name;
	
	private String sort;
	
	private int page;
	
	private int pageSize;
	
	private boolean asc;

	public RoleFilter() {
		this.page = 0;
		this.pageSize = 3;
		this.sort = "name";
		this.asc = true;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
