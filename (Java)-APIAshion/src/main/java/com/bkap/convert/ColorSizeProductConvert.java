package com.bkap.convert;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bkap.dto.ColorSizeProductDto;
import com.bkap.entities.ColorSizeProduct;
import com.bkap.repositories.ColorRepository;
import com.bkap.repositories.ProductRepository;
import com.bkap.repositories.SizeRepository;

@Component
public class ColorSizeProductConvert {
	
	@Autowired
	private ColorRepository colorRepository;
	
	@Autowired
	private SizeRepository sizeRepository;
	
	@Autowired
	private ProductRepository productRepository;

	public ColorSizeProduct toEntity(ColorSizeProductDto dto) {
		ColorSizeProduct colorSizeProduct = new ColorSizeProduct();
		colorSizeProduct.setColor(colorRepository.findByColorName(dto.getColorName()));
		colorSizeProduct.setSize(sizeRepository.findBySizeName(dto.getSizeName()));
		colorSizeProduct.setQuantity(dto.getQuantity());
		colorSizeProduct.setProductCSP(productRepository.getOne(dto.getProductId()));
		return colorSizeProduct;
	}
	
	public ColorSizeProduct toEntity(ColorSizeProductDto dto, ColorSizeProduct colorSizeProduct) {
		colorSizeProduct.setColor(colorRepository.getOne(dto.getColorId()));
		colorSizeProduct.setSize(sizeRepository.getOne(dto.getSizeId()));
		colorSizeProduct.setQuantity(dto.getQuantity());
		colorSizeProduct.setProductCSP(productRepository.getOne(dto.getProductId()));
		return colorSizeProduct;
	}
	
	public ColorSizeProductDto toDto(ColorSizeProduct entity) {
		ColorSizeProductDto colorSizeProductDto = new ColorSizeProductDto();
		colorSizeProductDto.setProductId(entity.getProductCSP().getProductId());
		colorSizeProductDto.setQuantity(entity.getQuantity());
		colorSizeProductDto.setId(entity.getId());
		colorSizeProductDto.setProductName(entity.getProductCSP().getProductName());
		colorSizeProductDto.setCode(entity.getColor().getCode());
		colorSizeProductDto.setColorId(entity.getColor().getColorId());
		colorSizeProductDto.setColorName(entity.getColor().getColorName());
		colorSizeProductDto.setSizeId(entity.getSize().getSizeId());
		colorSizeProductDto.setSizeName(entity.getSize().getSizeName());
		return colorSizeProductDto;
	}
	
	public List<ColorSizeProductDto> toListDto(List<ColorSizeProduct> listColorSizeProduct){
		List<ColorSizeProductDto> listCSP = new ArrayList<ColorSizeProductDto>();
		listColorSizeProduct.forEach(item -> listCSP.add(toDto(item)));
		return listCSP;
	}
	
}
