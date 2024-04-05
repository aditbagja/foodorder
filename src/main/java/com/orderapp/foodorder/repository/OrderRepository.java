package com.orderapp.foodorder.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.orderapp.foodorder.model.postgresql.Orders;

public interface OrderRepository extends JpaRepository<Orders, Long>, JpaSpecificationExecutor<Orders> {
    List<Orders> findAllByOrderDate(Timestamp orderDate);

    long countByStatus(String status);

}
