package com.orderapp.foodorder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderapp.foodorder.dto.request.LoginRequestDTO;
import com.orderapp.foodorder.dto.request.RegisterRequestDTO;
import com.orderapp.foodorder.service.UserService;

@RestController
@RequestMapping("/user-management")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<Object> register(@RequestBody RegisterRequestDTO request) {
        return userService.register(request);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<Object> login(@RequestBody LoginRequestDTO request) {
        return userService.login(request);
    }
}
