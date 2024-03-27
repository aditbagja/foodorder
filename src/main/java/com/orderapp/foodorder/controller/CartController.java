package com.orderapp.foodorder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderapp.foodorder.dto.request.CartRequestDTO;
import com.orderapp.foodorder.service.CartService;

@RestController
@RequestMapping("/food-order")
public class CartController {
    @Autowired
    CartService cartService;

    @PostMapping("/add-to-cart")
    public ResponseEntity<Object> addToCart(@RequestBody CartRequestDTO request) {
        return cartService.addToCart(request);
    }

    @DeleteMapping("/delete-from-cart")
    public ResponseEntity<Object> removeMenuFromCart(@RequestBody CartRequestDTO request) {
        return cartService.removeMenuFromCart(request);
    }

    @GetMapping("/cart/{userId}")
    public ResponseEntity<Object> displayListCart(@PathVariable Long userId) {
        return cartService.getDisplayListCart(userId);
    }
}
