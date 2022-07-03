package com.bkap.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bkap.dto.UserRoleDto;
import com.bkap.filter.UserRoleFilter;
import com.bkap.services.impl.UserRoleServiceImpl;
import com.google.gson.Gson;

@RestController
@RequestMapping(value ="/api/userRole")
@CrossOrigin(origins="*",allowedHeaders="*") 
public class UserRoleController  {
	
	@Autowired
	private UserRoleServiceImpl service;
	
	@GetMapping
	public List<UserRoleDto> getAll(){
		return service.getAll();
	}
	
	@GetMapping(value = "/{id}")
	public UserRoleDto getById(@PathVariable("id") int userRoleId) {
		return service.findOne(userRoleId);
	}
	
	@GetMapping(value= "/paginations")
	public Page<UserRoleDto> filter(@RequestParam("filter") String filter){
		UserRoleFilter ur = new Gson().fromJson(filter,UserRoleFilter.class);
		return service.where(ur);
	}
	
	@PostMapping
	public UserRoleDto save(@RequestBody UserRoleDto userRoleDto) {
		return service.save(userRoleDto);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<UserRoleDto> edit(@RequestBody UserRoleDto userRoleDto,@PathVariable("id") int userRoleId) {
		userRoleDto.setId(userRoleId);
		if(service.findOne(userRoleId).getId() != 0) {
			return new ResponseEntity<UserRoleDto>(service.save(userRoleDto),HttpStatus.OK);
		}
		return new ResponseEntity<UserRoleDto>(new UserRoleDto(),HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<UserRoleDto> delete(@PathVariable("id") int userRoleId){
		UserRoleDto userRoleDto = service.findOne(userRoleId);
		if(userRoleDto.getId() != 0) {
			service.remove(userRoleId);
			return new ResponseEntity<UserRoleDto>(userRoleDto, HttpStatus.OK);
		}
		return new ResponseEntity<UserRoleDto>(new UserRoleDto(), HttpStatus.NOT_FOUND);
	}
	


}
