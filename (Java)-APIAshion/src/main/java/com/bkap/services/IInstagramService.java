package com.bkap.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.bkap.dto.InstagramDto;
import com.bkap.filter.InstagramFilter;

@Service
public interface IInstagramService {
	
	List<InstagramDto> getAll();
	
	Page<InstagramDto> where(InstagramFilter filter);
	
	InstagramDto findOne(int blogId);
	
	InstagramDto save(InstagramDto instagramDto);
	
	InstagramDto edit(InstagramDto instagramDto);
	
	void remove(int instaId);
}
