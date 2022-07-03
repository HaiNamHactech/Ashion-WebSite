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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bkap.dto.OrderDetailDto;
import com.bkap.filter.OrderDetailFilter;
import com.bkap.services.impl.OrderDetailServiceImp;
import com.google.gson.Gson;

@RestController
@RequestMapping(value ="/api/orderDetail")
@CrossOrigin(origins="*",allowedHeaders="*") 
public class OrderDetailController {
	
	@Autowired
	private OrderDetailServiceImp service;
	
	@GetMapping
	public List<OrderDetailDto> getAll() {
		return service.getAll();
	}
	
	@GetMapping(value = "/orderId")
	public List<OrderDetailDto> getOrderDetailByOrderId(@RequestParam("orderId") int orderId){
		return service.getOrderDetailByOrderId(orderId);
	}
	
	@GetMapping(value = "{/id}")
	public OrderDetailDto findById(@PathVariable("id") int orderDetailId) {	
		return service.findOne(orderDetailId);
	}
	
	@GetMapping(value= "/paginations")
	public Page<OrderDetailDto> filter(@RequestParam("filter") String filter){
		OrderDetailFilter o = new Gson().fromJson(filter,OrderDetailFilter.class);
		return service.where(o);
	}
	
	@PostMapping
	public Boolean save(@RequestBody List<OrderDetailDto> listOrderDetailDto) {
		return service.save(listOrderDetailDto);
	}
	
//	@PutMapping(value = "/{id}")
//	private ResponseEntity<OrderDetailDto> edit(@RequestBody OrderDetailDto orderDetailDto,@PathVariable("id") int orderDetailId) {
//		orderDetailDto.setOrderDetailId(orderDetailId);
//		if (service.findOne(orderDetailId).getOrderDetailId() != 0) {
//			return new ResponseEntity<OrderDetailDto>(service.save(orderDetailDto), HttpStatus.OK);
//		} else {
//			return new ResponseEntity<OrderDetailDto>(new OrderDetailDto(), HttpStatus.NOT_FOUND);
//		}
//	}
	
	@DeleteMapping(value = "/{id}")
	private ResponseEntity<OrderDetailDto> detele(@PathVariable("id") int orderDetailId){
		OrderDetailDto orderDetailDto = service.findOne(orderDetailId);
		if(orderDetailDto.getOrderDetailId() != 0) {
			service.remove(orderDetailId);
			return new ResponseEntity<OrderDetailDto>(orderDetailDto, HttpStatus.OK);
		}
		return new ResponseEntity<OrderDetailDto>(new OrderDetailDto(), HttpStatus.NOT_FOUND);
	}

}
