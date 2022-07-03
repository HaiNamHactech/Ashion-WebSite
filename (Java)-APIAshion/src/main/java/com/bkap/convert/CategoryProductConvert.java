package com.bkap.convert;

import org.springframework.stereotype.Component;

import com.bkap.dto.CategoryProductDto;
import com.bkap.entities.CategoryProduct;

@Component
public class CategoryProductConvert {
	
	public CategoryProduct toEntity(CategoryProductDto Dto) {
		System.out.println(Dto.getParentId());
		CategoryProduct categoryProduct = new CategoryProduct();
		categoryProduct.setCategoryName(Dto.getCategoryName());
		categoryProduct.setDisplayNo(Dto.getDisplayNo());
		categoryProduct.setSlug(Dto.getSlug());
		categoryProduct.setUrlImg(Dto.getUrlImg());
		categoryProduct.setShowHome(Dto.getShowHome());
		categoryProduct.setDescription(Dto.getDescription());
		categoryProduct.setBreadcrumbLink(Dto.getBreadcrumbLink());
		categoryProduct.setParentId(Dto.getParentId());
		return categoryProduct;
	}
	
	public CategoryProductDto toDto(CategoryProduct entity) {
		CategoryProductDto categoryProductDto = new CategoryProductDto();
		categoryProductDto.setCategoryId(entity.getCategoryId());
		categoryProductDto.setCategoryName(entity.getCategoryName());
		categoryProductDto.setDisplayNo(entity.getDisplayNo());
		categoryProductDto.setSlug(entity.getSlug());
		categoryProductDto.setUrlImg(entity.getUrlImg());
		categoryProductDto.setDescription(entity.getDescription());
		categoryProductDto.setBreadcrumbLink(entity.getBreadcrumbLink());
		categoryProductDto.setShowHome(entity.getShowHome());
		categoryProductDto.setParentId(entity.getParentId());
		if(entity.getProducts() != null) {
			categoryProductDto.setTotalItemPrd(entity.getProducts().size());
		}
		return categoryProductDto;
	}
	
	public CategoryProduct toEntity(CategoryProductDto Dto,CategoryProduct categoryProduct) {
		categoryProduct.setCategoryName(Dto.getCategoryName());
		categoryProduct.setParentId(Dto.getParentId());
		categoryProduct.setDisplayNo(Dto.getDisplayNo());
		categoryProduct.setSlug(Dto.getSlug());
		categoryProduct.setUrlImg(Dto.getUrlImg());
		categoryProduct.setShowHome(Dto.getShowHome());
		categoryProduct.setDescription(Dto.getDescription());
		categoryProduct.setBreadcrumbLink(Dto.getBreadcrumbLink());
		return categoryProduct;
	}
	
	
}
