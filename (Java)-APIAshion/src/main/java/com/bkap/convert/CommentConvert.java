package com.bkap.convert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bkap.dto.CommentDto;
import com.bkap.entities.CommentBlog;
import com.bkap.repositories.BlogRepository;
import com.bkap.repositories.CommentRepository;
import com.bkap.repositories.UserRepository;

@Component
public class CommentConvert {
	
	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	BlogRepository blogRepository;
	
	@Autowired
	UserRepository userRepository;
	
	
	public CommentBlog toEntity(CommentDto dto) {
		CommentBlog commentEntity = new CommentBlog();
		commentEntity.setContent(dto.getContent());
		commentEntity.setBlog(blogRepository.getOne(dto.getBlogId()));
		commentEntity.setUserB(userRepository.getOne(dto.getUserId()));
		commentEntity.setFavourite(dto.getFavourite());
		commentEntity.setFeedbackCommentId(dto.getFeedbackCommentId());
		commentEntity.setStatus(dto.getStatus());
		return commentEntity;
	}
	
	public CommentDto toDto(CommentBlog entity) {
		CommentDto commentDto = new CommentDto();
		commentDto.setCommentId(entity.getCommentId());
		commentDto.setBlogId(entity.getBlog().getBlogId());
		commentDto.setUserId(entity.getUserB().getUserId());
		commentDto.setCreateBy(entity.getUserB().getFirstName());
		commentDto.setCreateDate(entity.getCreateDate());
		commentDto.setFavourite(entity.getFavourite());
		commentDto.setContent(entity.getContent());
		commentDto.setModifieBy(entity.getModifieBy());
		commentDto.setModifieDate(entity.getModifieDate());
		commentDto.setFeedbackCommentId(entity.getFeedbackCommentId());
		commentDto.setAvatar(entity.getUserB().getAvatar());
		commentDto.setStatus(entity.getStatus());
		return commentDto;
	}
	
	public CommentBlog toEntity(CommentDto commentDto, CommentBlog commentEntity) {
		commentEntity.setContent(commentDto.getContent());
		commentEntity.setBlog(blogRepository.getOne(commentDto.getBlogId()));
		commentEntity.setUserB(userRepository.getOne(commentDto.getUserId()));
		commentEntity.setFavourite(commentDto.getFavourite());
		commentEntity.setFeedbackCommentId(commentDto.getFeedbackCommentId());
		commentEntity.setStatus(commentDto.getStatus());
		return commentEntity;
	}

}
