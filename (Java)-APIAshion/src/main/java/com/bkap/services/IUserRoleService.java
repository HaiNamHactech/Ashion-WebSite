package com.bkap.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.bkap.dto.UserRoleDto;
import com.bkap.filter.UserRoleFilter;

@Service
public interface IUserRoleService {
	
	List<UserRoleDto> getAll();
	
	Page<UserRoleDto> where(UserRoleFilter filter);
	
	UserRoleDto findOne(int userRoleId);
	
	UserRoleDto save(UserRoleDto userRoleDto);
	
	UserRoleDto edit(UserRoleDto userRoleDto);
	
	void remove(int userRoleId);
}
