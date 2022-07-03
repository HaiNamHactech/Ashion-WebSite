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

import com.bkap.dto.ProductDto;
import com.bkap.filter.ProductFilter;
import com.bkap.services.impl.ProductServiceImpl;
import com.google.gson.Gson;

@RestController
@RequestMapping(value = "/api/product")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductController {
	
	

	@Autowired
	private ProductServiceImpl service;

	@GetMapping
	public List<ProductDto> getAll() {
		return service.getAll();
	}

	@GetMapping(value = "/{id}")
	public ProductDto getById(@PathVariable("id") int productId) {
		return service.findOne(productId);
	}

	@GetMapping(value = "/filter")
	public Page<ProductDto> filter(@RequestBody ProductFilter filter) {
		return service.where(filter);
	}

	@GetMapping(value = "/paginations")
	public Page<ProductDto> filter(@RequestParam("filter") String filter) {
		ProductFilter p = new Gson().fromJson(filter, ProductFilter.class);
		return service.where(p);
	}

	@PostMapping
	public ProductDto save(@RequestBody ProductDto productDto) {
		return service.save(productDto);
	}

	@PutMapping(value = "/{id}")
	private ResponseEntity<ProductDto> edit(@RequestBody ProductDto productDto, @PathVariable("id") int productId) {
		productDto.setProductId(productId);
		if (service.findOne(productId).getProductId() != 0) {
			return new ResponseEntity<ProductDto>(service.save(productDto), HttpStatus.OK);
		} else {
			return new ResponseEntity<ProductDto>(new ProductDto(), HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping(value = "/{id}")
	private ResponseEntity<ProductDto> detele(@PathVariable("id") int productId) {
		ProductDto productDto = service.findOne(productId);
		if (productDto.getProductId() != 0) {
			service.remove(productId);
			return new ResponseEntity<ProductDto>(productDto, HttpStatus.OK);
		}
		return new ResponseEntity<ProductDto>(new ProductDto(), HttpStatus.NOT_FOUND);
	}

	@GetMapping(value = "/productName")
	public ResponseEntity<ProductDto> getByName(@RequestParam("productName") String productName ) {		
		ProductDto productDto = service.getProductDtoByName(productName);
		if(productDto.getProductId() != 0) {
			return new ResponseEntity<ProductDto>(productDto, HttpStatus.OK);
		}
		return new ResponseEntity<ProductDto>(new ProductDto(), HttpStatus.NOT_FOUND);
	}
}
