package com.orderapp.foodorder.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.orderapp.foodorder.model.mongoDb.MenuMongo;
import com.orderapp.foodorder.repository.MenuMongoRepository;

@Service
public class MenuService {
    @Autowired
    MenuMongoRepository menuMongoRepository;

    private static final HttpStatus statusOk = HttpStatus.OK;

    public ResponseEntity<Object> getMenuByRestoId(Long restoId) {
        List<MenuMongo> menus = menuMongoRepository.findAllByResto_Id(restoId);

        return new ResponseEntity<>(menus, statusOk);
    }
}
