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

import com.bkap.dto.ProductImageDto;
import com.bkap.filter.ImageProductFilter;
import com.bkap.services.impl.ProductImageServiceImpl;
import com.google.gson.Gson;

@RestController
@RequestMapping(value ="/api/productImage")
@CrossOrigin(origins="*",allowedHeaders="*") 
public class ProductImageController {
	
	@Autowired
	private ProductImageServiceImpl service;

	@GetMapping
	public List<ProductImageDto> getAll() {
		return service.getAll();
	}
	
	@GetMapping(value = "/{id}")
	public ProductImageDto getById (@PathVariable("id") int productImageId) {
		return service.findOne(productImageId);
	}
	
	@GetMapping(value= "/paginations")
	public Page<ProductImageDto> filter(@RequestParam("filter") String filter){
		ImageProductFilter img = new Gson().fromJson(filter,ImageProductFilter.class);
		return service.where(img);
	}
	
	@PostMapping
	public ProductImageDto save(@RequestBody ProductImageDto productImageDto) {
		return service.save(productImageDto);
	}
	
	@PutMapping(value = "/{id}")
	private ResponseEntity<ProductImageDto> edit(@RequestBody ProductImageDto productImageDto,@PathVariable("id") int productImageId) {
		productImageDto.setImgId(productImageId);
		if (service.findOne(productImageId).getImgId() != 0) {
			return new ResponseEntity<ProductImageDto>(service.save(productImageDto), HttpStatus.OK);
		} else {
			return new ResponseEntity<ProductImageDto>(new ProductImageDto(), HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping(value = "/{id}")
	private ResponseEntity<ProductImageDto> detele(@PathVariable("id") int productImageId){
		ProductImageDto productImageDto = service.findOne(productImageId);
		if(productImageDto.getImgId() != 0) {
			service.remove(productImageId);
			return new ResponseEntity<ProductImageDto>(productImageDto, HttpStatus.OK);
		}
		return new ResponseEntity<ProductImageDto>(new ProductImageDto(), HttpStatus.NOT_FOUND);
	}
}
