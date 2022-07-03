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

import com.bkap.convert.CommentConvert;
import com.bkap.dto.CommentDto;
import com.bkap.entities.CommentBlog;
import com.bkap.filter.CommentFilter;
import com.bkap.repositories.CommentRepository;
import com.bkap.services.ICommentService;

@Service
public class CommentServiceImpl  implements ICommentService{
	
	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	CommentConvert convert;

	@Override
	public List<CommentDto> getAll() {
		List<CommentBlog> commentBlog = commentRepository.findAll();
		System.out.println(commentBlog);
		List<CommentDto> listDto = new ArrayList<CommentDto>();
		commentBlog.forEach(item -> {listDto.add(convert.toDto(item));});
		return listDto;
	}

	@Override
	public CommentDto findOne(int blogId) {
		return convert.toDto(commentRepository.findById(blogId).get());
	}

	@Override
	public CommentDto save(CommentDto commentDto) {
		CommentBlog commentBlog = new CommentBlog();
		if(commentDto.getCommentId() != 0) {
			CommentBlog oldCommentBlog = commentRepository.findById(commentDto.getCommentId()).get();
			commentBlog = commentRepository.save(convert.toEntity(commentDto, oldCommentBlog));
		}
		else {
			commentBlog = commentRepository.save(convert.toEntity(commentDto));
		}
		return convert.toDto(commentBlog);
	}

	@Override
	public CommentDto edit(CommentDto commentDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(int commentId) {
		commentRepository.deleteById(commentId);
		
	}

	@Override
	public Page<CommentDto> where(CommentFilter filter) {
		Page<CommentDto> pageCommentDto = null;
		List<CommentDto> listCommentDto = new ArrayList<>();
		Sort sort = Sort.by(filter.getSort()).ascending();
		if(!filter.isAsc()) {
			sort = Sort.by(filter.getSort()).descending();
		}
		PageRequest pageRequest = PageRequest.of(filter.getPage(),filter.getPageSize(),sort);
		List<CommentBlog> listCommentBlog= commentRepository.findAll(new Specification<CommentBlog>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<CommentBlog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (filter.getCommentId() != 0) {
					predicates.add(criteriaBuilder.equal(root.get("commentId"), filter.getCommentId()));
				}
				if(filter.getContent() != null && !filter.getContent().isEmpty()) {
					predicates.add(criteriaBuilder.like(root.get("content"), "%" + filter.getContent() + "%"));
				}
				if (filter.getBlogId() != 0) {
					predicates.add(criteriaBuilder.equal(root.get("blogId"), filter.getBlogId()));
				}
				if(filter.getUserId() != 0 ) {
					predicates.add(criteriaBuilder.equal(root.get("userId"), filter.getUserId()));
				}
				if(filter.getCreateBy() != null && !filter.getCreateBy().isEmpty()) {
					predicates.add(criteriaBuilder.like(root.get("createBy"), "%" + filter.getCreateBy() + "%"));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
			
		});
		if (listCommentBlog != null) {
			listCommentBlog.forEach(item -> { listCommentDto.add(convert.toDto(item));});
			Collections.reverse(listCommentDto);
			List<CommentDto> output = new ArrayList<>();
			int total = listCommentDto.size();
			int start = (int) pageRequest.getOffset();
			int end = Math.min((start + pageRequest.getPageSize()), total);
			if (start <= end) {
				output = listCommentDto.subList(start, end);
			}
			pageCommentDto = new PageImpl<CommentDto>(output, pageRequest, total);
		}
		return pageCommentDto;
	}

}
