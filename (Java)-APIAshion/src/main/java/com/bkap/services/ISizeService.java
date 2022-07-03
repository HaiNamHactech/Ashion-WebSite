package com.bkap.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.bkap.dto.SizeDto;
import com.bkap.filter.SizeFilter;

@Service
public interface ISizeService {
	
	List<SizeDto> getAll();
	
	Page<SizeDto> where(SizeFilter filter);
	
	SizeDto findOne(int sizeId);
	
	SizeDto save(SizeDto sizeDto);
	
	SizeDto edit(SizeDto sizeDto);
	
	SizeDto findBySizeName(String sizeName);
	
	void remove(int sizeId);
}
