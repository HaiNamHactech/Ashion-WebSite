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

import com.bkap.convert.ColorSizeProductConvert;
import com.bkap.dto.ColorSizeProductDto;
import com.bkap.entities.ColorSizeProduct;
import com.bkap.filter.SizeColorPrdFilter;
import com.bkap.repositories.ColorSizeRepository;
import com.bkap.services.IColorSizeProductService;

@Service
public class ColorSizeProductServiceImpl implements IColorSizeProductService {
	
	@Autowired
	private ColorSizeRepository colorSizeRepository;
	
	@Autowired
	private ColorSizeProductConvert convert;

	@Override
	public List<ColorSizeProductDto> getAll() {
		List<ColorSizeProduct> listCSP = colorSizeRepository.findAll();
		List<ColorSizeProductDto> listCSPDto = new ArrayList<ColorSizeProductDto>();
		listCSP.forEach(item -> listCSPDto.add(convert.toDto(item)));
		return listCSPDto;
	}

	@Override
	public ColorSizeProductDto findOne(int colorSizePrdId) {
		return convert.toDto(colorSizeRepository.getOne(colorSizePrdId));
	}
	
	@Override
	public List<ColorSizeProductDto> save(List<ColorSizeProductDto> listColorSizeProductDto) {
		List<ColorSizeProduct> listCSP = new ArrayList<>();
		for(int i = 0 ; i < listColorSizeProductDto.size(); i++) {
			listCSP.add(colorSizeRepository.save(convert.toEntity(listColorSizeProductDto.get(i))));
		}
		return convert.toListDto(listCSP);
	}


	@Override
	public ColorSizeProductDto edit(ColorSizeProductDto colorSizeProductDto) {
		ColorSizeProduct colorSizeProduct = new ColorSizeProduct();
		ColorSizeProduct oldColorSizeProduct = colorSizeRepository.getOne(colorSizeProductDto.getId());
		System.out.println(oldColorSizeProduct);
		colorSizeProduct = colorSizeRepository.save(convert.toEntity(colorSizeProductDto, oldColorSizeProduct));
		return convert.toDto(colorSizeProduct);
	}

	@Override
	public void remove(int colorSizePrdId) {
		colorSizeRepository.deleteById(colorSizePrdId);
		
	}

	@Override
	public Page<ColorSizeProductDto> where(SizeColorPrdFilter filter) {
		Page<ColorSizeProductDto> pageSizeColor = null;
		List<ColorSizeProductDto> listSizeColorDto = new ArrayList<>();
		Sort sort = Sort.by(filter.getSort()).ascending();
		if(!filter.isAsc()) {
			sort = Sort.by(filter.getSort()).descending();
		}
		PageRequest pageRequest = PageRequest.of(filter.getPage(),filter.getPageSize(),sort);
		List<ColorSizeProduct> listColorSize = colorSizeRepository.findAll(new Specification<ColorSizeProduct>() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public Predicate toPredicate(Root<ColorSizeProduct> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (filter.getId() != 0) {
					predicates.add(criteriaBuilder.equal(root.get("id"), filter.getId()));
				}
				if(filter.getColorName() != null && !filter.getColorName().isEmpty()) {
					predicates.add(criteriaBuilder.like(root.get("colorName"),"%" + filter.getColorName() + "%"));
				}
				if(filter.getSizeName() != null && !filter.getSizeName().isEmpty()) {
					predicates.add(criteriaBuilder.like(root.get("sizeName"),"%" + filter.getSizeName() + "%"));
				}
				if(filter.getProductName() != null && !filter.getProductName().isEmpty()) {
					predicates.add(criteriaBuilder.like(root.get("productName"),"%" + filter.getProductName() + "%"));
				}
				if (filter.getColorId() != 0) {
					predicates.add(criteriaBuilder.equal(root.get("colorId"), filter.getColorId()));
				}
				if (filter.getSizeId() != 0) {
					predicates.add(criteriaBuilder.equal(root.get("sizeId"), filter.getSizeId()));
				}
				if (filter.getProductId() != 0) {
					predicates.add(criteriaBuilder.equal(root.get("productId"), filter.getProductId()));
				}
				if (filter.getQuantity() != 0) {
					predicates.add(criteriaBuilder.equal(root.get("quantity"), filter.getQuantity()));
				}
				if (filter.getCode() != null && !filter.getCode().isEmpty()) {
					predicates.add(criteriaBuilder.equal(root.get("code"), filter.getCode()));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		});
		
		if (listColorSize != null) {
			listColorSize.forEach(item -> { listSizeColorDto.add(convert.toDto(item));});
			Collections.reverse(listSizeColorDto);
			List<ColorSizeProductDto> output = new ArrayList<>();
			int total = listSizeColorDto.size();
			int start = (int) pageRequest.getOffset();
			int end = Math.min((start + pageRequest.getPageSize()), total);
			if (start <= end) {
				output = listSizeColorDto.subList(start, end);
			}
			pageSizeColor = new PageImpl<ColorSizeProductDto>(output, pageRequest, total);
		}
		return pageSizeColor;
	}

	


}
