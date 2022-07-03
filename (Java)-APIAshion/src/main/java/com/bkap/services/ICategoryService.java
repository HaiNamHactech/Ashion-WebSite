package com.bkap.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.bkap.dto.CategoryProductDto;
import com.bkap.filter.CategoryProductFilter;

@Service
public interface ICategoryService {

	List<CategoryProductDto> getAll();

	Page<CategoryProductDto> where(CategoryProductFilter filter);

	CategoryProductDto save(CategoryProductDto categoryPrd);

	CategoryProductDto edit(CategoryProductDto categoryPrd);

	CategoryProductDto findOne(int id);

	CategoryProductDto findByCategoryName(String categoryName);

	CategoryProductDto findBySlug(String slug);

	CategoryProductDto fintByDisplayNo(int displayNo);

	void remove(int cateId);

}
