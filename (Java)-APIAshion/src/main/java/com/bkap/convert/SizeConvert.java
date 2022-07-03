package com.bkap.convert;

import org.springframework.stereotype.Component;

import com.bkap.dto.SizeDto;
import com.bkap.entities.Size;

@Component
public class SizeConvert {
	
	public Size toEntity(SizeDto sizeDto) {
		Size size = new Size();
		size.setSizeName(sizeDto.getSizeName());
		size.setDescription(sizeDto.getDescription());
		return size;
	}
	
	public Size toEntity(SizeDto sizeDto,Size size) {
		size.setSizeName(sizeDto.getSizeName());
		size.setDescription(sizeDto.getDescription());
		return size;
	}
	
	public SizeDto toDto(Size size) {
		SizeDto sizeDto = new SizeDto();
		sizeDto.setSizeId(size.getSizeId());
		sizeDto.setSizeName(size.getSizeName());
		sizeDto.setDescription(size.getDescription());
		return sizeDto;
	}
	

}
