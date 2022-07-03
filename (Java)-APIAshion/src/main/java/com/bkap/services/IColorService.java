package com.bkap.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.bkap.dto.ColorDto;
import com.bkap.filter.ColorFilter;

@Service
public interface IColorService {
	
	List<ColorDto> getAll();
	
	Page<ColorDto> where(ColorFilter filter);
	
	ColorDto save(ColorDto colorDto);
	
	ColorDto edit(ColorDto colorDto);
	
	ColorDto findOne(int colorId);
	
	ColorDto findByCode(String code);
	
	ColorDto findByName(String name);
	
	void remove(int colorId);
}
