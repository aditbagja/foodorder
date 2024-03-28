package com.orderapp.foodorder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderapp.foodorder.service.OrderService;

@RestController
@RequestMapping("/food-order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/order/{userId}")
    public ResponseEntity<Object> createOrder(@PathVariable Long userId) {
        return orderService.createOrder(userId);
    }
}
