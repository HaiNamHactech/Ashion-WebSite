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

import com.bkap.convert.ColorConvert;
import com.bkap.dto.ColorDto;
import com.bkap.entities.Color;
import com.bkap.filter.ColorFilter;
import com.bkap.repositories.ColorRepository;
import com.bkap.services.IColorService;

@Service
public class ColorServiceImpl implements IColorService {

	@Autowired
	ColorConvert convert;

	@Autowired
	ColorRepository colorRepository;

	@Override
	public List<ColorDto> getAll() {
		List<Color> listColor = colorRepository.findAll();
		List<ColorDto> listColorDto = new ArrayList<ColorDto>();
		listColor.forEach(item -> listColorDto.add(convert.toDto(item)));
		return listColorDto;
	}

	@Override
	public ColorDto save(ColorDto colorDto) {
		Color color = new Color();
		if (colorDto.getColorId() != 0) {
			Color oldColor = colorRepository.getOne(colorDto.getColorId());
			color = colorRepository.save(convert.toEntity(colorDto, oldColor));
		} else {
			color = colorRepository.save(convert.toEntity(colorDto));
		}
		return convert.toDto(color);
	}

	@Override
	public ColorDto edit(ColorDto colorDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ColorDto findOne(int colorId) {
		return convert.toDto(colorRepository.getOne(colorId));
	}

	@Override
	public void remove(int colorId) {
		colorRepository.deleteById(colorId);

	}

	@Override
	public Page<ColorDto> where(ColorFilter filter) {
		Page<ColorDto> pageColor = null;
		List<ColorDto> listColorDto = new ArrayList<>();
		Sort sort = Sort.by(filter.getSort()).ascending();
		if (!filter.isAsc()) {
			sort = Sort.by(filter.getSort()).descending();
		}
		PageRequest pageRequest = PageRequest.of(filter.getPage(), filter.getPageSize(), sort);
		List<Color> listColor = colorRepository.findAll(new Specification<Color>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Color> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (filter.getColorId() != 0) {
					predicates.add(criteriaBuilder.equal(root.get("colorId"), filter.getColorId()));
				}
				if (filter.getColorName() != null && !filter.getColorName().isEmpty()) {
					predicates.add(criteriaBuilder.like(root.get("colorName"), "%" + filter.getColorName() + "%"));
				}
				if (filter.getCode() != null && !filter.getCode().isEmpty()) {
					predicates.add(criteriaBuilder.like(root.get("code"), "%" + filter.getCode() + "%"));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		});

		if (listColor != null) {
			listColor.forEach(item -> {
				listColorDto.add(convert.toDto(item));
			});
			Collections.reverse(listColorDto);
			List<ColorDto> output = new ArrayList<>();
			int total = listColorDto.size();
			int start = (int) pageRequest.getOffset();
			int end = Math.min((start + pageRequest.getPageSize()), total);
			if (start <= end) {
				output = listColorDto.subList(start, end);
			}
			pageColor = new PageImpl<ColorDto>(output, pageRequest, total);
		}
		return pageColor;
	}

	@Override
	public ColorDto findByCode(String code) {
		return convert.toDto(colorRepository.findByCode(code));
	}

	@Override
	public ColorDto findByName(String name) {
		return convert.toDto(colorRepository.findByColorName(name));
	}

}
