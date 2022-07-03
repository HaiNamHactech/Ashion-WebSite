package com.bkap.convert;

import org.springframework.stereotype.Component;

import com.bkap.dto.RoleDto;
import com.bkap.entities.Role;

@Component
public class RoleConvert {
	
	public Role toEntity(RoleDto roleDto) {
		Role role = new Role();
		role.setName(roleDto.getName());
		return role;
	}
	
	public Role toEntity(RoleDto roleDto,Role role) {
		role.setName(roleDto.getName());
		return role;
	}
	
	public RoleDto toDto(Role role) {
		RoleDto roleDto = new RoleDto();
		roleDto.setRoleId(role.getRoleId());
		roleDto.setName(role.getName());
		return roleDto;
	}
}
