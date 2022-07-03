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

import com.bkap.dto.ColorDto;
import com.bkap.filter.ColorFilter;
import com.bkap.services.impl.ColorServiceImpl;
import com.google.gson.Gson;

@RestController
@RequestMapping("/api/color")
@CrossOrigin(origins="*",allowedHeaders="*")
public class ColorController {
	
	@Autowired
	private ColorServiceImpl service;
	
	@GetMapping
	private List<ColorDto> getAll(){
		return service.getAll();
	}
	
	@GetMapping(value = "/{id}")
	private ColorDto getById(@PathVariable("id") int colorId){
		return service.findOne(colorId);
	}
	
	@GetMapping(value= "/paginations")
	public Page<ColorDto> filter(@RequestParam("filter") String filter){
		ColorFilter c = new Gson().fromJson(filter,ColorFilter.class);
		return service.where(c);
	}
	
	@PostMapping
	private ColorDto save(@RequestBody ColorDto ColorDto) {
		return service.save(ColorDto);
	}
	
	
	@PutMapping(value = "/{id}")
	private ResponseEntity<ColorDto> edit(@RequestBody ColorDto colorDto,@PathVariable("id") int colorId) {
		colorDto.setColorId(colorId);
		if (service.findOne(colorId).getColorId() != 0) {
			return new ResponseEntity<ColorDto>(service.save(colorDto), HttpStatus.OK);
		} else {
			return new ResponseEntity<ColorDto>(new ColorDto(), HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping(value = "/{id}")
	private ResponseEntity<ColorDto>  delete(@PathVariable("id") int colorId){
		ColorDto colorDto = service.findOne(colorId);
		if(colorDto.getColorId() != 0 ) {
			service.remove(colorId);
			return new ResponseEntity<ColorDto>(colorDto, HttpStatus.OK);
		}
		return new ResponseEntity<ColorDto>(new ColorDto(), HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(value= "/code")
	public ResponseEntity<ColorDto> findByCode(@RequestParam("code") String code){
		ColorDto colorDto = service.findByCode("#"+code);
		return colorDto(colorDto);
	}
	
	@GetMapping(value= "/colorName")
	public ResponseEntity<ColorDto> findByColorName(@RequestParam("colorName") String colorName){
		ColorDto colorDto = service.findByName(colorName);
		return colorDto(colorDto);
	}
	
	public ResponseEntity<ColorDto> colorDto(ColorDto colorDto){
		if(colorDto.getColorId() != 0) {
			return new ResponseEntity<ColorDto>(colorDto, HttpStatus.OK);
		}
		return new ResponseEntity<ColorDto>(new ColorDto(), HttpStatus.NOT_FOUND);
	}

}
