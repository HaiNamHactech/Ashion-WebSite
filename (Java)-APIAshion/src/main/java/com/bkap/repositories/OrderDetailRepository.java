package com.bkap.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bkap.entities.OrderDetail;

@Repository
public interface OrderDetailRepository  extends JpaRepository<OrderDetail, Integer>,JpaSpecificationExecutor<OrderDetail>{
	@Query("select od from OrderDetail od JOIN od.order o where o.orderId = ?1")
	List<OrderDetail> findByOrderId(int orderId);
	
}
