package com.bkap.convert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bkap.dto.OrderDetailDto;
import com.bkap.entities.OrderDetail;
import com.bkap.repositories.ColorRepository;
import com.bkap.repositories.OrderRepository;
import com.bkap.repositories.ProductRepository;
import com.bkap.repositories.SizeRepository;

@Component
public class OrderDetailCovert {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ColorRepository colorRepository;
	
	@Autowired
	private SizeRepository sizeRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	public OrderDetail toEntity(OrderDetailDto orderDetailDto) {
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setOrder(orderRepository.getOne(orderDetailDto.getOrderId()));
		orderDetail.setColor(colorRepository.getOne(orderDetailDto.getColorId()));
		orderDetail.setSize(sizeRepository.getOne(orderDetailDto.getSizeId()));
		orderDetail.setProduct(productRepository.getOne(orderDetailDto.getProductId()));
		orderDetail.setPrice(orderDetailDto.getPrice());
		orderDetail.setQuantity(orderDetailDto.getQuantity());
		orderDetail.setTotal(orderDetailDto.getTotal());
		return orderDetail;
	}
	
	public OrderDetail toEntity(OrderDetailDto orderDetailDto, OrderDetail orderDetail) {
		orderDetail.setOrder(orderRepository.getOne(orderDetailDto.getOrderId()));
		orderDetail.setColor(colorRepository.getOne(orderDetailDto.getColorId()));
		orderDetail.setSize(sizeRepository.getOne(orderDetailDto.getSizeId()));
		orderDetail.setProduct(productRepository.getOne(orderDetailDto.getProductId()));
		orderDetail.setPrice(orderDetailDto.getPrice());
		orderDetail.setQuantity(orderDetailDto.getQuantity());
		orderDetail.setTotal(orderDetailDto.getTotal());
		return orderDetail;
	}
	
	public OrderDetailDto toDto(OrderDetail orderDetail) {
		OrderDetailDto orderDetailDto = new OrderDetailDto();
		orderDetailDto.setOrderDetailId(orderDetail.getOrderDetailId());
		orderDetailDto.setColorId(orderDetail.getColor().getColorId());
		orderDetailDto.setColorName(orderDetail.getColor().getColorName());
		orderDetailDto.setSizeId(orderDetail.getSize().getSizeId());
		orderDetailDto.setSizeName(orderDetail.getSize().getSizeName());
		orderDetailDto.setProductId(orderDetail.getProduct().getProductId());
		orderDetailDto.setProductName(orderDetail.getProduct().getProductName());
		orderDetailDto.setPrice(orderDetail.getPrice());
		orderDetailDto.setQuantity(orderDetail.getQuantity());
		orderDetailDto.setOrderId(orderDetail.getOrder().getOrderId());
		orderDetailDto.setTotal(orderDetail.getTotal());
		return orderDetailDto;
	}
}
