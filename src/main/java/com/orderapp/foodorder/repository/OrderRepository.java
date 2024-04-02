package com.orderapp.foodorder.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderapp.foodorder.model.postgresql.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByOrderDate(Timestamp orderDate);
}
