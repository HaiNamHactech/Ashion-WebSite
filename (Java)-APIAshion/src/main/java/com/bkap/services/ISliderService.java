package com.bkap.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.bkap.dto.SliderDto;
import com.bkap.filter.SliderFilter;

@Service
public interface ISliderService {
	
	List<SliderDto> getAll();
	
	Page<SliderDto> where(SliderFilter filter);
	
	SliderDto findOne(int blogId);
	
	SliderDto save(SliderDto sliderDto);
	
	SliderDto edit(SliderDto sliderDto);
	
	void remove(int blogId);

}
