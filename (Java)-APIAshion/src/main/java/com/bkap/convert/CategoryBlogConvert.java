package com.bkap.convert;

import org.springframework.stereotype.Component;

import com.bkap.dto.CategoryBlogDto;
import com.bkap.entities.CategoryBlog;

@Component
public class CategoryBlogConvert {
	
	public CategoryBlog toEntity(CategoryBlogDto dto) {
		CategoryBlog categoryBlog = new CategoryBlog();
		categoryBlog.setCategoryName(dto.getCategoryName());
		categoryBlog.setParentId(dto.getParentId());
		categoryBlog.setSlug(dto.getSlug());
		return categoryBlog;
	}
	
	
	public CategoryBlogDto toDto(CategoryBlog entity) {
		CategoryBlogDto categoryBlogDto = new CategoryBlogDto();
		categoryBlogDto.setCategoryId(entity.getCategoryId());
		categoryBlogDto.setCategoryName(entity.getCategoryName());
		categoryBlogDto.setSlug(entity.getSlug());
		categoryBlogDto.setParentId(entity.getParentId());
		if(entity.getListBlog() != null) {
			categoryBlogDto.setTotalItem(entity.getListBlog().size());
		}
		return categoryBlogDto;
	}
	
	
	public CategoryBlog toEntity(CategoryBlog categoryBlog, CategoryBlogDto dto) {
		categoryBlog.setCategoryName(dto.getCategoryName());
		categoryBlog.setParentId(dto.getParentId());
		categoryBlog.setSlug(dto.getSlug());
		return categoryBlog;
	}

}
