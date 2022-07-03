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

import com.bkap.convert.BlogConvert;
import com.bkap.dto.BlogDto;
import com.bkap.entities.Blog;
import com.bkap.filter.BlogFilter;
import com.bkap.repositories.BlogRepository;
import com.bkap.services.IBlogService;

@Service
public class BlogServiceImpl implements IBlogService {
	
	@Autowired
	BlogConvert convert;
	
	@Autowired
	BlogRepository blogRepository;

	@Override
	public List<BlogDto> getAll() {
		List<Blog> listBlog = blogRepository.findAll();
		List<BlogDto> listBlogDto = new ArrayList<BlogDto>();
		listBlog.forEach(item -> {listBlogDto.add(convert.toDto(item));});
		return listBlogDto ;
	}

	@Override
	public BlogDto findOne(int blogId) {
		return convert.toDto(blogRepository.getOne(blogId));
	}

	@Override
	public BlogDto save(BlogDto blogDto) {
		Blog blog = new Blog();
		if(blogDto.getBlogId() != 0) {
			Blog oldBlog = blogRepository.findById(blogDto.getBlogId()).get();
			blog = blogRepository.save(convert.toEntity(blogDto, oldBlog));
		}
		else {
			blog = blogRepository.save(convert.toEntity(blogDto));
		}
		return convert.toDto(blog);
	}


	@Override
	public void remove(int blogId) {
		blogRepository.deleteById(blogId);
	}
		
	@Override
	public BlogDto edit(BlogDto blogDto) {
		
		return null;
	}

	@Override
	public Page<BlogDto> where(BlogFilter filter) {
		Page<BlogDto> pageBlogDto = null;
		List<BlogDto> listBlogDto = new ArrayList<>();
		Sort sort = Sort.by(filter.getSort()).ascending();
		if(!filter.isAsc()) {
			sort = Sort.by(filter.getSort()).descending();
		}
		PageRequest pageRequest = PageRequest.of(filter.getPage(),filter.getPageSize(),sort);
		List<Blog> listBlog= blogRepository.findAll(new Specification<Blog>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (filter.getBlogId() != 0) {
					predicates.add(criteriaBuilder.equal(root.get("blogId"), filter.getBlogId()));
				}
				if(filter.getTitleBlog() != null && !filter.getTitleBlog().isEmpty()) {
					predicates.add(criteriaBuilder.like(root.get("titleBlog"), "%" + filter.getTitleBlog() + "%"));
				}
				if(filter.getCategoryId() != 0 ) {
					predicates.add(criteriaBuilder.equal(root.get("categoryId"), filter.getBlogId()));
				}
				if(filter.getContent() != null && !filter.getContent().isEmpty()) {
					predicates.add(criteriaBuilder.like(root.get("content"), "%" + filter.getContent() + "%"));
				}
				if(filter.getCategoryName() != null && !filter.getCategoryName().isEmpty()) {
					predicates.add(criteriaBuilder.like(root.get("categoryName"), "%" + filter.getCategoryName() + "%"));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
			
		});
		if (listBlog != null) {
			listBlog.forEach(item -> { listBlogDto.add(convert.toDto(item));});
			Collections.reverse(listBlogDto);
			List<BlogDto> output = new ArrayList<>();
			int total = listBlogDto.size();
			int start = (int) pageRequest.getOffset();
			int end = Math.min((start + pageRequest.getPageSize()), total);
			if (start <= end) {
				output = listBlogDto.subList(start, end);
			}
			pageBlogDto = new PageImpl<BlogDto>(output, pageRequest, total);
		}
		return pageBlogDto;
	}
	

}
