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

import com.bkap.dto.BlogDto;
import com.bkap.filter.BlogFilter;
import com.bkap.services.impl.BlogServiceImpl;
import com.google.gson.Gson;

@RestController
@RequestMapping("/api/blog")
@CrossOrigin(origins="*",allowedHeaders="*") 
public class BlogController {
	
	@Autowired
	private BlogServiceImpl blogServiceImpl;
	
	@GetMapping
	public List<BlogDto> getAll() {
		return blogServiceImpl.getAll();
	}
	
	@GetMapping(value = "/{id}")
	public BlogDto getBlogById (@PathVariable("id") int blogId) {
		return blogServiceImpl.findOne(blogId);
	}
	
	@GetMapping(value= "/paginations")
	public Page<BlogDto> filter(@RequestParam("filter") String filter){
		BlogFilter b = new Gson().fromJson(filter,BlogFilter.class);
		return blogServiceImpl.where(b);
	}
	
	@PostMapping
	public BlogDto save(@RequestBody BlogDto blogDto) {
		return blogServiceImpl.save(blogDto);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<BlogDto> edit(@RequestBody BlogDto blogDto,@PathVariable("id") int blogId){
		blogDto.setBlogId(blogId);
		if(blogServiceImpl.findOne(blogId).getBlogId() != 0 ) {
			return new ResponseEntity<BlogDto>(blogServiceImpl.save(blogDto),HttpStatus.OK);
		}
		return new ResponseEntity<BlogDto>(new BlogDto(), HttpStatus.NOT_FOUND);
	}
	
	
	@DeleteMapping(value ="/{id}")
	public ResponseEntity<BlogDto> delete(@PathVariable("id") int blogId) {
		BlogDto blogDto = blogServiceImpl.findOne(blogId);
		if(blogDto.getBlogId() != 0) {
			blogServiceImpl.remove(blogId);
			return new ResponseEntity<BlogDto>(blogDto,HttpStatus.OK);
		}
		return new ResponseEntity<BlogDto>(new BlogDto(), HttpStatus.NOT_FOUND);
	}

}
