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

import com.bkap.dto.SliderDto;
import com.bkap.filter.SliderFilter;
import com.bkap.services.impl.SliderServiceImpl;
import com.google.gson.Gson;

@RestController
@RequestMapping(value ="/api/slider")
@CrossOrigin(origins="*",allowedHeaders="*") 
public class SliderController {

	@Autowired
	SliderServiceImpl service;
	
	@GetMapping
	public List<SliderDto> getAll() {
		return service.getAll();
	}
	
	@GetMapping(value = "/{id}")
	public SliderDto getById (@PathVariable("id") int sliderId) {
		return service.findOne(sliderId);
	}
	@GetMapping(value= "/paginations")
	public Page<SliderDto> filter(@RequestParam("filter") String filter){
		SliderFilter sl = new Gson().fromJson(filter,SliderFilter.class);
		return service.where(sl);
	}
	
	@PostMapping
	public SliderDto save(@RequestBody SliderDto sliderDto) {
		return service.save(sliderDto);
	}
	
	@PutMapping(value = "/{id}")
	private ResponseEntity<SliderDto> edit(@RequestBody SliderDto sliderDto,@PathVariable("id") int sliderId) {
		sliderDto.setSliderId(sliderId);
		if(service.findOne(sliderId).getSliderId() != 0) {
			return new ResponseEntity<SliderDto>(service.save(sliderDto), HttpStatus.OK);
		} else {
			return new ResponseEntity<SliderDto>(new SliderDto(), HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping(value = "/{id}")
	private ResponseEntity<SliderDto> detele(@PathVariable("id") int sliderId){
		SliderDto sliderDto = service.findOne(sliderId);
		if(sliderDto.getSliderId() != 0) {
			service.remove(sliderId);
			return new ResponseEntity<SliderDto>(sliderDto, HttpStatus.OK);
		}
			return new ResponseEntity<SliderDto>(new SliderDto(), HttpStatus.NOT_FOUND);
	}

}
