package com.bkap.convert;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bkap.dto.ProductImageDto;
import com.bkap.entities.ProductImage;
import com.bkap.repositories.ProductRepository;

@Component
public class ProductImageConvert {
	
	
	@Autowired
	private ProductRepository productRepository;
	
	public static String toStr(List<String> list) {
		String str = "";
		for(int i = 0 ; i < list.size(); i++) {
			str += list.get(i);
			str += ",";
		}
		return str;
	}
	
	public static List<String> toList(String str) {
		List<String> listStr = new ArrayList<>();
		for (String c : str.split(",")) {
			   listStr.add(c);
			  }
		return listStr;
	}
	
	public ProductImage toEntity(ProductImageDto productImageDto) {
		ProductImage productImage = new ProductImage();
		productImage.setUrlImg(toStr(productImageDto.getUrlImgs()));
		productImage.setProduct(productRepository.getOne(productImageDto.getProductId()));
		return productImage;
	}
	
	public ProductImage toEntity(ProductImageDto productImageDto,ProductImage productImage) {
		productImage.setUrlImg(toStr(productImageDto.getUrlImgs()));
		productImage.setProduct(productRepository.getOne(productImageDto.getProductId()));
		return productImage;
	}
	
	public ProductImageDto toDto(ProductImage productImage) {
		ProductImageDto productImageDto = new ProductImageDto();
		productImageDto.setImgId(productImage.getImgId());
		productImageDto.setProductId(productImage.getProduct().getProductId());
		productImageDto.setUrlImgs(toList(productImage.getUrlImg()));
		return productImageDto;
	}

}
