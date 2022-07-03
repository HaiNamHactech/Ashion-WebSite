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

import com.bkap.dto.RoleDto;
import com.bkap.filter.RoleFilter;
import com.bkap.services.impl.RoleServiceImpl;
import com.google.gson.Gson;

@RestController
@RequestMapping(value ="/api/role")
@CrossOrigin(origins="*",allowedHeaders="*") 
public class RoleController {
	
	@Autowired
	RoleServiceImpl service;
	
	@GetMapping
	public List<RoleDto> getAll() {
		return service.getAll();
	}
	
	@GetMapping(value = "/{id}")
	public RoleDto getById (@PathVariable("id") int roleId) {
		return service.findOne(roleId);
	}
	
	@GetMapping(value= "/paginations")
	public Page<RoleDto> filter(@RequestParam("filter") String filter){
		System.out.println(filter);
		RoleFilter rf = new Gson().fromJson(filter,RoleFilter.class);
		return service.where(rf);
	}
	
	@PostMapping
	public RoleDto save(@RequestBody RoleDto roleDto) {
		return service.save(roleDto);
	}
	
	@PutMapping(value = "/{id}")
	private ResponseEntity<RoleDto> edit(@RequestBody RoleDto roleDto,@PathVariable("id") int roleId) {
		roleDto.setRoleId(roleId);
		if(service.findOne(roleId).getRoleId()!= 0) {
			return new ResponseEntity<RoleDto>(service.save(roleDto), HttpStatus.OK);
		} else {
			return new ResponseEntity<RoleDto>(new RoleDto(), HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping(value = "/{id}")
	private ResponseEntity<RoleDto> detele(@PathVariable("id") int roleId){
		RoleDto roleDto = service.findOne(roleId);
		if(roleDto.getRoleId() != 0) {
			service.remove(roleId);
			return new ResponseEntity<RoleDto>(roleDto, HttpStatus.OK);
		}
		return new ResponseEntity<RoleDto>(new RoleDto(), HttpStatus.NOT_FOUND);
	}
	
	
	@GetMapping(value = "/name")
	public ResponseEntity<RoleDto> findByColorName(@RequestParam("name") String roleName) {
		RoleDto roleDto = service.findByName(roleName);
		if (roleDto.getRoleId() != 0) {
			return new ResponseEntity<RoleDto>(roleDto, HttpStatus.OK);
		}
		return new ResponseEntity<RoleDto>(new RoleDto(), HttpStatus.NOT_FOUND);
	}

}
