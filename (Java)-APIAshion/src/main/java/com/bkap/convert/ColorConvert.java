package com.bkap.convert;

import org.springframework.stereotype.Component;

import com.bkap.dto.ColorDto;
import com.bkap.entities.Color;

@Component
public class ColorConvert {
	
	public Color toEntity(ColorDto colorDto) {
		Color color = new Color();
		color.setColorName(colorDto.getColorName());
		color.setCode(colorDto.getCode());
		return color;
	}
	
	public Color toEntity(ColorDto colorDto,Color color) {
		color.setColorName(colorDto.getColorName());
		color.setCode(colorDto.getCode());
		return color;
	}
	
	public ColorDto toDto(Color color) {
		ColorDto colorDto = new ColorDto();
		colorDto.setColorId(color.getColorId());
		colorDto.setCode(color.getCode());
		colorDto.setColorName(color.getColorName());
		return colorDto;
	}

}
