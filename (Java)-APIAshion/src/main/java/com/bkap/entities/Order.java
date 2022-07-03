package com.bkap.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;



//@JsonIgnoreProperties(value= {"hibernateLazyInitializer","handler"})
@Entity
@Table(name ="orders")
@EntityListeners(AuditingEntityListener.class)
public class Order {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int orderId;
	@Column(name = "firstName")
	private String firstName;
	@Column(name = "lastName")
	private String lastName;
	@Column(name ="phone")
	private String phone;
	@Column(name ="createBy")
	@CreatedBy
	private String createBy;
	@Column(name ="modifieBy")
	@LastModifiedBy
	private String modifieBy;
	@Column(name ="createDate")
	@CreatedDate
	private Date createDate;
	@Column(name ="modifieDate")
	@LastModifiedDate
	private Date modifieDate;
	@Column(name ="address")
	private String address;
	@Column(name ="note")
	private String note;
	@Column(name ="total")
	private float total;
	@Column(name ="status")
	private int status;
	@Column(name ="email")
	private String email;
	
	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY,mappedBy="order")
	private List<OrderDetail> orderDetails;
	
	@ManyToOne
	@JoinColumn(name = "userId" , referencedColumnName="userId")
	private Users userO;
	

	public Users getUserO() {
		return userO;
	}

	public void setUserO(Users userO) {
		this.userO = userO;
	}

	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public Order() {
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Users getUser() {
		return userO;
	}

	public void setUser(Users user) {
		this.userO = user;
	}
	
	
}
