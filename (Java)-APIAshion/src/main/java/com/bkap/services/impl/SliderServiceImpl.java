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

import com.bkap.convert.SliderConvert;
import com.bkap.dto.SliderDto;
import com.bkap.entities.Slider;
import com.bkap.filter.SliderFilter;
import com.bkap.repositories.SliderRepository;
import com.bkap.services.ISliderService;

@Service
public class SliderServiceImpl implements ISliderService {
	
	@Autowired
	private SliderRepository sliderRepository;
	
	@Autowired
	private SliderConvert convert;

	@Override
	public List<SliderDto> getAll() {
		List<Slider> listSlider = sliderRepository.findAll();
		List<SliderDto> listSliderDto = new ArrayList<SliderDto>();
		listSlider.forEach(item -> listSliderDto.add(convert.toDto(item)) );
		return listSliderDto;
	}

	@Override
	public SliderDto findOne(int blogId) {
		return convert.toDto(sliderRepository.getOne(blogId));
	}

	@Override
	public SliderDto save(SliderDto sliderDto) {
		Slider slider = new Slider();
		if(sliderDto.getSliderId() != 0) {
			Slider oldSlider = sliderRepository.getOne(sliderDto.getSliderId());
			slider = sliderRepository.save(convert.toEntity(sliderDto, oldSlider));
		}
		else {
			slider = sliderRepository.save(convert.toEntity(sliderDto));
		}
		return convert.toDto(slider);
	}

	@Override
	public SliderDto edit(SliderDto sliderDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(int blogId) {
		sliderRepository.deleteById(blogId);
		
	}

	@Override
	public Page<SliderDto> where(SliderFilter filter) {
		Page<SliderDto> pageSlider = null;
		List<SliderDto> listSlidrDto = new ArrayList<>();
		Sort sort = Sort.by(filter.getSort()).ascending();
		if(!filter.isAsc()) {
			sort = Sort.by(filter.getSort()).descending();
		}
		PageRequest pageRequest = PageRequest.of(filter.getPage(),filter.getPageSize(),sort);
		List<Slider> listSlider= sliderRepository.findAll(new Specification<Slider>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Slider> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (filter.getSliderId() != 0) {
					predicates.add(criteriaBuilder.equal(root.get("sliderId"), filter.getSliderId()));
				}
				if(filter.getContent() != null && !filter.getContent().isEmpty()) {
					predicates.add(criteriaBuilder.like(root.get("content"), "%" + filter.getContent() + "%"));
				}
				if(filter.getTitle() != null && !filter.getTitle().isEmpty()) {
					predicates.add(criteriaBuilder.like(root.get("title"), "%" + filter.getTitle() + "%"));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
				
		});
		if (listSlider != null) {
			listSlider.forEach(item -> { listSlidrDto.add(convert.toDto(item));});
			Collections.reverse(listSlidrDto);
			List<SliderDto> output = new ArrayList<>();
			int total = listSlidrDto.size();
			int start = (int) pageRequest.getOffset();
			int end = Math.min((start + pageRequest.getPageSize()), total);
			if (start <= end) {
				output = listSlidrDto.subList(start, end);
			}
			pageSlider = new PageImpl<SliderDto>(output, pageRequest, total);
		}
		return pageSlider;
	}

}
