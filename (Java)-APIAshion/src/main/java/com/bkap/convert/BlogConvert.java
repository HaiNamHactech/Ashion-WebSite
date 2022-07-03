package com.bkap.convert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bkap.dto.BlogDto;
import com.bkap.entities.Blog;
import com.bkap.repositories.CategoryBlogRepository;

@Component
public class BlogConvert {
	@Autowired
	CategoryBlogRepository categoryBlogRepository;
	
	public Blog toEntity(BlogDto blogDto) {
		Blog blog = new Blog();
		blog.setTitleBlog(blogDto.getTitleBlog());
		blog.setContent(blogDto.getContent());
		blog.setUrlImg(blogDto.getUrlImg());
		blog.setCategoryBlog(categoryBlogRepository.getOne(blogDto.getCategoryId()));
		return blog;
	}
	
	public BlogDto toDto(Blog entity) {
		BlogDto blogDto = new BlogDto();
		blogDto.setBlogId(entity.getBlogId());
		blogDto.setCategoryId(entity.getCategoryBlog().getCategoryId());
		blogDto.setCategoryName(entity.getCategoryBlog().getCategoryName());
		blogDto.setContent(entity.getContent());
		blogDto.setTitleBlog(entity.getTitleBlog());
		blogDto.setUrlImg(entity.getUrlImg());
		if(entity.getCommentBlogs() != null ) {
			blogDto.setTotalComment(entity.getCommentBlogs().size());
		}
		blogDto.setCreateDate(entity.getCreateDate());
		blogDto.setModifieDate(entity.getModifieDate());
		blogDto.setCreateBy(entity.getCreateBy());
		blogDto.setModifieBy(entity.getModifieBy());
		return blogDto;
	}
	
	public Blog toEntity(BlogDto blogDto,Blog blog) {
		blog.setTitleBlog(blogDto.getTitleBlog());
		blog.setContent(blogDto.getContent());
		blog.setUrlImg(blogDto.getUrlImg());
		blog.setCategoryBlog(categoryBlogRepository.getOne(blogDto.getCategoryId()));
		return blog;
	}
	

}
