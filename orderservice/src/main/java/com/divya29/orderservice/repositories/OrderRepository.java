package com.divya29.orderservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.divya29.orderservice.models.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	public List<Order> findAllByCustomerId(Long customerId);
}