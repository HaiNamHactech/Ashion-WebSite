package com.bkap.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "slider")
public class Slider {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int sliderId;
	@Column(name ="title")
	private String title;
	@Column(name ="content")
	private String content;
	@Column(name ="displayNo") 
	private int displayNo;
	
	public Slider() {
	}
	
	public int getSliderId() {
		return sliderId;
	}
	public void setSliderId(int sliderId) {
		this.sliderId = sliderId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getDisplayNo() {
		return displayNo;
	}
	public void setDisplayNo(int displayNo) {
		this.displayNo = displayNo;
	}
	
	
}
