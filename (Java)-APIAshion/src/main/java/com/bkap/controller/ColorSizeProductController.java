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

import com.bkap.dto.ColorSizeProductDto;
import com.bkap.filter.SizeColorPrdFilter;
import com.bkap.services.impl.ColorSizeProductServiceImpl;
import com.google.gson.Gson;

@RestController
@RequestMapping("/api/colorSizeProduct")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ColorSizeProductController {

	@Autowired
	private ColorSizeProductServiceImpl service;

	@GetMapping
	private List<ColorSizeProductDto> getAll() {
		return service.getAll();
	}

	@GetMapping(value = "/{id}")
	private ColorSizeProductDto getById(@PathVariable("id") int id) {
		return service.findOne(id);
	}
	
	@GetMapping(value= "/paginations")
	public Page<ColorSizeProductDto> filter(@RequestParam("filter") String filter){
		SizeColorPrdFilter sc = new Gson().fromJson(filter,SizeColorPrdFilter.class);
		return service.where(sc);
	}

	@PostMapping
	private List<ColorSizeProductDto> save(@RequestBody List<ColorSizeProductDto> listColorSizeProductDto) {
		listColorSizeProductDto.forEach(Item -> System.out.println(Item.toString()));
		return service.save(listColorSizeProductDto);
	}
	
	@PutMapping(value = "/{id}")
	private ResponseEntity<ColorSizeProductDto> update(@RequestBody ColorSizeProductDto colorSizeProductDto,@PathVariable("id") int id) {
		colorSizeProductDto.setId(id);
		if (service.findOne(id).getId() != 0){
			return new ResponseEntity<ColorSizeProductDto>(service.edit(colorSizeProductDto), HttpStatus.OK);
		} else {
			return new ResponseEntity<ColorSizeProductDto>(new ColorSizeProductDto(),HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping(value = "/{id}")
	private ResponseEntity<ColorSizeProductDto> delete(@PathVariable("id") int id) {
		ColorSizeProductDto colorSizePrdDto = service.findOne(id);
		if (colorSizePrdDto.getId() != 0) {
			service.remove(id);
			return new ResponseEntity<ColorSizeProductDto>(colorSizePrdDto, HttpStatus.OK);
		}
		return new ResponseEntity<ColorSizeProductDto>(new ColorSizeProductDto(), HttpStatus.NOT_FOUND);
	}
	
}
