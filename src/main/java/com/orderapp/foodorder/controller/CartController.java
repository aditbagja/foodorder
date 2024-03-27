package com.orderapp.foodorder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderapp.foodorder.dto.request.AddToCartRequestDTO;
import com.orderapp.foodorder.service.CartService;

@RestController
@RequestMapping("/food-order")
public class CartController {
    @Autowired
    CartService cartService;

    @PostMapping("/cart")
    public ResponseEntity<Object> addToCart(@RequestBody AddToCartRequestDTO request) {
        return cartService.addToCart(request);
    }
}
