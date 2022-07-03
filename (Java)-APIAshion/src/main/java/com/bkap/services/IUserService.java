package com.bkap.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.bkap.dto.UserDto;
import com.bkap.filter.UserFilter;

@Service
public interface IUserService extends UserDetailsService {

	List<UserDto> getAll();

	Page<UserDto> where(UserFilter filter);

	UserDto findOne(int userID);

	UserDto save(UserDto userDto);

	UserDto edit(UserDto userDto);

	void remove(int userID);

	UserDto findByFirstName(String firstName);
	
	UserDto findByEmail(String email);
}
