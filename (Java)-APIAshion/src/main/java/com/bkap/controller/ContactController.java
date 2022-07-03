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

import com.bkap.dto.ContactDto;
import com.bkap.filter.ContactFilter;
import com.bkap.services.impl.ContactServiceImpl;
import com.google.gson.Gson;

@RestController
@RequestMapping(value ="/api/contact")
@CrossOrigin(origins="*",allowedHeaders="*") 
public class ContactController {
	
	@Autowired
	ContactServiceImpl service;
	
	@GetMapping
	public List<ContactDto> getAll() {
		return service.getAll();
	}
	
	@GetMapping(value = "/{id}")
	public ContactDto getById (@PathVariable("id") int contactId) {
		return service.findOne(contactId);
	}
	
	@GetMapping(value= "/paginations")
	public Page<ContactDto> filter(@RequestParam("filter") String filter){
		ContactFilter c = new Gson().fromJson(filter,ContactFilter.class);
		return service.where(c);
	}
	
	@PostMapping
	public ContactDto save(@RequestBody ContactDto contactDto) {
		return service.save(contactDto);
	}
	
	@PutMapping(value = "/{id}")
	private ResponseEntity<ContactDto> edit(@RequestBody ContactDto contactDto,@PathVariable("id") int contactId) {
		contactDto.setContactId(contactId);
		if (service.findOne(contactId).getContactId() != 0) {
			return new ResponseEntity<ContactDto>(service.save(contactDto), HttpStatus.OK);
		} else {
			return new ResponseEntity<ContactDto>(new ContactDto(), HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping(value = "/{id}")
	private ResponseEntity<ContactDto> detele(@PathVariable("id") int contactId){
		ContactDto contactDto = service.findOne(contactId);
		if(contactDto.getContactId() != 0) {
			service.remove(contactId);
			return new ResponseEntity<ContactDto>(contactDto, HttpStatus.OK);
		}
		return new ResponseEntity<ContactDto>(new ContactDto(), HttpStatus.NOT_FOUND);
	}
	

}
