package com.orderapp.foodorder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orderapp.foodorder.dto.request.OrderActionDTO;
import com.orderapp.foodorder.dto.request.OrderFilterRequestDTO;
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

    @PutMapping("/order/update")
    public ResponseEntity<Object> updateOrder(@RequestBody OrderActionDTO request) {
        return orderService.updateOrder(request);
    }

    @GetMapping("/order/users")
    public ResponseEntity<Object> getUserOrder(@RequestParam Long userId) {
        return orderService.getUserOrder(userId);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Object> getOrderById(@PathVariable("orderId") Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @GetMapping("/order/ongoing")
    public ResponseEntity<Object> getOngoingOrderByCustomerId(@RequestParam Long customerId) {
        return orderService.getOngoingOrderByCustomerId(customerId);
    }

    @GetMapping("/order/historical")
    public ResponseEntity<Object> getHistoricalOrder(@ModelAttribute OrderFilterRequestDTO request,
            @PageableDefault(page = 1, sort = "orderDate", direction = Direction.DESC) Pageable page) {
        return orderService.getHistoricalOrders(request, page);
    }
}
