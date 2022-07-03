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

import com.bkap.dto.CategoryBlogDto;
import com.bkap.filter.CategoryBlogFilter;
import com.bkap.services.impl.CategoryBlogServiceImpl;
import com.google.gson.Gson;

@RestController
@RequestMapping("/api/categoryBlog")
@CrossOrigin(origins="*",allowedHeaders="*") 
public class CategoryBlogController {
	
	@Autowired
	private CategoryBlogServiceImpl categoryService;
	
	@GetMapping
	private List<CategoryBlogDto> getAll(){
		return categoryService.getAll();
	}
	
	@GetMapping(value = "/{id}")
	private CategoryBlogDto getById(@PathVariable("id") int cateId) {
		return categoryService.findOne(cateId);
	}
	
	@GetMapping(value= "/paginations")
	public Page<CategoryBlogDto> filter(@RequestParam("filter") String filter){
		CategoryBlogFilter p = new Gson().fromJson(filter,CategoryBlogFilter.class);
		return categoryService.where(p);
	}
	
	@GetMapping(value= "/categoryName")
	public ResponseEntity<CategoryBlogDto> findByCatgoryName(@RequestParam("categoryName") String categoryName){
		CategoryBlogDto categoryBlogDto = categoryService.findByName(categoryName);
		return categoryDto(categoryBlogDto);
	}
	
	@GetMapping(value= "/slug")
	public ResponseEntity<CategoryBlogDto> findBySlug(@RequestParam("slug") String slug){
		CategoryBlogDto categoryBlogDto = categoryService.findBySlug(slug);
		return categoryDto(categoryBlogDto);
	}
	
	public ResponseEntity<CategoryBlogDto> categoryDto(CategoryBlogDto categoryBlogDto){
		if(categoryBlogDto.getCategoryId() != 0) {
			return new ResponseEntity<CategoryBlogDto>(categoryBlogDto, HttpStatus.OK);
		}
		return new ResponseEntity<CategoryBlogDto>(new CategoryBlogDto(), HttpStatus.NOT_FOUND);
	}
	@PutMapping(value ="/{id}")
	private ResponseEntity<CategoryBlogDto> edit(@RequestBody CategoryBlogDto categoryBlogDto,@PathVariable("id") int cateId){
		categoryBlogDto.setCategoryId(cateId);
		if(categoryService.findOne(cateId).getCategoryId() != 0 ) {
			return new ResponseEntity<CategoryBlogDto>(categoryService.save(categoryBlogDto),HttpStatus.OK);
		}
		return new ResponseEntity<CategoryBlogDto>(new CategoryBlogDto(), HttpStatus.NOT_FOUND);
	}
	
	@PostMapping
	private CategoryBlogDto save(@RequestBody CategoryBlogDto categoryBlogDto){
		return categoryService.save(categoryBlogDto);
	}
	
	@DeleteMapping(value = "/{id}")
	private ResponseEntity<CategoryBlogDto> delete(@PathVariable("id") int cateId){
		CategoryBlogDto category = categoryService.findOne(cateId);
		if(category.getCategoryId() != 0 ) {
			categoryService.remove(cateId);
			return new ResponseEntity<CategoryBlogDto>(category, HttpStatus.OK);
		}
		return new ResponseEntity<CategoryBlogDto>(new CategoryBlogDto(), HttpStatus.NOT_FOUND);
	}

}
