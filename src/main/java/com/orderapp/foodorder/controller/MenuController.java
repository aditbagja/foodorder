package com.orderapp.foodorder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderapp.foodorder.service.MenuService;

@RestController
@RequestMapping("/master-management")
public class MenuController {
    @Autowired
    MenuService menuService;

    @GetMapping("/menus/{restoId}")
    public ResponseEntity<Object> getAllMenuByRestoId(@PathVariable Long restoId) {
        return menuService.getMenuByRestoId(restoId);
    }
}
