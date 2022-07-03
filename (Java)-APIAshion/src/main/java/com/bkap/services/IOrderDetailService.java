package com.bkap.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.bkap.dto.OrderDetailDto;
import com.bkap.filter.OrderDetailFilter;

@Service
public interface IOrderDetailService {

	List<OrderDetailDto> getAll();
	
	List<OrderDetailDto> getOrderDetailByOrderId(int orderId);

	boolean save(List<OrderDetailDto> listOrderDetailDto);

	OrderDetailDto edit(OrderDetailDto OrderDetailDto);

	OrderDetailDto findOne(int OrderDetailId);

	void remove(int OrderDetailId);

	Page<OrderDetailDto> where(OrderDetailFilter filter);
}
