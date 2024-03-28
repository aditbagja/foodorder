package com.orderapp.foodorder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderapp.foodorder.model.postgresql.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
