package com.bkap.dto;

public class InstagramDto {
	
	private int instaId;
	private int displayNo;
	private String urlImg;
	
	public InstagramDto() {
	}

	public InstagramDto(int instaId, int displayNo, String urlImg) {
		this.instaId = instaId;
		this.displayNo = displayNo;
		this.urlImg = urlImg;
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
