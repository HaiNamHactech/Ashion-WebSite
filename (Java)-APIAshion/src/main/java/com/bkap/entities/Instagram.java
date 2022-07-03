package com.bkap.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="instagram")
public class Instagram {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int instaId;
	@Column(name = "displayNo")
	private int displayNo;
	@Column(name = "urlImg")
	private String urlImg;
	
	public Instagram() {
	}

	public int getInstaId() {
		return instaId;
	}

	public void setInstaId(int instaId) {
		this.instaId = instaId;
	}
	
	

	public int getDisplayNo() {
		return displayNo;
	}

	public void setDisplayNo(int displayNo) {
		this.displayNo = displayNo;
	}

	public String getUrlImg() {
		return urlImg;
	}

	public void setUrlImg(String urlImg) {
		this.urlImg = urlImg;
	}
	
	
	
	

}
