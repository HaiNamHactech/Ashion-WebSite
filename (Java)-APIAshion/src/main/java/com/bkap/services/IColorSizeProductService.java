package com.bkap.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.bkap.dto.ColorSizeProductDto;
import com.bkap.filter.SizeColorPrdFilter;

@Service
public interface IColorSizeProductService {
	
	List<ColorSizeProductDto> getAll();
	
	Page<ColorSizeProductDto> where(SizeColorPrdFilter filter);
	
	ColorSizeProductDto findOne(int colorSizePrdId);
	
	List<ColorSizeProductDto> save(List<ColorSizeProductDto> listColorSizeProductDto);
	
	ColorSizeProductDto edit(ColorSizeProductDto colorSizeProductDto);
	
	void remove(int colorSizePrdId);
}
