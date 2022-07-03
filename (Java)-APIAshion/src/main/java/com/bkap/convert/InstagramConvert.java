package com.bkap.convert;

import org.springframework.stereotype.Component;

import com.bkap.dto.InstagramDto;
import com.bkap.entities.Instagram;

@Component
public class InstagramConvert {
	
	public  Instagram toEntity(InstagramDto instagramDto) {
		Instagram instagram = new Instagram();
		instagram.setDisplayNo(instagramDto.getDisplayNo());
		instagram.setUrlImg(instagramDto.getUrlImg());
		return instagram;
	}
	
	public  Instagram toEntity(InstagramDto instagramDto,Instagram instagram) {
		instagram.setDisplayNo(instagramDto.getDisplayNo());
		instagram.setUrlImg(instagramDto.getUrlImg());
		return instagram;
	}  
	
	public InstagramDto toDto(Instagram instagram) {
		InstagramDto instagramDto = new InstagramDto();
		instagramDto.setInstaId(instagram.getInstaId());
		instagramDto.setDisplayNo(instagram.getDisplayNo());
		instagramDto.setUrlImg(instagram.getUrlImg());
		return instagramDto;
	}
	

}
