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

import com.bkap.convert.RoleConvert;
import com.bkap.dto.RoleDto;
import com.bkap.entities.Role;
import com.bkap.filter.RoleFilter;
import com.bkap.repositories.RoleRepository;
import com.bkap.services.IRole;

@Service
public class RoleServiceImpl implements IRole {
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private RoleConvert convert;

	@Override
	public List<RoleDto> getAll() {
		List<Role> listRole = roleRepository.findAll();
		List<RoleDto> listRoleDto = new ArrayList<RoleDto>();
		listRole.forEach(item -> listRoleDto.add(convert.toDto(item)));
		return listRoleDto;
	}

	@Override
	public RoleDto findOne(int roleId) {
		return convert.toDto(roleRepository.getOne(roleId));
	}

	@Override
	public RoleDto save(RoleDto roleDto) {
		Role role = new Role();
		if(roleDto.getRoleId() != 0 ) {
			Role oldRole = roleRepository.getOne(roleDto.getRoleId());
			System.out.println(oldRole.getName());
			role = roleRepository.save(convert.toEntity(roleDto, oldRole));
		}
		else {
			role = roleRepository.save(convert.toEntity(roleDto));
		}
		return convert.toDto(role);
	}

	@Override
	public RoleDto edit(RoleDto roleDto) {
		return null;
	}

	@Override
	public void remove(int roleId) {
		roleRepository.deleteById(roleId);
		
	}
	
	

	@Override
	public Page<RoleDto> where(RoleFilter filter) {
		Page<RoleDto> pageRole = null;
		List<RoleDto> listRoleDto = new ArrayList<>();
		Sort sort = Sort.by(filter.getSort()).ascending();
		if(!filter.isAsc()) {
			sort = Sort.by(filter.getSort()).descending();
		}
		PageRequest pageRequest = PageRequest.of(filter.getPage(),filter.getPageSize(),sort);
		List<Role> listRole = roleRepository.findAll(new Specification<Role>() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (filter.getRoleId() != 0) {
					predicates.add(criteriaBuilder.equal(root.get("roleId"), filter.getRoleId()));
				}
				if (filter.getName() != null && !filter.getName().isEmpty()) {
					predicates.add(criteriaBuilder.like(root.get("name"), "%" + filter.getName() + "%"));
				}	
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		});
		
		if (listRole != null) {
			listRole.forEach(item -> { listRoleDto.add(convert.toDto(item));});
			Collections.reverse(listRoleDto);
			List<RoleDto> output = new ArrayList<>();
			int total = listRoleDto.size();
			int start = (int) pageRequest.getOffset();
			int end = Math.min((start + pageRequest.getPageSize()), total);
			if (start <= end) {
				output = listRoleDto.subList(start, end);
			}
			pageRole = new PageImpl<RoleDto>(output, pageRequest, total);
		}
		return pageRole;
	}

	@Override
	public RoleDto findByName(String roleName) {		
		return convert.toDto(roleRepository.findByName(roleName));
	}

}
