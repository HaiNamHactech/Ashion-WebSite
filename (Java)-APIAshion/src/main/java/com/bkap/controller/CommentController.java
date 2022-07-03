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

import com.bkap.dto.CommentDto;
import com.bkap.filter.CommentFilter;
import com.bkap.services.impl.CommentServiceImpl;
import com.google.gson.Gson;

@RestController
@RequestMapping(value ="/api/comment")
@CrossOrigin(origins="*",allowedHeaders="*") 
public class CommentController {
	
	@Autowired
	CommentServiceImpl service;
	
	@GetMapping
	public List<CommentDto> getAll() {
		return service.getAll();
	}
	
	@GetMapping(value = "/{id}")
	public CommentDto getBlogById (@PathVariable("id") int commentId) {
		return service.findOne(commentId);
	}
	
	@GetMapping(value= "/paginations")
	public Page<CommentDto> filter(@RequestParam("filter") String filter){
		CommentFilter c = new Gson().fromJson(filter,CommentFilter.class);
		return service.where(c);
	}
	
	@PostMapping
	public CommentDto save(@RequestBody CommentDto commentDto) {
		return service.save(commentDto);
	}
	
	@PutMapping(value = "/{id}")
	private ResponseEntity<CommentDto> edit(@RequestBody CommentDto commentDto,@PathVariable("id") int commentId) {
		commentDto.setCommentId(commentId);
		if (service.findOne(commentId).getCommentId() != 0) {
			return new ResponseEntity<CommentDto>(service.save(commentDto), HttpStatus.OK);
		} else {
			return new ResponseEntity<CommentDto>(new CommentDto(), HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping(value = "/{id}")
	private ResponseEntity<CommentDto> detele(@PathVariable("id") int commentId){
		CommentDto commentDto = service.findOne(commentId);
		if(commentDto.getBlogId() != 0) {
			service.remove(commentId);
			return new ResponseEntity<CommentDto>(commentDto, HttpStatus.OK);
		}
		return new ResponseEntity<CommentDto>(new CommentDto(), HttpStatus.NOT_FOUND);
	}
	
	
	

	

}
