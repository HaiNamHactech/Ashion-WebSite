package com.bkap.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "user_role")
@JsonIgnoreProperties(value= {"hibernateLazyInitializer","handler"})
public class UserRole {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name ="userId", referencedColumnName="userId")
	private Users userR;
	
	@ManyToOne
	@JoinColumn(name ="roleId", referencedColumnName="roleId")
	private Role role;

	public UserRole() {
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Users getUserR() {
		return userR;
	}
	public void setUserR(Users userR) {
		this.userR = userR;
	}
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	
	
}
