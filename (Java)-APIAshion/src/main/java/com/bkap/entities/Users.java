package com.bkap.entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name ="users")
@JsonIgnoreProperties(value= {"hibernateLazyInitializer","handler"})
public class Users {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "userId")
	private Integer userId;
	@Column(name = "firstName")
	private String firstName;
	@Column(name = "lastName")
	private String lastName;
	@Column(name = "email")
	private String email;
	@Column(name = "passWord")
	private String passWord;
	@Column(name = "createDate")
	private Date createDate;
	@Column(name = "modifieDate")
	private Date modifieDate;
	@Column(name = "createBy")
	private String createBy;
	@Column(name = "modifieBy")
	private String modifieBy;
	@Column(name = "avatar")
	private String avatar;
	@Column(name = "status")
	private Boolean status;
	
	@JsonIgnore
	@OneToMany(mappedBy="userR" , fetch=FetchType.EAGER)
	private List<UserRole> userRoles;
	
	@JsonIgnore
	@OneToMany(mappedBy="userB",fetch=FetchType.LAZY)
	private List<CommentBlog> commentBlogs;
	
	@JsonIgnore
	@OneToMany(mappedBy="user",fetch=FetchType.LAZY)
	private List<Contact> contacts;
	
	@JsonIgnore
	@OneToMany(mappedBy="userO",fetch=FetchType.LAZY)
	private List<Order> orders;
	
	public Users() {
	}

	public Users(Integer userId) {
		this.userId = userId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
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

	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}
	
	@Transient
	public List<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (UserRole ur : userRoles){
			authorities.add(new SimpleGrantedAuthority(ur.getRole().getName()));
		}
		return authorities;
	}

	

}
