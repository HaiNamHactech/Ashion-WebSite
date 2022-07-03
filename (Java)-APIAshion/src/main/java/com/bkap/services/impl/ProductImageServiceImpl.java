package com.bkap.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.bkap.convert.ProductImageConvert;
import com.bkap.dto.ProductImageDto;
import com.bkap.entities.ProductImage;
import com.bkap.filter.ImageProductFilter;
import com.bkap.repositories.ProductImgaeRepository;
import com.bkap.services.IProductImageService;

@Service
public class ProductImageServiceImpl implements IProductImageService {
	
	@Autowired
	private ProductImgaeRepository productImgaeRepository;
	
	@Autowired
	private ProductImageConvert convert;

	@Override
	public List<ProductImageDto> getAll() {
		List<ProductImage> listProductImage = productImgaeRepository.findAll();
		List<ProductImageDto> listProductImageDto = new ArrayList<ProductImageDto>();
		listProductImage.forEach(item -> listProductImageDto.add(convert.toDto(item)));
		return listProductImageDto;
	}

	@Override
	public ProductImageDto findOne(int ProductImageId) {
		return convert.toDto(productImgaeRepository.getOne(ProductImageId));
	}

	@Override
	public ProductImageDto save(ProductImageDto productImageDto) {
		ProductImage productImage = new ProductImage();
		if(productImageDto.getImgId() != 0) {
			ProductImage oldProductImage = productImgaeRepository.getOne(productImageDto.getImgId());
			productImage = productImgaeRepository.save(convert.toEntity(productImageDto, oldProductImage));
		}
		else {
			productImage = productImgaeRepository.save(convert.toEntity(productImageDto));
		}
		return convert.toDto(productImage);
	}


	@Override
	public void remove(int productImageId) {
		productImgaeRepository.deleteById(productImageId);
		
	}

	@Override
	public ProductImageDto edit(ProductImageDto productImageDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<ProductImageDto> where(ImageProductFilter filter) {
		Page<ProductImageDto> pagePrdImage = null;
		List<ProductImageDto> listPrdImageDto = new ArrayList<>();
		Sort sort = Sort.by(filter.getSort()).ascending();
		if(!filter.isAsc()) {
			sort = Sort.by(filter.getSort()).descending();
		}
		PageRequest pageRequest = PageRequest.of(filter.getPage(),filter.getPageSize(),sort);
		List<ProductImage> listImagePrd = productImgaeRepository.findAll(new Specification<ProductImage>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<ProductImage> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (filter.getImgId() != 0) {
					predicates.add(criteriaBuilder.equal(root.get("imgId"), filter.getImgId()));
				}
				if (filter.getProductId() != 0) {
					predicates.add(criteriaBuilder.equal(root.get("productId"), filter.getProductId()));
				}	
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
			
		});
		if (listImagePrd != null) {
			listImagePrd.forEach(item -> { listPrdImageDto.add(convert.toDto(item));});
			Collections.reverse(listPrdImageDto);
			List<ProductImageDto> output = new ArrayList<>();
			int total = listPrdImageDto.size();
			int start = (int) pageRequest.getOffset();
			int end = Math.min((start + pageRequest.getPageSize()), total);
			if (start <= end) {
				output = listPrdImageDto.subList(start, end);
			}
			pagePrdImage = new PageImpl<ProductImageDto>(output, pageRequest, total);
		}
		return pagePrdImage;
	}


}
