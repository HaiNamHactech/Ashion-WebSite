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

import com.bkap.convert.UserRoleConvert;
import com.bkap.dto.UserRoleDto;
import com.bkap.entities.UserRole;
import com.bkap.filter.UserRoleFilter;
import com.bkap.repositories.UserRoleRepository;
import com.bkap.services.IUserRoleService;

@Service
public class UserRoleServiceImpl implements IUserRoleService {
	
	@Autowired
	private UserRoleRepository userRoleRepository ;
	
	@Autowired
	private UserRoleConvert convert;

	@Override
	public List<UserRoleDto> getAll() {
		List<UserRole> listUserRole = userRoleRepository.findAll();
		List<UserRoleDto> listUserRoleDto = new ArrayList<UserRoleDto>();
		listUserRole.forEach(item -> listUserRoleDto.add(convert.toDto(item)));
		return listUserRoleDto;
	}

	@Override
	public UserRoleDto findOne(int userRoleId) {
		return convert.toDto(userRoleRepository.getOne(userRoleId));
	}

	@Override
	public UserRoleDto save(UserRoleDto userRoleDto) {
		UserRole userRole = new UserRole();
		if(userRoleDto.getId() != 0) {
			UserRole oldUserRole = userRoleRepository.getOne(userRoleDto.getId());
			userRole = userRoleRepository.save(convert.toEntity(userRoleDto, oldUserRole));
		}
		else {
			userRole = userRoleRepository.save(convert.toEntity(userRoleDto));
		}
		return convert.toDto(userRole);
	}

	@Override
	public UserRoleDto edit(UserRoleDto userRoleDto) {
		return null;
	}

	@Override
	public void remove(int userRoleId) {
		userRoleRepository.deleteById(userRoleId);
	}

	@Override
	public Page<UserRoleDto> where(UserRoleFilter filter) {
		Page<UserRoleDto> pageUserRole = null;
		List<UserRoleDto> listUserRoleDto = new ArrayList<>();
		Sort sort = Sort.by(filter.getSort()).ascending();
		if(!filter.isAsc()) {
			sort = Sort.by(filter.getSort()).descending();
		}
		PageRequest pageRequest = PageRequest.of(filter.getPage(),filter.getPageSize(),sort);
		List<UserRole> listUserRole = userRoleRepository.findAll(new Specification<UserRole>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<UserRole> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (filter.getId()!= 0) {
					predicates.add(criteriaBuilder.equal(root.get("id"), filter.getId()));
				}	
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		});
		if (listUserRole != null) {
			listUserRole.forEach(item -> { listUserRoleDto.add(convert.toDto(item));});
			Collections.reverse(listUserRoleDto);
			List<UserRoleDto> output = new ArrayList<>();
			int total = listUserRoleDto.size();
			int start = (int) pageRequest.getOffset();
			int end = Math.min((start + pageRequest.getPageSize()), total);
			if (start <= end) {
				output = listUserRoleDto.subList(start, end);
			}
			pageUserRole = new PageImpl<UserRoleDto>(output, pageRequest, total);
		}
		return pageUserRole;
	}

}
