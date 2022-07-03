package com.bkap.convert;

import org.springframework.stereotype.Component;

import com.bkap.dto.UserDto;
import com.bkap.entities.Users;

@Component
public class UserConvert {
	
	public Users toEntity(UserDto userDto ) {
		Users user = new Users();
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setEmail(userDto.getEmail());
		user.setPassWord(userDto.getPassWord());
		user.setAvatar(userDto.getAvatar());
		user.setStatus(true);
		return user;
	}
	
	public Users toEntity(UserDto userDto,Users user) {
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setEmail(userDto.getEmail());
		user.setPassWord(userDto.getPassWord());
		user.setAvatar(userDto.getAvatar());
		user.setStatus(true);
		return user;
	}
	
	public UserDto toDto(Users user) {
		UserDto userDto = new UserDto();
		userDto.setUserId(user.getUserId());
		userDto.setFirstName(user.getFirstName());
		userDto.setLastName(user.getLastName());
		userDto.setAvatar(user.getAvatar());
		userDto.setEmail(user.getEmail());
		userDto.setPassWord(user.getPassWord());
		userDto.setStatus(user.getStatus());
		userDto.setCreateBy(user.getCreateBy());
		userDto.setModifieBy(user.getModifieBy());
		userDto.setCreateDate(user.getCreateDate());
		userDto.setModifieDate(user.getModifieDate());
		return userDto;
	}

}
