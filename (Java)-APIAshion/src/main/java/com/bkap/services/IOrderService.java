package com.bkap.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.bkap.dto.OrderDto;
import com.bkap.filter.OrderFilter;

@Service
public interface IOrderService {
	
	List<OrderDto> getAll();
	
	List<OrderDto> getByUserId(int userId);
	
	OrderDto save(OrderDto orderDto);
	
	OrderDto edit(OrderDto orderDto);
	
	OrderDto findOne(int orderId);
	
	void remove(int orderId);

	Page<OrderDto> where(OrderFilter filter);
}
