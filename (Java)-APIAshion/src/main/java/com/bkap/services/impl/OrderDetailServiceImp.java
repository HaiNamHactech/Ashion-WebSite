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

import com.bkap.convert.OrderDetailCovert;
import com.bkap.dto.OrderDetailDto;
import com.bkap.entities.OrderDetail;
import com.bkap.filter.OrderDetailFilter;
import com.bkap.repositories.OrderDetailRepository;
import com.bkap.services.IOrderDetailService;

@Service
public class OrderDetailServiceImp implements IOrderDetailService {
	
	@Autowired
	private  OrderDetailRepository orderDetailRepository;
	
	@Autowired
	private OrderDetailCovert convert;

	@Override
	public List<OrderDetailDto> getAll() {
		List<OrderDetail> listOrderDetail = orderDetailRepository.findAll(); 
		List<OrderDetailDto> listOrderDetailDto = new ArrayList<OrderDetailDto>();
		listOrderDetail.forEach(item -> listOrderDetailDto.add(convert.toDto(item)));
		return listOrderDetailDto;
	}

//	@Override
//	public boolean save(List<OrderDetailDto> listOrderDetailDto) {
//		OrderDetail orderDetail = new OrderDetail();
//		if(orderDetailDto.getOrderDetailId() != 0) {
//			OrderDetail oldOrderDetail = orderDetailRepository.getOne(orderDetailDto.getOrderDetailId());
//			orderDetail = orderDetailRepository.save(convert.toEntity(orderDetailDto, oldOrderDetail));		
//		}
//		else {
//			orderDetail = orderDetailRepository.save(convert.toEntity(orderDetailDto));
//		}
//		return convert.toDto(orderDetail);
//	}

	@Override
	public OrderDetailDto edit(OrderDetailDto OrderDetailDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderDetailDto findOne(int orderDetailId){	
		return convert.toDto(orderDetailRepository.getOne(orderDetailId));
	}

	@Override
	public void remove(int orderDetailId) {
		orderDetailRepository.deleteById(orderDetailId);
		
	}

	@Override
	public Page<OrderDetailDto> where(OrderDetailFilter filter) {
		Page<OrderDetailDto> pageOrderDetail = null;
		List<OrderDetailDto> listOrderDetailDto = new ArrayList<>();
		Sort sort = Sort.by(filter.getSort()).ascending();
		if(!filter.isAsc()) {
			sort = Sort.by(filter.getSort()).descending();
		}
		PageRequest pageRequest = PageRequest.of(filter.getPage(),filter.getPageSize(),sort);
		List<OrderDetail> listOrderDetail = orderDetailRepository.findAll(new Specification<OrderDetail>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<OrderDetail> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (filter.getOrderId() != 0) {
					predicates.add(criteriaBuilder.equal(root.get("orderId"), filter.getOrderId()));
				}
				if(filter.getProductName() != null && !filter.getProductName().isEmpty()) {
					predicates.add(criteriaBuilder.like(root.get("productName"), "%" + filter.getProductName() + "%"));
				}
				if(filter.getOrderDetailId() != 0) {
					predicates.add(criteriaBuilder.equal(root.get("orderDetailId"), filter.getOrderDetailId()));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
				
		});
		if (listOrderDetail != null) {
			listOrderDetail.forEach(item -> { listOrderDetailDto.add(convert.toDto(item));});
			Collections.reverse(listOrderDetailDto);
			List<OrderDetailDto> output = new ArrayList<>();
			int total = listOrderDetailDto.size();
			int start = (int) pageRequest.getOffset();
			int end = Math.min((start + pageRequest.getPageSize()), total);
			if (start <= end) {
				output = listOrderDetailDto.subList(start, end);
			}
			pageOrderDetail = new PageImpl<OrderDetailDto>(output, pageRequest, total);
		}
		return pageOrderDetail;
	}

	@Override
	public boolean save(List<OrderDetailDto> listOrderDetailDto) {
		if(listOrderDetailDto != null) {
			listOrderDetailDto.forEach(item -> {
				orderDetailRepository.save(convert.toEntity(item));
			});
			return true;
		}
		return false;
	}

	@Override
	public List<OrderDetailDto> getOrderDetailByOrderId(int orderId) {
		List<OrderDetail> listOrderDetail = orderDetailRepository.findByOrderId(orderId);
		List<OrderDetailDto> listOrderDetailDto = new ArrayList<>();
		listOrderDetail.forEach(item -> {
			listOrderDetailDto.add(convert.toDto(item));
		});
		return listOrderDetailDto;
	}

}
