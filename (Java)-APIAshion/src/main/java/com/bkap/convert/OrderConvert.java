package com.bkap.convert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bkap.dto.OrderDto;
import com.bkap.entities.Order;
import com.bkap.repositories.UserRepository;

@Component
public class OrderConvert {
	
	@Autowired
	private UserRepository userRepository;
	
	public Order toEntity(OrderDto orderDto) {
		Order order = new Order();
		order.setAddress(orderDto.getAddress());
		order.setFirstName(orderDto.getFirstName());
		order.setLastName(orderDto.getLastName());
		order.setEmail(orderDto.getEmail());
		order.setNote(orderDto.getNote());
		order.setPhone(orderDto.getPhone());
		order.setTotal(orderDto.getTotal());
		order.setStatus(orderDto.getStatus());
		order.setUser(userRepository.getOne(orderDto.getUserId()));
		return order;
	}
	
	public Order toEntity(OrderDto orderDto, Order order) {
		order.setAddress(orderDto.getAddress());
		order.setFirstName(orderDto.getFirstName());
		order.setLastName(orderDto.getLastName());
		order.setEmail(orderDto.getEmail());
		order.setNote(orderDto.getNote());
		order.setPhone(orderDto.getPhone());
		order.setTotal(orderDto.getTotal());
		order.setStatus(orderDto.getStatus());
		order.setUser(userRepository.getOne(orderDto.getUserId()));
		return order;
	}
	
	public OrderDto toDto(Order order) {
		OrderDto orderDto = new OrderDto();
		orderDto.setOrderId(order.getOrderId());
		orderDto.setUserId(order.getUser().getUserId());
		orderDto.setUserName(order.getUser().getFirstName());
		orderDto.setAddress(order.getAddress());
		orderDto.setFirstName(order.getFirstName());
		orderDto.setLastName(order.getLastName());
		orderDto.setTotal(order.getTotal());
		orderDto.setNote(order.getNote());
		orderDto.setCreateBy(order.getCreateBy());
		orderDto.setModifieBy(order.getModifieBy());
		orderDto.setCreateDate(order.getCreateDate());
		orderDto.setModifieDate(order.getModifieDate());
		orderDto.setEmail(order.getEmail());
		orderDto.setPhone(order.getPhone());
		orderDto.setStatus(order.getStatus());
		return orderDto;
	}
}
