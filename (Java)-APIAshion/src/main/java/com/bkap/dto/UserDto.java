package com.bkap.dto;

import java.sql.Date;

public class UserDto {
	
	private int userId;
	private String firstName;
	private String lastName;
	private String email;
	private String passWord;
	private Date createDate;
	private Date modifieDate;
	private String createBy;
	private String modifieBy;
	private String avatar;
	private Boolean status;
	
	public UserDto() {
	}
	
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
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
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	
}
