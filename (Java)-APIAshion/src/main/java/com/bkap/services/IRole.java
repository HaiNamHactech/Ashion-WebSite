package com.bkap.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.bkap.dto.RoleDto;
import com.bkap.filter.RoleFilter;

@Service
public interface IRole  {
	
	List<RoleDto> getAll();
	
	Page<RoleDto> where(RoleFilter filter);
	
	RoleDto findOne(int blogId);
	
	RoleDto save(RoleDto roleDto);
	
	RoleDto edit(RoleDto roleDto);
	
	RoleDto findByName(String roleName);
	
	void remove(int roleId);

}
