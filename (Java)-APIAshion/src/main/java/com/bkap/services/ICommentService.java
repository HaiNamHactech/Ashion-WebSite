package com.bkap.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.bkap.dto.CommentDto;
import com.bkap.filter.CommentFilter;

@Service
public interface ICommentService {
	List<CommentDto> getAll();
	
	Page<CommentDto> where(CommentFilter filter);
	
	CommentDto findOne(int commentId);
	
	CommentDto save(CommentDto commentDto);
	
	CommentDto edit(CommentDto commentDto);
	
	void remove(int commentId);
}
