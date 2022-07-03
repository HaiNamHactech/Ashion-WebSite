package com.bkap.dto;

public class SliderDto {
	
	private int sliderId;
	private String title;
	private String content;
	private int displayNo;
	
	public SliderDto() {
	}
	
	public SliderDto(int sliderId, String title, String content, int displayNo) {
		this.sliderId = sliderId;
		this.title = title;
		this.content = content;
		this.displayNo = displayNo;
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
