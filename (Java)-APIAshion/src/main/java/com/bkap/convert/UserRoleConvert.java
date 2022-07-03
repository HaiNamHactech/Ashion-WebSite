package com.bkap.convert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bkap.dto.UserRoleDto;
import com.bkap.entities.UserRole;
import com.bkap.repositories.RoleRepository;
import com.bkap.repositories.UserRepository;

@Component
public class UserRoleConvert {
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public UserRole toEntity(UserRoleDto userRoleDto ) {
		UserRole userRole = new UserRole();
		userRole.setRole(roleRepository.getOne(userRoleDto.getRoleId()));
		userRole.setUserR(userRepository.getOne(userRoleDto.getUserId()));
		return userRole;
	}
	
	public UserRole toEntity(UserRoleDto userRoleDto,UserRole userRole ) {
		userRole.setRole(roleRepository.getOne(userRoleDto.getRoleId()));
		userRole.setUserR(userRepository.getOne(userRoleDto.getUserId()));
		return userRole;
	}
	
	public UserRoleDto toDto(UserRole userRole) {
		UserRoleDto userRoleDto = new UserRoleDto(); 
		userRoleDto.setId(userRole.getId());
		userRoleDto.setRoleId(userRole.getRole().getRoleId());
		userRoleDto.setRoleName(userRole.getRole().getName());
		userRoleDto.setUserId(userRole.getUserR().getUserId());
		userRoleDto.setUserName(userRole.getUserR().getFirstName());
		return userRoleDto;
	}
	
	

}
