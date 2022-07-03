package com.bkap.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.bkap.dto.ProductImageDto;
import com.bkap.filter.ImageProductFilter;

@Service
public interface IProductImageService {
	
	List<ProductImageDto> getAll();
	
	Page<ProductImageDto> where(ImageProductFilter filter);
	
	ProductImageDto findOne(int blogId);
	
	ProductImageDto save(ProductImageDto productImageDto);
	
	ProductImageDto edit(ProductImageDto productImageDto);
	
	void remove(int productImageId);
}
