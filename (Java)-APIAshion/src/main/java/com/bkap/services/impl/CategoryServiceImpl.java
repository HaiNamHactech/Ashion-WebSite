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

import com.bkap.convert.CategoryProductConvert;
import com.bkap.dto.CategoryProductDto;
import com.bkap.entities.CategoryProduct;
import com.bkap.filter.CategoryProductFilter;
import com.bkap.repositories.CategoryRepository;
import com.bkap.services.ICategoryService;

@Service
public class CategoryServiceImpl implements ICategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private CategoryProductConvert convert;

	@Override
	public List<CategoryProductDto> getAll() {
		List<CategoryProduct> listCategoryPrd = categoryRepository.findAll();
		List<CategoryProductDto> listDto = new ArrayList<CategoryProductDto>();
		listCategoryPrd.forEach(item -> {listDto.add(convert.toDto(item));});
		return listDto;
	}
	

	@Override
	public CategoryProductDto save(CategoryProductDto categoryPrdDto) {	
		CategoryProduct categoryProduct = new CategoryProduct();
		if(categoryPrdDto.getCategoryId() != 0) {
			CategoryProduct oldCategoryProduct = categoryRepository.findById(categoryPrdDto.getCategoryId()).get();
			categoryProduct = categoryRepository.save(convert.toEntity(categoryPrdDto, oldCategoryProduct));
		}
		else {
			categoryProduct = categoryRepository.save(convert.toEntity(categoryPrdDto));
		}
		
		return convert.toDto(categoryProduct);
	}


	@Override
	public CategoryProductDto findOne(int cateId) {	
		return convert.toDto(categoryRepository.findById(cateId).get());
	}

	@Override
	public void remove(int cateId) {
		categoryRepository.deleteById(cateId);
		
	}

	@Override
	public CategoryProductDto edit(CategoryProductDto categoryPrd) {
		return null;
	}


	@Override
	public Page<CategoryProductDto> where(CategoryProductFilter filter) {
		Page<CategoryProductDto> listPageCateDto = null;
		List<CategoryProductDto> listCateDto = new ArrayList<>();
		Sort sort = Sort.by(filter.getSort()).ascending();
		if(!filter.isAsc()) {
			sort = Sort.by(filter.getSort()).descending();
		}
		PageRequest pageRequest = PageRequest.of(filter.getPage(),filter.getPageSize(),sort);
		List<CategoryProduct> listCatePrd = categoryRepository.findAll(new Specification<CategoryProduct>() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public Predicate toPredicate(Root<CategoryProduct> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (filter.getCategoryId() != 0) {
					predicates.add(criteriaBuilder.equal(root.get("categoryId"), filter.getCategoryId()));
				}
				if(filter.getCategoryName() != null && !filter.getCategoryName().isEmpty()) {
					predicates.add(criteriaBuilder.like(root.get("categoryName"),"%" + filter.getCategoryName() + "%"));
				}
				if(filter.getDescription() != null && !filter.getDescription().isEmpty()) {
					predicates.add(criteriaBuilder.like(root.get("description"),"%" + filter.getDescription() + "%"));
				}
				if(filter.getParentId() != 0) {
					predicates.add(criteriaBuilder.equal(root.get("parentId"), filter.getParentId()));
				}
				if(filter.getBreadcrumbLink() != null && !filter.getBreadcrumbLink().isEmpty()) {
					predicates.add(criteriaBuilder.like(root.get("breadcrumbLink"),"%" + filter.getBreadcrumbLink() + "%"));
				}
				if(filter.getSlug() != null && !filter.getSlug().isEmpty()) {
					predicates.add(criteriaBuilder.like(root.get("slug"),"%" + filter.getSlug() + "%"));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		});
		
		if (listCatePrd != null) {
			listCatePrd.forEach(item -> { listCateDto.add(convert.toDto(item));});
			Collections.reverse(listCateDto);
			List<CategoryProductDto> output = new ArrayList<>();
			int total = listCateDto.size();
			int start = (int) pageRequest.getOffset();
			int end = Math.min((start + pageRequest.getPageSize()), total);
			if (start <= end) {
				output = listCateDto.subList(start, end);
			}
			listPageCateDto = new PageImpl<CategoryProductDto>(output, pageRequest, total);
		}
		return listPageCateDto;
	}


	@Override
	public CategoryProductDto findByCategoryName(String categoryName) {
		
		return convert.toDto(categoryRepository.findByCategoryName(categoryName)) ;
	}


	@Override
	public CategoryProductDto findBySlug(String slug) {
		return convert.toDto(categoryRepository.findBySlug(slug));
	}


	@Override
	public CategoryProductDto fintByDisplayNo(int displayNo) {
		return convert.toDto(categoryRepository.findByDisplayNo(displayNo));
	}


}
