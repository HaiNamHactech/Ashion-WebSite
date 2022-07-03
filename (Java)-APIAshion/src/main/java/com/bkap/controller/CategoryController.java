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

import com.bkap.dto.CategoryProductDto;
import com.bkap.filter.CategoryProductFilter;
import com.bkap.services.impl.CategoryServiceImpl;
import com.google.gson.Gson;

@RestController
@RequestMapping("/api/categoryPrd")
@CrossOrigin(origins="*",allowedHeaders="*") 
public class CategoryController {

	@Autowired
	private CategoryServiceImpl categoryService;
	
	@GetMapping
	private List<CategoryProductDto> getAll(){
		return categoryService.getAll();
	}
	
	@GetMapping(value = "/{id}")
	private CategoryProductDto getById(@PathVariable("id") int cateId){
		return categoryService.findOne(cateId);
	}
	
	@PostMapping
	private CategoryProductDto save(@RequestBody CategoryProductDto categoryProductDto) {
		return categoryService.save(categoryProductDto);
	}
	
	
	@PutMapping(value = "/{id}")
	private ResponseEntity<CategoryProductDto> edit(@RequestBody CategoryProductDto categoryProductDto,@PathVariable("id") int cateId) {
		categoryProductDto.setCategoryId(cateId);
		CategoryProductDto categoryPrdDto = categoryService.findOne(cateId);
		if (categoryPrdDto.getCategoryId() != 0) {
			return new ResponseEntity<CategoryProductDto>(categoryService.save(categoryProductDto), HttpStatus.OK);
		} else {
			return new ResponseEntity<CategoryProductDto>(new CategoryProductDto(), HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping(value = "/{id}")
	private ResponseEntity<CategoryProductDto>  delete(@PathVariable("id") int cateId){
		CategoryProductDto category = categoryService.findOne(cateId);
		if(category.getCategoryId() != 0 ) {
			categoryService.remove(cateId);
			return new ResponseEntity<CategoryProductDto>(category, HttpStatus.OK);
		}
		return new ResponseEntity<CategoryProductDto>(new CategoryProductDto(), HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(value= "/paginations")
	public Page<CategoryProductDto> filter(@RequestParam("filter") String filter){
		CategoryProductFilter p = new Gson().fromJson(filter,CategoryProductFilter.class);
		return categoryService.where(p);
	}
	
	@GetMapping(value= "/categoryName")
	public ResponseEntity<CategoryProductDto> findByCatgoryName(@RequestParam("categoryName") String categoryName){
		CategoryProductDto categoryProductDto = categoryService.findByCategoryName(categoryName);
		return categoryProductDto(categoryProductDto);
	}
	
	@GetMapping(value= "/slug")
	public ResponseEntity<CategoryProductDto> findBySlug(@RequestParam("slug") String slug){
		CategoryProductDto categoryProductDto = categoryService.findBySlug(slug);
		return categoryProductDto(categoryProductDto);
	}
	
	@GetMapping(value= "/displayNo")
	public ResponseEntity<CategoryProductDto> findByDisplayNo(@RequestParam("displayNo") int displayNo){
		CategoryProductDto categoryProductDto = categoryService.fintByDisplayNo(displayNo);
		return categoryProductDto(categoryProductDto);
	}
	
	public ResponseEntity<CategoryProductDto> categoryProductDto(CategoryProductDto categoryProductDto){
		if(categoryProductDto.getCategoryId() != 0) {
			return new ResponseEntity<CategoryProductDto>(categoryProductDto, HttpStatus.OK);
		}
		return new ResponseEntity<CategoryProductDto>(new CategoryProductDto(), HttpStatus.NOT_FOUND);
	}
	
	
	
}
