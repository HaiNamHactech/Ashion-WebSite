package com.bkap.convert;

import org.springframework.stereotype.Component;

import com.bkap.dto.SliderDto;
import com.bkap.entities.Slider;

@Component
public class SliderConvert {
	
	public Slider toEntity(SliderDto sliderDto) {
		Slider slider = new Slider();
		slider.setTitle(sliderDto.getTitle());
		slider.setContent(sliderDto.getContent());
		slider.setDisplayNo(sliderDto.getDisplayNo());
		return slider;
	}
	
	public Slider toEntity(SliderDto sliderDto, Slider slider) {
		slider.setTitle(sliderDto.getTitle());
		slider.setContent(sliderDto.getContent());
		slider.setDisplayNo(sliderDto.getDisplayNo());
		return slider;
	}
	
	public SliderDto toDto(Slider slider) {
		SliderDto sliderDto = new SliderDto();
		sliderDto.setSliderId(slider.getSliderId());
		sliderDto.setTitle(slider.getTitle());
		sliderDto.setDisplayNo(slider.getDisplayNo());
		sliderDto.setContent(slider.getContent());
		return sliderDto;
	}

}
