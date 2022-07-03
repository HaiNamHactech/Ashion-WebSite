package com.bkap.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.bkap.dto.BlogDto;
import com.bkap.filter.BlogFilter;

@Service
public interface IBlogService {
	
	List<BlogDto> getAll();
	
	Page<BlogDto> where(BlogFilter filter);
	
	BlogDto findOne(int blogId);
	
	BlogDto save(BlogDto blogDto);
	
	BlogDto edit(BlogDto blogDto);
	
	void remove(int blogId);
	

}
