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

import com.bkap.dto.InstagramDto;
import com.bkap.filter.InstagramFilter;
import com.bkap.services.impl.InstagramServiceImpl;
import com.google.gson.Gson;

@RestController
@RequestMapping(value ="/api/instagram")
@CrossOrigin(origins="*",allowedHeaders="*") 
public class InstagramController {
	
	@Autowired
	private InstagramServiceImpl service;
	
	@GetMapping
	public List<InstagramDto> getAll() {
		return service.getAll();
	}
	
	@GetMapping(value = "/{id}")
	public InstagramDto getById (@PathVariable("id") int instaId) {
		return service.findOne(instaId);
	}
	
	@GetMapping(value= "/paginations")
	public Page<InstagramDto> filter(@RequestParam("filter") String filter){
		InstagramFilter i = new Gson().fromJson(filter,InstagramFilter.class);
		return service.where(i);
	}
	
	@PostMapping
	public InstagramDto save(@RequestBody InstagramDto instagramDto) {
		return service.save(instagramDto);
	}
	
	@PutMapping(value = "/{id}")
	private ResponseEntity<InstagramDto> edit(@RequestBody InstagramDto instagramDto,@PathVariable("id") int instaId) {
		instagramDto.setInstaId(instaId);
		if (service.findOne(instaId).getInstaId() != 0) {
			return new ResponseEntity<InstagramDto>(service.save(instagramDto), HttpStatus.OK);
		} else {
			return new ResponseEntity<InstagramDto>(new InstagramDto(), HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping(value = "/{id}")
	private ResponseEntity<InstagramDto> detele(@PathVariable("id") int instaId){
		InstagramDto instagramDto = service.findOne(instaId);
		if(instagramDto.getInstaId() != 0) {
			service.remove(instaId);
			return new ResponseEntity<InstagramDto>(instagramDto, HttpStatus.OK);
		}
		return new ResponseEntity<InstagramDto>(new InstagramDto(), HttpStatus.NOT_FOUND);
	}
	
}
