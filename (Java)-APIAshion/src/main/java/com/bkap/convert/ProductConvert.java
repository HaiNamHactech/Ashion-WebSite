package com.bkap.convert;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.bkap.dto.ProductDto;
import com.bkap.entities.Product;
import com.bkap.repositories.CategoryRepository;

@Component
public class ProductConvert {
	
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ColorSizeProductConvert convert;
	
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
	
	public Product toEntity(ProductDto dto) {
		Product product = new Product();
		product.setProductName(dto.getProductName());
		product.setVote(dto.getVote());
		product.setTrendProduct(dto.getTrendProduct());
		product.setFeatureProduct(dto.getFeatureProduct());
		product.setNewProduct(dto.getNewProduct());
		product.setBrand(dto.getBrand());
		product.setCountBy(dto.getCountBy());
		product.setPrice(dto.getPrice());
		product.setDiscount(dto.getDiscount());
		product.setThumbnail(dto.getThumbnail());
		product.setStatus(dto.getStatus());
		product.setCategoryProduct(categoryRepository.getOne(dto.getCategoryPrdId()));
		product.setDescriptionDetail(dto.getDescriptionDetail());
		product.setDescriptionShort(dto.getDescriptionShort());
		return product;
	}
	
	public Product toEntity(ProductDto dto,Product product) {
		product.setProductName(dto.getProductName());
		product.setVote(dto.getVote());
		product.setTrendProduct(dto.getTrendProduct());
		product.setFeatureProduct(dto.getFeatureProduct());
		product.setNewProduct(dto.getNewProduct());
		product.setBrand(dto.getBrand());
		product.setCountBy(dto.getCountBy());
		product.setPrice(dto.getPrice());
		product.setDiscount(dto.getDiscount());
		product.setThumbnail(dto.getThumbnail());
		product.setStatus(dto.getStatus());
		product.setCategoryProduct(categoryRepository.getOne(dto.getCategoryPrdId()));
		product.setDescriptionDetail(dto.getDescriptionDetail());
		product.setDescriptionShort(dto.getDescriptionShort());
		return product;
	}
	
	public ProductDto toDto(Product entity) {
		ProductDto productDto = new ProductDto();
		productDto.setProductId(entity.getProductId());
		productDto.setProductName(entity.getProductName());
		productDto.setVote(entity.getVote());
		productDto.setTrendProduct(entity.getTrendProduct());
		productDto.setNewProduct(entity.getNewProduct());
		productDto.setFeatureProduct(entity.getFeatureProduct());
		productDto.setCreateDate(entity.getCreateDate());
		productDto.setModifieDate(entity.getModifieDate());
		productDto.setCreateBy(entity.getCreateBy());
		productDto.setModifieBy(entity.getModifieBy());
		productDto.setCategoryPrdId(entity.getCategoryProduct().getCategoryId());
		productDto.setThumbnail(entity.getThumbnail());
		productDto.setStatus(entity.getStatus());
		productDto.setBrand(entity.getBrand());
		productDto.setPrice(entity.getPrice());
		productDto.setDiscount(entity.getDiscount());
		productDto.setCountBy(entity.getCountBy());
		productDto.setCategoryName(entity.getCategoryProduct().getCategoryName());
		
		if(entity.getColorSizeProducts() != null) {
			productDto.setListColorSizeDto(convert.toListDto(entity.getColorSizeProducts()));
		}
		if(entity.getProductImages() != null && entity.getProductImages().size() > 0) {
			productDto.setUrlImgs(toList(entity.getProductImages().get(0).getUrlImg()));
			productDto.setUrlImgId(entity.getProductImages().get(0).getImgId());
		}
		productDto.setDescriptionShort(entity.getDescriptionShort());
		productDto.setDescriptionDetail(entity.getDescriptionDetail());
		
		return productDto;
	}


	
}
