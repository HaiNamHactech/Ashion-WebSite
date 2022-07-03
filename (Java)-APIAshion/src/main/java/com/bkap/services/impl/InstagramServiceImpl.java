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

import com.bkap.convert.InstagramConvert;
import com.bkap.dto.InstagramDto;
import com.bkap.entities.Instagram;
import com.bkap.filter.InstagramFilter;
import com.bkap.repositories.InstagramRepository;
import com.bkap.services.IInstagramService;

@Service
public class InstagramServiceImpl implements IInstagramService {
	
	@Autowired
	private InstagramRepository instagramRepository;
	
	@Autowired
	private InstagramConvert convert;

	@Override
	public List<InstagramDto> getAll() {
		List<Instagram> listInstagram = instagramRepository.findAll();
		List<InstagramDto> listInstagramDto = new ArrayList<InstagramDto>();
		listInstagram.forEach(item -> listInstagramDto.add(convert.toDto(item)));
		return listInstagramDto;
	}

	@Override
	public InstagramDto findOne(int blogId) {
		return convert.toDto(instagramRepository.getOne(blogId));
	}

	@Override
	public InstagramDto save(InstagramDto instagramDto) {
		Instagram instagram = new Instagram();
		if(instagramDto.getInstaId() != 0) {
			Instagram oldInstagram = instagramRepository.getOne(instagramDto.getInstaId());
			instagram = instagramRepository.save(convert.toEntity(instagramDto, oldInstagram));		
		}
		else {
			instagram = instagramRepository.save(convert.toEntity(instagramDto));
		}
		return convert.toDto(instagram);
	}

	@Override
	public InstagramDto edit(InstagramDto instagramDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(int instaId) {
		instagramRepository.deleteById(instaId);
		
	}

	@Override
	public Page<InstagramDto> where(InstagramFilter filter) {
		Page<InstagramDto> pageInstagram = null;
		List<InstagramDto> listInstaDto = new ArrayList<>();
		Sort sort = Sort.by(filter.getSort()).ascending();
		if(!filter.isAsc()) {
			sort = Sort.by(filter.getSort()).descending();
		}
		PageRequest pageRequest = PageRequest.of(filter.getPage(),filter.getPageSize(),sort);
		List<Instagram> listInstagram = instagramRepository.findAll(new Specification<Instagram>() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public Predicate toPredicate(Root<Instagram> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (filter.getInstaId() != 0) {
					predicates.add(criteriaBuilder.equal(root.get("instaId"), filter.getInstaId()));
				}
				if (filter.getDisplayNo() != 0) {
					predicates.add(criteriaBuilder.equal(root.get("displayNo"), filter.getDisplayNo()));
				}	
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		});
		
		if (listInstagram != null) {
			listInstagram.forEach(item -> { listInstaDto.add(convert.toDto(item));});
			Collections.reverse(listInstaDto);
			List<InstagramDto> output = new ArrayList<>();
			int total = listInstaDto.size();
			int start = (int) pageRequest.getOffset();
			int end = Math.min((start + pageRequest.getPageSize()), total);
			if (start <= end) {
				output = listInstaDto.subList(start, end);
			}
			pageInstagram = new PageImpl<InstagramDto>(output, pageRequest, total);
		}
		return pageInstagram;
	}
	

	

}
