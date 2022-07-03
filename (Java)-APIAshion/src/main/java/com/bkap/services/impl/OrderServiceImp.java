package com.bkap.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.bkap.convert.OrderConvert;
import com.bkap.dto.OrderDto;
import com.bkap.entities.Order;
import com.bkap.filter.OrderFilter;
import com.bkap.repositories.OrderRepository;
import com.bkap.services.IOrderService;

@Service
public class OrderServiceImp implements IOrderService{
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderConvert convert;

	@Override
	public List<OrderDto> getAll() {
		List<Order> listOrder = orderRepository.findAll(); 
		List<OrderDto> listOrderDto = new ArrayList<OrderDto>();
		listOrder.forEach(item -> listOrderDto.add(convert.toDto(item)));
		return listOrderDto;
	}
	
	@Override
	public List<OrderDto> getByUserId(int userId) {
		List<Order> listOrder = orderRepository.findByUserId(userId); 
		List<OrderDto> listOrderDto = new ArrayList<OrderDto>();
		listOrder.forEach(item -> listOrderDto.add(convert.toDto(item)));
		return listOrderDto;
	}

	@Override
	public OrderDto save(OrderDto orderDto) {
		Order order = new Order();
		if(orderDto.getOrderId() != 0) {
			Order oldOrder = orderRepository.getOne(orderDto.getOrderId());
			order = orderRepository.save(convert.toEntity(orderDto, oldOrder));		
		}
		else {
			order = orderRepository.save(convert.toEntity(orderDto));
		}
		return convert.toDto(order);
	}

	@Override
	public OrderDto edit(OrderDto orderDto) {
		
		return null;
	}

	@Override
	public OrderDto findOne(int orderId) {	
		return convert.toDto(orderRepository.getOne(orderId));
	}

	@Override
	public void remove(int orderId) {
		orderRepository.deleteById(orderId);
		
	}

	@Override
	public Page<OrderDto> where(OrderFilter filter) {
		Page<OrderDto> pageOrder = null;
		List<OrderDto> listOrderDto = new ArrayList<>();
		Sort sort = Sort.by(filter.getSort()).ascending();
		if(!filter.isAsc()) {
			sort = Sort.by(filter.getSort()).descending();
		}
		PageRequest pageRequest = PageRequest.of(filter.getPage(),filter.getPageSize(),sort);
		List<Order> listOrder = orderRepository.findAll(new Specification<Order>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (filter.getOrderId() != 0) {
					predicates.add(criteriaBuilder.equal(root.get("orderId"), filter.getOrderId()));
				}
				if(filter.getUserName() != null && !filter.getUserName().isEmpty()) {
					predicates.add(criteriaBuilder.like(root.get("userName"), "%" + filter.getUserName() + "%"));
				}
				if(filter.getCreateDate() != null) {
					predicates.add(criteriaBuilder.equal(root.get("createDate"), filter.getCreateDate()));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
				
		});
		if (listOrder != null) {
			listOrder.forEach(item -> { listOrderDto.add(convert.toDto(item));});
			Collections.reverse(listOrderDto);
			List<OrderDto> output = new ArrayList<>();
			int total = listOrderDto.size();
			int start = (int) pageRequest.getOffset();
			int end = Math.min((start + pageRequest.getPageSize()), total);
			if (start <= end) {
				output = listOrderDto.subList(start, end);
			}
			pageOrder = new PageImpl<OrderDto>(output, pageRequest, total);
		}
		return pageOrder;
	}


	
}
