package com.bkap.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.bkap.dto.ProductDto;
import com.bkap.filter.ProductFilter;

@Service
public interface IProductService {
	
	List<ProductDto> getAll();
	
	Page<ProductDto> where(ProductFilter filter);
	
	ProductDto getProductDtoByName(String productName);
	
	ProductDto findOne(int productId);
	
	ProductDto save(ProductDto productDto);
	
	ProductDto edit(ProductDto productDto);
	
	void remove(int productId);
	

}
