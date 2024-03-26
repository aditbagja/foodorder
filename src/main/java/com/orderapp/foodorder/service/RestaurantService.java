package com.orderapp.foodorder.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.orderapp.foodorder.model.mongoDb.RestaurantMongo;
import com.orderapp.foodorder.repository.RestaurantMongoRepository;

@Service
public class RestaurantService {
    @Autowired
    RestaurantMongoRepository restaurantMongoRepository;

    private static final HttpStatus statusOk = HttpStatus.OK;

    public ResponseEntity<Object> getAllRestaurant() {
        List<RestaurantMongo> resto = restaurantMongoRepository.findAll();

        return new ResponseEntity<>(resto, statusOk);
    }
}
