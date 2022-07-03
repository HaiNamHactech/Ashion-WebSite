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

import com.bkap.convert.CategoryBlogConvert;
import com.bkap.dto.CategoryBlogDto;
import com.bkap.entities.CategoryBlog;
import com.bkap.filter.CategoryBlogFilter;
import com.bkap.repositories.CategoryBlogRepository;
import com.bkap.services.ICategoryBlogService;

@Service
public class CategoryBlogServiceImpl implements ICategoryBlogService {
	
	
	@Autowired
	CategoryBlogRepository  categoryBlogRepository;
	
	@Autowired
	CategoryBlogConvert convert;

	@Override
	public List<CategoryBlogDto> getAll() {
		List<CategoryBlog> listCategoryBlogEntity = categoryBlogRepository.findAll();
		List<CategoryBlogDto> listCategoryBlogDto = new ArrayList<CategoryBlogDto>();
		listCategoryBlogEntity.forEach(item -> {
			listCategoryBlogDto.add(convert.toDto(item));
		});
		return listCategoryBlogDto;
	}

	@Override
	public CategoryBlogDto findOne(int cateId) {		
		return convert.toDto(categoryBlogRepository.findById(cateId).get());
	}


	@Override
	public CategoryBlogDto save(CategoryBlogDto categoryBlogDto) {
		CategoryBlog categoryBlog = new CategoryBlog();
		if(categoryBlogDto.getCategoryId() != 0) {
			CategoryBlog oldCategoryBlog = categoryBlogRepository.findById(categoryBlogDto.getCategoryId()).get();
			categoryBlog = categoryBlogRepository.save(convert.toEntity(oldCategoryBlog,categoryBlogDto));	
		}
		else {
			categoryBlog = categoryBlogRepository.save(convert.toEntity(categoryBlogDto));
		}
		return convert.toDto(categoryBlog);
	}

	@Override
	public void remove(int cateId) {
		categoryBlogRepository.deleteById(cateId);
	}

	@Override
	public CategoryBlogDto edit(CategoryBlogDto categoryBlogDto) {
		return null;
	}

	@Override
	public Page<CategoryBlogDto> where(CategoryBlogFilter filter) {
		Page<CategoryBlogDto> listPageCateDto = null;
		List<CategoryBlogDto> listCateDto = new ArrayList<>();
		Sort sort = Sort.by(filter.getSort()).ascending();
		if(!filter.isAsc()) {
			sort = Sort.by(filter.getSort()).descending();
		}
		PageRequest pageRequest = PageRequest.of(filter.getPage(),filter.getPageSize(),sort);
		List<CategoryBlog> listCateBlog= categoryBlogRepository.findAll(new Specification<CategoryBlog>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<CategoryBlog> root, CriteriaQuery<?> query,CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (filter.getCategoryId() != 0) {
					predicates.add(criteriaBuilder.equal(root.get("categoryId"), filter.getCategoryId()));
				}
				if(filter.getCategoryName() != null && !filter.getCategoryName().isEmpty()) {
					predicates.add(criteriaBuilder.like(root.get("categoryName"),"%" + filter.getCategoryName() + "%"));
				}
				if(filter.getParentId() != 0) {
					predicates.add(criteriaBuilder.equal(root.get("parentId"), filter.getParentId()));
				}
				if(filter.getSlug() != null && !filter.getSlug().isEmpty()) {
					predicates.add(criteriaBuilder.like(root.get("slug"),"%" + filter.getSlug() + "%"));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		});
		if (listCateBlog != null) {
			listCateBlog.forEach(item -> { listCateDto.add(convert.toDto(item));});
			Collections.reverse(listCateDto);
			List<CategoryBlogDto> output = new ArrayList<>();
			int total = listCateDto.size();
			int start = (int) pageRequest.getOffset();
			int end = Math.min((start + pageRequest.getPageSize()), total);
			if (start <= end) {
				output = listCateDto.subList(start, end);
			}
			listPageCateDto = new PageImpl<CategoryBlogDto>(output, pageRequest, total);
		}
		return listPageCateDto;
	}

	@Override
	public CategoryBlogDto findByName(String categoryName) {		
		return convert.toDto(categoryBlogRepository.findByCategoryName(categoryName));
	}

	@Override
	public CategoryBlogDto findBySlug(String slug) {
		return convert.toDto(categoryBlogRepository.findBySlug(slug));
	}

}
