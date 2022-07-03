package com.bkap.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bkap.entities.Order;

@Repository
public interface OrderRepository  extends JpaRepository<Order, Integer>,JpaSpecificationExecutor<Order> {
	@Query("select o from Order o JOIN o.userO u where u.userId = ?1")
	List<Order> findByUserId(int userId);
}
