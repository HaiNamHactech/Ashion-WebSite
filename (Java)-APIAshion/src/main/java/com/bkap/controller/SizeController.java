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

import com.bkap.dto.SizeDto;
import com.bkap.filter.SizeFilter;
import com.bkap.services.impl.SizeServiceImpl;
import com.google.gson.Gson;

@RestController
@RequestMapping(value = "/api/size")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SizeController {

	@Autowired
	SizeServiceImpl service;

	@GetMapping
	public List<SizeDto> getAll() {
		return service.getAll();
	}

	@GetMapping(value = "/{id}")
	public SizeDto getById(@PathVariable("id") int sizeId) {
		return service.findOne(sizeId);
	}

	@GetMapping(value = "/paginations")
	public Page<SizeDto> filter(@RequestParam("filter") String filter) {
		SizeFilter s = new Gson().fromJson(filter, SizeFilter.class);
		return service.where(s);
	}

	@PostMapping
	public SizeDto save(@RequestBody SizeDto sizeDto) {
		return service.save(sizeDto);
	}

	@PutMapping(value = "/{id}")
	private ResponseEntity<SizeDto> edit(@RequestBody SizeDto sizeDto, @PathVariable("id") int sizeId) {
		sizeDto.setSizeId(sizeId);
		if (service.findOne(sizeId).getSizeId() != 0) {
			return new ResponseEntity<SizeDto>(service.save(sizeDto), HttpStatus.OK);
		} else {
			return new ResponseEntity<SizeDto>(new SizeDto(), HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping(value = "/{id}")
	private ResponseEntity<SizeDto> detele(@PathVariable("id") int sizeId) {
		SizeDto sizeDto = service.findOne(sizeId);
		if (sizeDto.getSizeId() != 0) {
			service.remove(sizeId);
			return new ResponseEntity<SizeDto>(sizeDto, HttpStatus.OK);
		}
		return new ResponseEntity<SizeDto>(new SizeDto(), HttpStatus.NOT_FOUND);
	}

	@GetMapping(value = "/sizeName")
	public ResponseEntity<SizeDto> findByColorName(@RequestParam("sizeName") String sizeName) {
		SizeDto sizeDto = service.findBySizeName(sizeName);
		if (sizeDto.getSizeId() != 0) {
			return new ResponseEntity<SizeDto>(sizeDto, HttpStatus.OK);
		}
		return new ResponseEntity<SizeDto>(new SizeDto(), HttpStatus.NOT_FOUND);
	}

}
