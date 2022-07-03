package com.bkap.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.bkap.dto.CategoryBlogDto;
import com.bkap.filter.CategoryBlogFilter;

@Service
public interface ICategoryBlogService {

	List<CategoryBlogDto> getAll();

	Page<CategoryBlogDto> where(CategoryBlogFilter filter);

	CategoryBlogDto findOne(int cateId);

	CategoryBlogDto findByName(String categoryName);

	CategoryBlogDto findBySlug(String slug);

	CategoryBlogDto save(CategoryBlogDto categoryBlogDto);

	CategoryBlogDto edit(CategoryBlogDto categoryBlogDto);

	void remove(int cateId);

}
