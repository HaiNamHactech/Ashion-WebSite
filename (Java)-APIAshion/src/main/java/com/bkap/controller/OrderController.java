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

import com.bkap.dto.OrderDto;
import com.bkap.filter.OrderFilter;
import com.bkap.services.impl.OrderServiceImp;
import com.google.gson.Gson;

@RestController
@RequestMapping(value ="/api/order")
@CrossOrigin(origins="*",allowedHeaders="*") 
public class OrderController {
	
	@Autowired
	private OrderServiceImp service;
	
	@GetMapping
	public List<OrderDto> getAll() {
		return service.getAll();
	}
	
	@GetMapping(value = "/{id}")
	public OrderDto findById(@PathVariable("id") int orderId) {
		return service.findOne(orderId);
	}
	
	@GetMapping(value ="/userId")
	public List<OrderDto> findByUserId(@RequestParam("userId") int userId) {
		return service.getByUserId(userId);
	}
	
	@GetMapping(value= "/paginations")
	public Page<OrderDto> filter(@RequestParam("filter") String filter){
		OrderFilter o = new Gson().fromJson(filter,OrderFilter.class);
		return service.where(o);
	}
	
	@PostMapping
	public OrderDto save(@RequestBody OrderDto orderDto) {
		return service.save(orderDto);
	}
	
	@PutMapping(value = "/{id}")
	private ResponseEntity<OrderDto> edit(@RequestBody OrderDto orderDto,@PathVariable("id") int orderId) {
		orderDto.setOrderId(orderId);
		if (service.findOne(orderId).getOrderId() != 0) {
			return new ResponseEntity<OrderDto>(service.save(orderDto), HttpStatus.OK);
		} else {
			return new ResponseEntity<OrderDto>(new OrderDto(), HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping(value = "/{id}")
	private ResponseEntity<OrderDto> detele(@PathVariable("id") int orderId){
		OrderDto orderDto = service.findOne(orderId);
		if(orderDto.getOrderId() != 0) {
			service.remove(orderId);
			return new ResponseEntity<OrderDto>(orderDto, HttpStatus.OK);
		}
		return new ResponseEntity<OrderDto>(new OrderDto(), HttpStatus.NOT_FOUND);
	}

}
