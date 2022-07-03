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

import com.bkap.convert.SizeConvert;
import com.bkap.dto.SizeDto;
import com.bkap.entities.Size;
import com.bkap.filter.SizeFilter;
import com.bkap.repositories.SizeRepository;
import com.bkap.services.ISizeService;

@Service
public class SizeServiceImpl implements ISizeService {
	
	@Autowired
	private SizeRepository sizeRepository;
	
	@Autowired
	private SizeConvert convert;

	@Override
	public List<SizeDto> getAll() {
		List<Size> listSize = sizeRepository.findAll();
		List<SizeDto> listSizeDto = new ArrayList<SizeDto>();
		listSize.forEach(item -> listSizeDto.add(convert.toDto(item)));
		return listSizeDto;
	}

	@Override
	public SizeDto findOne(int sizeId) {
		return convert.toDto(sizeRepository.getOne(sizeId));
	}

	@Override
	public SizeDto save(SizeDto sizeDto) {
		Size size = new Size();
		if(sizeDto.getSizeId() != 0) {
			Size oldSize = sizeRepository.getOne(sizeDto.getSizeId());
			size = sizeRepository.save(convert.toEntity(sizeDto, oldSize));
		}
		else {
			size = sizeRepository.save(convert.toEntity(sizeDto));
		}
		return convert.toDto(size);
	}

	@Override
	public SizeDto edit(SizeDto sizeDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(int sizeId) {
	sizeRepository.deleteById(sizeId);
		
	}

	@Override
	public Page<SizeDto> where(SizeFilter filter) {
		Page<SizeDto> pageSizeDto = null;
		List<SizeDto> listSizeDto = new ArrayList<>();
		Sort sort = Sort.by(filter.getSort()).ascending();
		if(!filter.isAsc()) {
			sort = Sort.by(filter.getSort()).descending();
		}
		PageRequest pageRequest = PageRequest.of(filter.getPage(),filter.getPageSize(),sort);
		List<Size> listSize= sizeRepository.findAll(new Specification<Size>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Size> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (filter.getSizeId() != 0) {
					predicates.add(criteriaBuilder.equal(root.get("sizeId"), filter.getSizeId()));
				}
				if(filter.getSizeName() != null && !filter.getSizeName().isEmpty()) {
					predicates.add(criteriaBuilder.like(root.get("sizeName"), "%" + filter.getSizeName() + "%"));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
			
		});
		if (listSize != null) {
			listSize.forEach(item -> { listSizeDto.add(convert.toDto(item));});
			Collections.reverse(listSizeDto);
			List<SizeDto> output = new ArrayList<>();
			int total = listSizeDto.size();
			int start = (int) pageRequest.getOffset();
			int end = Math.min((start + pageRequest.getPageSize()), total);
			if (start <= end) {
				output = listSizeDto.subList(start, end);
			}
			pageSizeDto = new PageImpl<SizeDto>(output, pageRequest, total);
		}
		return pageSizeDto;
	}

	@Override
	public SizeDto findBySizeName(String sizeName) {
		
		return convert.toDto(sizeRepository.findBySizeName(sizeName));
	}

}
